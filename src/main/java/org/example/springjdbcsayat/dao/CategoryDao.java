package org.example.springjdbcsayat.dao;

import org.example.springjdbcsayat.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
    Category findById(int id);
    Category create(Category category);
}
