package com.example.final_coursework.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static DBConnection dbConnection;
    public Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos","root","2217");
    }
    public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
        return dbConnection != null ? dbConnection : (dbConnection = new DBConnection());
    }
    public Connection getConnection(){
        return connection;
    }
}
