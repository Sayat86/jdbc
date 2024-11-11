package org.example.springjdbcsayat.controller;

import lombok.RequiredArgsConstructor;
import org.example.springjdbcsayat.dao.CategoryDao;
import org.example.springjdbcsayat.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryDao categoryDao;

    @GetMapping
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable int id) {
        return categoryDao.findById(id);
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryDao.create(category);
    }
}
