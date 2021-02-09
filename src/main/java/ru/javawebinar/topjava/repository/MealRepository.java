package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    void addMeal(Meal meal);

    Meal getMealById(int id);

    void updateMeal(int id, Meal editMeal);

    void deleteMeal(int id);

    List<Meal> getAllMeals();
}
