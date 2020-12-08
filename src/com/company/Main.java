package com.company;

import static com.company.TableManager.createTables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Our Main class for the benchmark database.
 */
public class Main {
    /**
     * Our main function that gets called when program is executed.
     *
     * @param args String of supplied command-line-arguments
     */
    public static void main(final String[] args) {
        String url = "jdbc:mysql://localhost/bench_database";
        String username = "root";
        String password = "";
        Connection myConnection = openSqlCon(url, username, password);
        createTables(myConnection);
        try {
            myConnection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Try's to Open the SQL Connection.
     *
     * @param url      Url to Database
     * @param username name of the user
     * @param password password of the user
     * @return the Connection
     */
    public static Connection openSqlCon(final String url, final String username, final String password) {
        System.out.println("Connecting database...");
        try {
            Connection connection = DriverManager.getConnection(
                    url, username, password);
            System.out.println("Database connected!");
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Cannot connect to the database!", e);
        }
    }
}
