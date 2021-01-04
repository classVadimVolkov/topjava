package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Collections.sort(meals, Comparator.comparing(UserMeal::getDateTime));

        List<UserMealWithExcess> mealWithExcessList = new ArrayList<>();
        Map<Integer, Boolean> map = new HashMap<>();
        LocalDateTime previousDateTime = null;
        UserMeal previousUserMeal = null;
        int totalCalories = 0;

        for (int i = 0; i < meals.size(); i++) {
            UserMeal userMeal = meals.get(i);
            if (previousDateTime == null) {
                previousDateTime = userMeal.getDateTime();
            }

            if (previousDateTime.getDayOfYear() != userMeal.getDateTime().getDayOfYear()) {
                Boolean excess = totalCalories > caloriesPerDay;
                Integer dayOfTheYear = previousUserMeal.getDateTime().getDayOfYear();
                map.put(dayOfTheYear, excess);
                previousDateTime = userMeal.getDateTime();
                totalCalories = userMeal.getCalories();
            } else {
                totalCalories += userMeal.getCalories();
                previousUserMeal = userMeal;
            }

            if (i == meals.size() - 1) {
                Boolean excess = totalCalories > caloriesPerDay;
                Integer dayOfTheYear = userMeal.getDateTime().getDayOfYear();
                map.put(dayOfTheYear, excess);
            }
        }

        for(UserMeal userMeal : meals) {
            Boolean excess = map.get(userMeal.getDateTime().getDayOfYear());
            Boolean timeIsBetween = TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime);
            if (excess != null && timeIsBetween) {
                mealWithExcessList.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(), excess));
            }
        }

        return mealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
