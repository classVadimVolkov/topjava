package ru.javawebinar.topjava.repository.impl;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealRepositoryInMemoryImpl implements MealRepository {
    private static final Logger log = getLogger(MealRepositoryInMemoryImpl.class);
    private final AtomicInteger atomicId;
    private final Map<Integer, Meal> mealsById = new ConcurrentHashMap<>();

    public MealRepositoryInMemoryImpl() {
        this.atomicId = new AtomicInteger();
        MealsUtil.getMeals().forEach(meal -> {
            mealsById.put(meal.getId(), meal);
            atomicId.getAndIncrement();
        });
    }

    @Override
    public void addMeal(Meal meal) {
        log.debug("add meal {}", meal.toString());
        int id = atomicId.incrementAndGet();
        meal.setId(id);
        mealsById.put(id, meal);
    }

    @Override
    public Meal getMealById(int id) {
        log.debug("get meal by id = {}", id);
        return mealsById.get(id);
    }

    @Override
    public void updateMeal(int id, Meal editMeal) {
        log.debug("update editMeal {} by id = {}", editMeal.toString(), id);
        mealsById.put(id, editMeal);
    }

    @Override
    public void deleteMeal(int id) {
        log.debug("delete meal by id = {}", id);
        mealsById.remove(id);
    }

    @Override
    public List<Meal> getAllMeals() {
        log.debug("get all meals");
        return new ArrayList<>(mealsById.values());
    }
}
