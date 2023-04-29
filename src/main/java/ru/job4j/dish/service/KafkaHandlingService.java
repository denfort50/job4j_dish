package ru.job4j.dish.service;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.job4j.dish.exception.DishDoesNotExistException;
import ru.job4j.dish.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KafkaHandlingService {

    private final DishServiceImpl dishService;

    private final KafkaSendingService kafkaSendingService;

    @KafkaListener(topics = "save-requests-topic", groupId = "group-id")
    void receiveAndHandleSaveResponse(String dishString) {
        JSONObject jsonDish = new JSONObject(dishString);
        Dish dish = new Dish(
                jsonDish.getString("name"),
                jsonDish.getString("description"));
        dishService.save(dish);
        kafkaSendingService.sendSaveResponse(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @KafkaListener(topics = "update-requests-topic", groupId = "group-id")
    void receiveAndHandleUpdateResponse(String dishString) {
        JSONObject jsonDish = new JSONObject(dishString);
        Dish dish = new Dish(
                jsonDish.getInt("id"),
                jsonDish.getString("name"),
                jsonDish.getString("description"));
        dishService.findById(dish.getId())
                .orElseThrow(() -> new DishDoesNotExistException("Such a dish doesn't exist in database."));
        dishService.update(dish);
        kafkaSendingService.sendUpdateResponse(ResponseEntity.ok().build());
    }

    @KafkaListener(topics = "delete-requests-topic", groupId = "group-id")
    void receiveAndHandleDeleteResponse(String idString) {
        int id = Integer.parseInt(idString);
        dishService.findById(id)
                .orElseThrow(() -> new DishDoesNotExistException("Such a dish doesn't exist in database."));
        dishService.delete(id);
        kafkaSendingService.sendDeleteResponse(ResponseEntity.ok().build());
    }

    @KafkaListener(topics = "findAll-requests-topic", groupId = "group-id")
    void receiveAndHandleFindAllResponse(String request) {
        List<Dish> dishes = new ArrayList<>();
        if ("findAll".equals(request)) {
            dishes = dishService.findAll();
        }
        kafkaSendingService.sendFindAllResponse(dishes);
    }

    @KafkaListener(topics = "findById-requests-topic", groupId = "group-id")
    void receiveAndHandleFindByIdResponse(String idString) {
        int id = Integer.parseInt(idString);
        Optional<Dish> optionalDish = dishService.findById(id);
        kafkaSendingService.sendFindByIdResponse(optionalDish.orElse(new Dish()));
    }

    @KafkaListener(topics = "findByName-requests-topic", groupId = "group-id")
    void receiveAndHandleFindByNameResponse(String name) {
        Optional<Dish> optionalDish = dishService.findByName(name);
        kafkaSendingService.sendFindByNameResponse(optionalDish.orElse(new Dish()));
    }

}
