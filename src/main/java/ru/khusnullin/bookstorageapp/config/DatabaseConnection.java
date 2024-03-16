package ru.khusnullin.bookstorageapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable {
    private static final String URL = "jdbc:postgresql://localhost:5432/library";
    private static final String PASSWORD = "qwerty007";
    private static final String USERNAME = "postgres";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); // Загрузка драйвера JDBC
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC driver not found", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    @Override
    public void close() throws Exception {

    }
}