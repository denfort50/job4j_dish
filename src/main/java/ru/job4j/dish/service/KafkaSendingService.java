package ru.job4j.dish.service;

import lombok.AllArgsConstructor;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.dish.model.Dish;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class KafkaSendingService {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendSaveResponse(ResponseEntity<?> response) {
        kafkaTemplate.send("save-responses-topic", response.getStatusCode().toString());
    }

    public void sendUpdateResponse(ResponseEntity<?> response) {
        kafkaTemplate.send("update-responses-topic", response.getStatusCode().toString());
    }

    public void sendDeleteResponse(ResponseEntity<?> response) {
        kafkaTemplate.send("delete-responses-topic", response.getStatusCode().toString());
    }

    public void sendFindAllResponse(List<Dish> response) {
        JSONObject responseDetailsJson  = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Dish dish : response) {
            JSONObject formDetailsJson  = new JSONObject(dish);
            jsonArray.put(formDetailsJson);
        }
        responseDetailsJson.put("dishes", jsonArray);
        kafkaTemplate.send("findAll-responses-topic", responseDetailsJson.toString());
    }

    public void sendFindByIdResponse(Dish response) {
        JSONObject jsonObject = new JSONObject(response);
        kafkaTemplate.send("findById-responses-topic", jsonObject.toString());
    }

    public void sendFindByNameResponse(Dish response) {
        JSONObject jsonObject = new JSONObject(response);
        kafkaTemplate.send("findByName-responses-topic", jsonObject.toString());
    }
}
