package ru.job4j.dish.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Контролер для манипуляции списком блюд",
        description = "Контроллер обрабатывает запросы на создание, чтение, обновление и удаление блюд")
public class DishController {

    private DishServiceImpl dishService;

    @Operation(summary = "Сохранить", description = "Позволяет сохранить блюдо")
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody Dish dish) {
        dishService.save(dish);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить полностью", description = "Позволяет обновить все поля блюда")
    @PostMapping("/update")
    public ResponseEntity<Void> updateEntire(@RequestBody Dish dish) {
        dishService.findById(dish.getId())
                .orElseThrow(() -> new DishDoesNotExistException("Such a dish doesn't exist in database."));
        dishService.update(dish);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновить частично", description = "Позволяет обновить часть полей блюда")
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
        dishService.update(dishInDb);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить", description = "Позволяет удалить блюдо")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Уникальный идентификатор") @PathVariable int id) {
        dishService.findById(id)
                .orElseThrow(() -> new DishDoesNotExistException("Such a dish doesn't exist in database."));
        dishService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Показать все", description = "Позволяет загрузить все блюда")
    @GetMapping("/findAll")
    public ResponseEntity<List<Dish>> findAll() {
        List<Dish> dishes = dishService.findAll();
        ResponseEntity<List<Dish>> response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if (!dishes.isEmpty()) {
            response = new ResponseEntity<>(dishes, HttpStatus.OK);
        }
        return response;
    }

    @Operation(summary = "Найти по ID", description = "Позволяет найти блюдо по ID")
    @GetMapping("/findById/{id}")
    public ResponseEntity<Dish> findById(@Parameter(description = "Уникальный идентификатор") @PathVariable int id) {
        Optional<Dish> optionalDish = dishService.findById(id);
        return new ResponseEntity<>(optionalDish.orElse(new Dish()),
                optionalDish.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Найти по названию", description = "Позволяет найти блюдо по названию")
    @GetMapping("/findByName/{name}")
    public ResponseEntity<Dish> findByName(@Parameter(description = "Название") @PathVariable String name) {
        Optional<Dish> optionalDish = dishService.findByName(name);
        return new ResponseEntity<>(optionalDish.orElse(new Dish()),
                optionalDish.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
