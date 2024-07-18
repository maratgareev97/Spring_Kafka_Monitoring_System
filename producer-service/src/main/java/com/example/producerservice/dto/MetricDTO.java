package com.example.producerservice.dto;


import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class MetricDTO {
    @NotNull(message = "Metric name cannot be null")
    private String name;

    @NotNull(message = "Metric data cannot be null")
    private Map<String, Object> data;

    public @NotNull(message = "Metric name cannot be null") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Metric name cannot be null") String name) {
        this.name = name;
    }

    public @NotNull(message = "Metric data cannot be null") Map<String, Object> getData() {
        return data;
    }

    public void setData(@NotNull(message = "Metric data cannot be null") Map<String, Object> data) {
        this.data = data;
    }
}
