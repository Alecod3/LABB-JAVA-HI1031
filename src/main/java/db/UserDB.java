package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import bo.User;

public class UserDB {

    private final DBManager dbManager = new DBManager();

    public User findByCredentials(String username, String password) throws SQLException {
        String sql = "SELECT id, username FROM users WHERE username = ?"
                + " AND CAST(password AS BINARY) = CAST(? AS BINARY)";
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new User(result.getInt("id"), result.getString("username"));
                }
            }
        }
        return null;
    }
}
