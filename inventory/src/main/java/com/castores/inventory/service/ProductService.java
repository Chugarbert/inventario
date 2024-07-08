package com.castores.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castores.inventory.model.Product;
import com.castores.inventory.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getActiveProducts() {
        return productRepository.findByEstatus(1);
    }
}