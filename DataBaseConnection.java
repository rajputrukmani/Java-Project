 
package com.example.CleanGreenIndore;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cleangreenindoreapp";
    private static final String USER = "root";
    private static final String PASSWORD = "9111337529";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
