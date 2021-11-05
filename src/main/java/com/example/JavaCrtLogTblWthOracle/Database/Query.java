package com.example.JavaCrtLogTblWthOracle.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query {

    public List<String> AllTablesShow(String url, String owner, String password) throws SQLException {
        ConnectionInfo connection = new ConnectionInfo();
        Connection conn = connection.Info(url, owner, password);
        Statement smt = conn.createStatement();
        String sql = "SELECT owner, table_name FROM all_tables WHERE OWNER='DENEME'";
        ResultSet resultSet = smt.executeQuery(sql);
        List<String> tables = new ArrayList<String>();
        while (resultSet.next()) {
            tables.add(resultSet.getString("TABLE_NAME"));
        }

        conn.close();
        return tables;
    }


    public List<String> ShowNotLogTables(String url, String owner, String password) throws SQLException {
        ConnectionInfo connection = new ConnectionInfo();
        Connection conn = connection.Info(url, owner, password);
        Statement smt = conn.createStatement();
        String sql = "SELECT owner, table_name FROM all_tables WHERE OWNER='DENEME'";
        ResultSet resultSet = smt.executeQuery(sql);
        List<String> tables = new ArrayList<String>();
        while (resultSet.next()) {
            tables.add(resultSet.getString("TABLE_NAME"));
        }
        System.out.println(tables);
        conn.close();
        int tableLen = tables.size();
        for (int i = 0; i < tables.size(); i++) {
            String tablePoint = tables.get(i);
            String tablePointAlternative = (tables.get(i) + "_LOG");

            if ((tables.contains(tablePoint) && tables.contains(tablePointAlternative)) ) {
                tables.remove(tablePoint);
                tables.remove(tablePointAlternative);
                i--;
            }
            }


        System.out.println(tables);
        return tables;
    }

        public List<String>GetColomnName(String url, String owner, String password, String table) throws SQLException {

            ConnectionInfo connection = new ConnectionInfo();
            Connection conn = connection.Info(url, owner, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from "+ table);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int coloumnsLen = rsMetaData.getColumnCount();
            List<String> column_names = new ArrayList<String>();
            for (int i = 1; i<=coloumnsLen; i++) {
                column_names.add(rsMetaData.getColumnName(i));

            }

            return column_names;
        }
}



