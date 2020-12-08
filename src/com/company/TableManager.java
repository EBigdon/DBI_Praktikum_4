package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("SpellCheckingInspection")
public class TableManager {

    /**
     * Connection.
     */
    private static Connection conn;

    TableManager() {
        String url = "jdbc:mysql://localhost/bench_database";
        String username = "root";
        String password = "";
        conn = openSqlCon(url, username, password);
    }

    /**
     * Creates the tables needed for the test.
     */
    public static void createTables() {
        createBranches(conn);
        createAccounts(conn);
        createTellers(conn);
        createHistory(conn);
    }

    /**
     * creates the Branches Table.
     *
     * @param con the connection to the database.
     */
    private static void createBranches(final Connection con) {
        String query = """
                create table branches
                ( branchid int not null,
                 branchname char(20) not null,
                 balance int not null,
                 address char(72) not null,
                 primary key (branchid) );""";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table branches created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates the Accounts Table.
     *
     * @param con the connection to the database.
     */
    private static void createAccounts(final Connection con) {
        String query = """
                create table accounts
                ( accid int not null,
                 name char(20) not null,
                 balance int not null,
                branchid int not null,
                address char(68) not null,
                primary key (accid),
                foreign key (branchid) references
                bench_database.branches(branchid)
                );""";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table Accounts created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates the Tellers Table.
     *
     * @param con the connection to the database.
     */
    private static void createTellers(final Connection con) {
        String query = """
                create table tellers
                ( tellerid int not null,
                 tellername char(20) not null,
                 balance int not null,
                 branchid int not null,
                 address char(68) not null,
                 primary key (tellerid),
                 foreign key (branchid) references branches(branchid)
                );\s""";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table Tellers created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates the History Table.
     *
     * @param con the connection to the database.
     */
    private static void createHistory(final Connection con) {
        String query = """
                create table history
                ( accid int not null,
                 tellerid int not null,
                 delta int not null,
                 branchid int not null,
                 accbalance int not null,
                 cmmnt char(30) not null,
                 foreign key (accid) references accounts(accid),
                 foreign key (tellerid) references tellers(tellerid),
                 foreign key (branchid) references branches(branchid) );\s""";

        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table history created");

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns Connection of Database.
     *
     * @param url      url of database
     * @param username username to database
     * @param password password to database
     * @return Connection to Database
     */
    public static Connection openSqlCon(final String url,
                                        final String username,
                                        final String password) {
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
    public static void drop_table(){

        String query1 = "SET FOREIGN_KEY_CHECKS = 0; ";

        String query2 = "DROP TABLE IF EXISTS history; ";
        String query3 = "DROP TABLE IF EXISTS accounts; ";
        String query4 = "DROP TABLE IF EXISTS tellers; ";
        String query5 = "DROP TABLE IF EXISTS branches; ";

        try {
            Statement st = conn.createStatement();
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

