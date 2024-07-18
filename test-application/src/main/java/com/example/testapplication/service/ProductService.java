package com.example.testapplication.service;

import com.example.testapplication.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    public Optional<Product> getProductById(String id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }
}