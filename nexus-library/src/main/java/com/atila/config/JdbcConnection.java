package com.atila.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/nexusLibrary";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres123";

    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            props.setProperty("user", USER);
            props.setProperty("password", PASSWORD);

            Connection conn = DriverManager.getConnection(URL, props);
            System.out.println(conn.getMetaData().getDatabaseProductVersion());

            return conn;

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }
}
