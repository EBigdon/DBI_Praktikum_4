package com.company;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("SpellCheckingInspection")
public class TableManager {

    /**
     * Creates the tables needed for the test.
     *
     * @param con the Connection to the Database
     */
    public static void createTables(final Connection con) {
        createBranches(con);
        createAccounts(con);
        createTellers(con);
        createHistory(con);
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
}

