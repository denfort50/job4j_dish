package ru.job4j.dish.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.dish.model.Dish;
import ru.job4j.dish.repository.DishRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DishServiceImpl implements DishService {

    private DishRepository dishRepository;

    public void save(Dish dish) {
        dishRepository.save(dish);
    }

    public Dish update(Dish dish) {
        return dishRepository.save(dish);
    }

    public void delete(int id) {
        dishRepository.deleteById(id);
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Optional<Dish> findById(int id) {
        return dishRepository.findById(id);
    }

    @Override
    public Optional<Dish> findByName(String name) {
        return dishRepository.findByName(name);
    }
}
