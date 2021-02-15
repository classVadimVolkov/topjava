package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;


    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("get all");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFilteredByDate(LocalDate startDate, LocalDate endDate,
                                          LocalTime startTime, LocalTime endTime) {
        log.info("get all filtered by startDate={}, endDate={}, startTime={}, endTime={}",
                startDate, endDate, startTime, endTime);
        Collection<Meal> filteredByDate = service.getFilteredByDate(SecurityUtil.authUserId(), startDate, endDate);
        return MealsUtil.getFilteredTos(filteredByDate, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);

    }

    public Meal get(int id) {
        log.info("get by id={}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal.toString());
        ValidationUtil.checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete by id={}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {}, id={}", meal.toString(), id);
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}