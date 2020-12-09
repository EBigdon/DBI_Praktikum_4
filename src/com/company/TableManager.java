package com.company;

import com.mysql.cj.jdbc.PreparedStatementWrapper;

import java.sql.*;

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
        createTables();
    }

    /**
     * Creates the tables needed for the test.
     */
    public static void createTables() {
        dropTable();
        createBranches();
        createAccounts();
        createTellers();
        createHistory();
    }

    /**
     * creates the Branches Table.
     */
    private static void createBranches() {
        String query = """
                create table branches
                ( branchid int not null,
                 branchname char(20) not null,
                 balance int not null,
                 address char(72) not null,
                 primary key (branchid) );""";
        executeQuerry(query);
    }

    /**
     * creates the Accounts Table.
     */
    private static void createAccounts() {
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
        executeQuerry(query);
    }

    /**
     * creates the Tellers Table.
     */
    private static void createTellers() {
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
        executeQuerry(query);
    }

    /**
     * creates the History Table.
     */
    private static void createHistory() {
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
        executeQuerry(query);
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

    /**
     * drops the tables.
     */
    public static void dropTable() {
        executeQuerry("DROP TABLE IF EXISTS history; ");
        executeQuerry("DROP TABLE IF EXISTS accounts; ");
        executeQuerry("DROP TABLE IF EXISTS tellers; ");
        executeQuerry("DROP TABLE IF EXISTS branches; ");
    }

    public static void fillDatabase(int n){

        String fillString = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKL" +
                "RSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJK";
        try {
            PreparedStatement branchstmt = conn.prepareStatement("INSERT INTO `branches` (`branchid`, `branchname`, `balance`, `address`) VALUES (?, ?, ?, ?)");
            PreparedStatement accountstmt = conn.prepareStatement("INSERT INTO `accounts` (`accid`, `name`, `balance`, `branchid`, `address`) VALUES (?, ?, ?, ?, ?)");
            PreparedStatement tellerstmt = conn.prepareStatement("INSERT INTO `tellers` (`tellerid`, `tellername`, `balance`, `branchid`,`address`) VALUES (?, ?, ?, ?, ?)");

            int counter = 0;
            int acccounter = 0;
            int tellercounter = 0;

            for (int i = 0; i < 1 * n; i++) {
                String name = Integer.toString(i) + fillString.substring(0, 20 - Integer.toString(i).length());
                int branchId = counter;
                int balance = 0;
                String address = Integer.toString(i) + fillString.substring(0, 72 - Integer.toString(i).length());

                branchstmt.setInt(1, branchId);
                branchstmt.setString(2, name);
                branchstmt.setInt(3, balance);
                branchstmt.setString(4, address);
                branchstmt.execute();
                counter++;

                //Accounts
                for (int j = 0;j < 100000;j++) {
                    String accname = Integer.toString(j) + fillString.substring(0, 20 - Integer.toString(j).length());
                    int accId = acccounter;
                    int accbalance = 0;
                    String accaddress = Integer.toString(j) + fillString.substring(0,68 - Integer.toString(j).length());

                    accountstmt.setInt(1, accId);
                    accountstmt.setString(2, accname);
                    accountstmt.setInt(3, accbalance);
                    accountstmt.setInt(4, branchId);
                    accountstmt.setString(5, accaddress);
                    accountstmt.execute();
                    acccounter++;
                }
                //tellers
                for (int j = 0;j< 10;j++) {
                    int tellerid = tellercounter;
                    String tellername = Integer.toString(j) + fillString.substring(0, 20 - Integer.toString(j).length());
                    int tellerbalance = 0;
                    String telleraddress = Integer.toString(j) + fillString.substring(0,68 - Integer.toString(j).length());

                    tellerstmt.setInt(1, tellerid);
                    tellerstmt.setString(2, tellername);
                    tellerstmt.setInt(3, tellerbalance);
                    tellerstmt.setInt(4, branchId);
                    tellerstmt.setString(5, telleraddress);
                    tellerstmt.execute();
                    tellercounter++;
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR while executing querry");
            e.printStackTrace();
        }

    }

    /**
     * executes given querry.
     *
     * @param query our querry
     */
    public static void executeQuerry(final String query) {
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("ERROR while executing querry:" + query);
            e.printStackTrace();
        }
    }
}

