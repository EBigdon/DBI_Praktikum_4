package com.company;

import java.sql.*;

import static com.company.Create_Database.create_database;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/bench_database";
        String username = "dbi";
        String password = "dbi_pass";
        Connection my_connection = get_sql_con(url,username,password);
        create_database(my_connection);
    }


    public static Connection get_sql_con(String url, String username, String password){
        System.out.println("Connecting database...");
        try  {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to the database!", e);
        }
    }
}
