package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            System.out.println("------------------");
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println("------------------");
            mealRestController.getFilteredByDate(LocalDate.of(2020, Month.JANUARY, 30),
                    LocalDate.of(2020, Month.JANUARY, 31),
                    LocalTime.of(9, 0), LocalTime.of(15, 0))
                    .forEach(System.out::println);
            System.out.println("------------------");
            System.out.println(mealRestController.get(1).toString());
            System.out.println("------------------");
            System.out.println(mealRestController.create(new Meal(
                    LocalDateTime.of(2020, Month.FEBRUARY, 15, 22, 0),
                    "Before sleep", 2500)));
            System.out.println("------------------");
            mealRestController.update(new Meal(
                    LocalDateTime.of(2020, Month.FEBRUARY, 15, 22, 0),
                    "Update", 4000), 1);
            System.out.println("------------------");
            mealRestController.delete(1);
        }
    }
}
