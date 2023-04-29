package ru.job4j.dish.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic saveResponsesTopic() {
        return TopicBuilder.name("save-responses-topic")
                .build();
    }

    @Bean
    public NewTopic updateResponsesTopic() {
        return TopicBuilder.name("update-responses-topic")
                .build();
    }

    @Bean
    public NewTopic deleteResponsesTopic() {
        return TopicBuilder.name("delete-responses-topic")
                .build();
    }

    @Bean
    public NewTopic findAllResponsesTopic() {
        return TopicBuilder.name("findAll-responses-topic")
                .build();
    }

    @Bean
    public NewTopic findByIdResponsesTopic() {
        return TopicBuilder.name("findById-responses-topic")
                .build();
    }

    @Bean
    public NewTopic findByNameResponsesTopic() {
        return TopicBuilder.name("findByName-responses-topic")
                .build();
    }
}
