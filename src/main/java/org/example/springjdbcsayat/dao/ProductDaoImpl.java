package org.example.springjdbcsayat.dao;

import lombok.RequiredArgsConstructor;
import org.example.springjdbcsayat.model.Category;
import org.example.springjdbcsayat.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {
    private final JdbcTemplate jdbcTemplate;
    private final CategoryDao categoryDao;

    private static final String SELECT = """
            select products.id     as product_id,
                   products.name   as product_name,
                   products.price as product_price,
                   categories.id as category_id,
                   categories.name as category_name
            from products
            join categories on products.category_id = categories.id""";

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public Product findById(int id) {
        String sql = SELECT + " where products.id = ?";
        return jdbcTemplate.query(sql, this::mapRow, id)
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Product create(Product product) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map = Map.of("name", product.getName(),
                "price", 123123,
                "category_id", product.getCategory().getId());
        int id = insert.executeAndReturnKey(map).intValue();
        product.setId(id);
        return product;
    }

    private Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        int productId = rs.getInt("product_id");
        String productName = rs.getString("product_name");

        int categoryId = rs.getInt("category_id");
        String categoryName = rs.getString("category_name");
        double productPrice = rs.getDouble("product_price");

        Category category = new Category(categoryId, categoryName);
        return new Product(productId, productName, productPrice, category);
    }
}
