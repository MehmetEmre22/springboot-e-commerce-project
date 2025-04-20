package com.example.demo1234.controller;

import com.example.demo1234.model.Product;
import com.example.demo1234.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get-all-product")
    public List<Product> getAll() {
        return productService.getAll();
    }
    @PostMapping("/save-product")
    public Product save(@RequestBody Product product){
        return  productService.save(product);
    }
}