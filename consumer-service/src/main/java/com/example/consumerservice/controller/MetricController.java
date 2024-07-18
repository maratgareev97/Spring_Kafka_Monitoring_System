package com.example.consumerservice.controller;

import com.example.consumerservice.model.Metric;
import com.example.consumerservice.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricController {
    @Autowired
    private MetricService metricService;

    @PostMapping
    public Metric createMetric(@RequestBody Metric metric) {
        return metricService.saveMetric(metric);
    }

    @GetMapping
    public List<Metric> getAllMetrics() {
        return metricService.getAllMetrics();
    }

    @GetMapping("/{id}")
    public Metric getMetricById(@PathVariable String id) {
        return metricService.getMetricById(id);
    }
}
