package org.example.springjdbcsayat.controller;

import lombok.RequiredArgsConstructor;
import org.example.springjdbcsayat.dao.ProductDao;
import org.example.springjdbcsayat.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductDao productDao;

    @GetMapping
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable int id) {
        return productDao.findById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productDao.create(product);
    }
}
