package com.example.demo1234.service;

import com.example.demo1234.model.Product;
import com.example.demo1234.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository=repository;
    }
    public Product save(Product product){
        return repository.save(product);
    }
    public List<Product> getAll(){
        return repository.findAll();
    }
}
