package ru.job4j.dish.service;

import ru.job4j.dish.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishService {

    void save(Dish dish);

    Dish update(Dish dish);

    void delete(int id);

    List<Dish> findAll();

    Optional<Dish> findById(int id);

    Optional<Dish> findByName(String name);
}
