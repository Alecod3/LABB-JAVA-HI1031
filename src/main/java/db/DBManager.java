package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

    public Connection getConnection() throws SQLException {
        Properties properties = new Properties();

        try (InputStream input = DBManager.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                throw new SQLException("Konfigurationsfilen db.properties saknas.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new SQLException("Kunde inte läsa db.properties.", e);
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        if (url == null || url.trim().isEmpty()
                || username == null || username.trim().isEmpty()
                || password == null) {
            throw new SQLException("db.url, db.username och db.password måste anges.");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQLs JDBC-driver saknas.", e);
        }

        return DriverManager.getConnection(url, username, password);
    }
}
