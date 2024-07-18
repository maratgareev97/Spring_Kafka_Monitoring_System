package com.example.testapplication.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "customEndpoint")
public class CustomEndpoint {
    @ReadOperation
    public CustomData customOperation() {
        return new CustomData("Some data", "More data");
    }

    public static class CustomData {
        private String data1;
        private String data2;

        public CustomData(String data1, String data2) {
            this.data1 = data1;
            this.data2 = data2;
        }

        // Getters and Setters
        public String getData1() {
            return data1;
        }

        public String getData2() {
            return data2;
        }
    }
}