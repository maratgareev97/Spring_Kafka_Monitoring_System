package com.example.producerservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MetricsCollectorService {
    private static final Logger logger = LoggerFactory.getLogger(MetricsCollectorService.class);

    private static final String METRICS_URL = "http://localhost:8083/actuator/metrics";
    private static final String TOPIC = "metrics-topic";

    @Autowired
    private WebClient webClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedRate = 10000)
    public void fetchAndSendMetrics() {
        logger.info("Начинаем сбор метрик");
        Map<String, Object> metrics = new HashMap<>();
        collectMetric("system.cpu.usage").subscribe(value -> {
            metrics.put("system.cpu.usage", value);
            logger.debug("Collected system.cpu.usage: {}", value);
        });
        collectMetric("jvm.memory.used").subscribe(value -> {
            metrics.put("jvm.memory.used", value);
            logger.debug("Collected jvm.memory.used: {}", value);
        });
        collectMetric("jvm.threads.live").subscribe(value -> {
            metrics.put("jvm.threads.live", value);
            logger.debug("Collected jvm.threads.live: {}", value);
            sendToKafka(metrics);
        });
        logger.info("Сбор и отправка метрик завершены");
    }

    private Mono<String> collectMetric(String metricName) {
        return webClient.get()
                .uri(METRICS_URL + "/" + metricName)
                .retrieve()
                .bodyToMono(String.class);
    }

    private void sendToKafka(Map<String, Object> metrics) {
        try {
            String jsonMetrics = objectMapper.writeValueAsString(metrics);
            kafkaTemplate.send(TOPIC, jsonMetrics);
            System.out.println("показатели отправлены в Kafka.: " + jsonMetrics);
        } catch (Exception e) {
            System.err.println("Ошибка отправки метрик в Kafka: " + e.getMessage());
        }
    }
}
