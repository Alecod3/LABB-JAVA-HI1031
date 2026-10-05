package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bo.Product;

public class ProductDB {

    private final DBManager dbManager = new DBManager();

    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<Product>();
        String sql = "SELECT id, name, description, price FROM products ORDER BY id";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Product product = new Product(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getBigDecimal("price"));
                products.add(product);
            }
        }

        return products;
    }
    public Product findById(int id) throws SQLException {
        String sql = "SELECT id, name, description, price FROM products WHERE id = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Product(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("description"),
                            result.getBigDecimal("price"));
                }
            }
        }

        return null;
    }
}
