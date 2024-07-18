package com.example.producerservice.controller;

import com.example.producerservice.dto.MetricDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.kafka.core.KafkaTemplate;

@RestController
public class MetricsController {

    private static final String TOPIC = "metrics-topic";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/metrics")
    public String postMetrics(@RequestBody @Validated MetricDTO metrics) {
        try {
            String jsonMetrics = objectMapper.writeValueAsString(metrics);
            kafkaTemplate.send(TOPIC, jsonMetrics);
            return "Показатели успешно опубликованы";
        } catch (Exception e) {
            return "Ошибка отправки показателей: " + e.getMessage();
        }
    }
}
