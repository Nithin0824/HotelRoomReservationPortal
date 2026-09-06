package com.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:oracle:thin:@//172.30.0.173:1521/FREEPDB1";

    private static final String USER = "HOTEL_USER";

    private static final String PASSWORD = "Hotel@123";

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Oracle JDBC Driver not found!", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
