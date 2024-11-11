package org.example.springjdbcsayat.dao;

import lombok.RequiredArgsConstructor;
import org.example.springjdbcsayat.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CategoryDaoImpl implements CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("select * from categories",this::mapRow);
    }

    @Override
    public Category findById(int id) {
        return jdbcTemplate.query("select * from categories where id = ?",this::mapRow, id)
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Category create(Category category) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("categories")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> map = Map.of("name", category.getName());

        int id = insert.executeAndReturnKey(map).intValue();
        category.setId(id);
        return category;
    }

    private Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Category(id, name);
    }
}
