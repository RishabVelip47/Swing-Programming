package com.library.db;

import java.sql.*;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/library_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER     = "root";
    private static final String PASSWORD = "Violatic123"; // <-- Change this

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Database connection failed!\n" + e.getMessage(),
                "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException ignored) {}
    }
}