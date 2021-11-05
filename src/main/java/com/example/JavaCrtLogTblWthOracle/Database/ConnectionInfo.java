package com.example.JavaCrtLogTblWthOracle.Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionInfo {
    public Connection Info(String url, String owner, String password) throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(url, owner, password);

        return connection;
    }
}
