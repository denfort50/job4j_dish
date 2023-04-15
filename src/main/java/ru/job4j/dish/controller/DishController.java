package ru.job4j.dish.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dish.exception.DishDoesNotExistException;
import ru.job4j.dish.model.Dish;
import ru.job4j.dish.service.DishServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/dish")
public class DishController {

    private DishServiceImpl dishService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody Dish dish) {
        dishService.save(dish);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateEntire(@RequestBody Dish dish) {
        dishService.findById(dish.getId())
                .orElseThrow(() -> new DishDoesNotExistException("Such a dish doesn't exist in database."));
        dishService.update(dish);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updatePartly(@RequestBody Dish dish) {
        Dish dishInDb = dishService.findById(dish.getId())
                .orElseThrow(() -> new DishDoesNotExistException("Such a dish doesn't exist in database."));
        if (dish.getName() != null) {
            dishInDb.setName(dish.getName());
        }
        if (dish.getDescription() != null) {
            dishInDb.setDescription(dish.getDescription());
        }

        dishService.update(dish);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        dishService.findById(id)
                .orElseThrow(() -> new DishDoesNotExistException("Such a dish doesn't exist in database."));
        dishService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Dish>> findAll() {
        List<Dish> dishes = dishService.findAll();
        ResponseEntity<List<Dish>> response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if (!dishes.isEmpty()) {
            response = new ResponseEntity<>(dishes, HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Dish> findById(@PathVariable int id) {
        Optional<Dish> optionalDish = dishService.findById(id);
        return new ResponseEntity<>(optionalDish.orElse(new Dish()),
                optionalDish.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<Dish> findByName(@PathVariable String name) {
        Optional<Dish> optionalDish = dishService.findByName(name);
        return new ResponseEntity<>(optionalDish.orElse(new Dish()),
                optionalDish.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
