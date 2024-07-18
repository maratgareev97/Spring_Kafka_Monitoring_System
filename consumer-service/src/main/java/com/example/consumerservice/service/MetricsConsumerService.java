package com.example.consumerservice.service;

import com.example.consumerservice.model.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MetricsConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(MetricsConsumerService.class);

    @Autowired
    private MetricService metricService;

    @KafkaListener(topics = "metrics-topic", groupId = "group_id")
    public void listen(Object message) {
        logger.info("Received message from Kafka: {}", message);

        Metric newMetric = new Metric(
                String.valueOf(System.currentTimeMillis()),
                "kafka",
                message.toString()
        );
        metricService.saveMetric(newMetric);
        logger.info("Metric saved: {}", newMetric);
    }
}
