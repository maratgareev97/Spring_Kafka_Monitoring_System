package com.example.consumerservice.service;

import com.example.consumerservice.model.Metric;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MetricService {
    private final ConcurrentHashMap<String, Metric> metrics = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    public Metric saveMetric(Metric metric) {
        String id = String.valueOf(idGenerator.incrementAndGet());
        metric.setId(id);
        metrics.put(id, metric);
        return metric;
    }

    public List<Metric> getAllMetrics() {
        return new ArrayList<>(metrics.values());
    }

    public Metric getMetricById(String id) {
        return metrics.get(id);
    }
}
