package com.company;

import java.util.Scanner;
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
     * @param args String of supplied command-line-arguments
     */
    public static void main(final String[] args) {
        TableManager myDatabase = new TableManager();
        myDatabase.createTables();
        System.out.println("Wieviele Tupel sollen ausgegeben werde?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
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

    public static void drop_table(Connection con){

        String query1 = "SET FOREIGN_KEY_CHECKS = 0; ";

        String query2 = "DROP TABLE IF EXISTS history; ";
        String query3 = "DROP TABLE IF EXISTS accounts; ";
        String query4 = "DROP TABLE IF EXISTS tellers; ";
        String query5 = "DROP TABLE IF EXISTS branches; ";

        try {
            Statement st = con.createStatement();
            st.executeUpdate(query1);
            st.executeUpdate(query2);
            st.executeUpdate(query3);
            st.executeUpdate(query4);
            st.executeUpdate(query5);
            System.out.println("Tables were dropped");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
