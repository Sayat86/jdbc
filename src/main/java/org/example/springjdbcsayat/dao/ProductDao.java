package org.example.springjdbcsayat.dao;

import org.example.springjdbcsayat.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();
    Product findById(int id);
    Product create(Product product);
    Product update(Product product);
}
