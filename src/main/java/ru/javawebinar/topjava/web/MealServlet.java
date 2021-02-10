package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.impl.MealRepositoryInMemoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
    private static MealRepository mealRepository;

    private static final String INSERT_OR_EDIT = "/meal_card.jsp";
    private static final String LIST_MEAL = "/meals.jsp";

    public MealServlet() {
        mealRepository = new MealRepositoryInMemoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = LIST_MEAL;
        String action = req.getParameter("action");

        log.debug("method doGet, action = {}", action);

        action = (action == null) ? "empty" : action;

        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            mealRepository.deleteMeal(id);
            forward = LIST_MEAL;
            req.setAttribute("meals", getMealsTo());
        } else if (action.equalsIgnoreCase("update")) {
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(req.getParameter("id"));
            Meal meal = mealRepository.getMealById(id);
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("add")) {
            forward = INSERT_OR_EDIT;
        } else if (action.equalsIgnoreCase("empty")) {
            req.setAttribute("meals", getMealsTo());
        }

        req.setAttribute("dateFormatter", DATE_TIME_FORMATTER);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("method doPost, trying add/update");

        Meal meal = new Meal();
        meal.setDescription(req.getParameter("description"));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));
        meal.setDateTime(LocalDateTime.parse(req.getParameter("dateTime")));

        String id = req.getParameter("id");

        if (id == null || id.isEmpty()) {
            log.debug("method doPost, add meal {}", meal.toString());
            mealRepository.addMeal(meal);
        } else {
            log.debug("method doPost, update by id = {} meal {}", id, meal.toString());
            meal.setId(Integer.parseInt(id));
            mealRepository.updateMeal(Integer.parseInt(id), meal);
        }

        req.setAttribute("dateFormatter", DATE_TIME_FORMATTER);
        req.setAttribute("meals", getMealsTo());
        req.getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }

    private static List<MealTo> getMealsTo() {
        return MealsUtil.filteredByStreams(
                mealRepository.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }
}

