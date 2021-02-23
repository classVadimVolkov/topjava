package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int USER_ID = AbstractBaseEntity.START_SEQ;
    public static final int ADMIN_ID = AbstractBaseEntity.START_SEQ + 1;
    public static final int MEAL_ID = ADMIN_ID + 1;
    public static final int NOT_FOUND = 100;
    public static final int CALORIES_PER_DAY = 1000;

    public static final Meal meal1 = new Meal(
            MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0),
            "Завтрак", 500);
    public static final Meal meal2 = new Meal(
            MEAL_ID + 1, LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0),
            "Обед", 1000);
    public static final Meal meal3 = new Meal(
            MEAL_ID + 2, LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0),
            "Ужин", 500);
    public static final Meal meal4 = new Meal(
            MEAL_ID + 3, LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0),
            "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(
            MEAL_ID + 4, LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0),
            "Завтрак", 1000);
    public static final Meal meal6 = new Meal(
            MEAL_ID + 5, LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0),
            "Обед", 500);
    public static final Meal meal7 = new Meal(
            MEAL_ID + 6, LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0),
            "Ужин", 410);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "Новая тестовая еда", CALORIES_PER_DAY);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.of(2021, Month.FEBRUARY, 23, 17, 20));
        updated.setDescription("Тестовое обновленное описание еды");
        updated.setCalories(9999);
        return updated;
    }

    public static List<Meal> getUserMeals() {
        return Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, (Meal) Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
