package com.company;

import com.mysql.cj.jdbc.PreparedStatementWrapper;

import java.sql.*;
import java.util.concurrent.TimeUnit;

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
        dropTable();
        createBranches();
        createAccounts();
        createTellers();
        createHistory();
    }

    public static void fastFillAccounts(final int n) {
        executeQuerry("SET @@session.unique_checks = 0;");
        executeQuerry("SET @@session.foreign_key_checks = 0;");
        String query = """
                INSERT INTO accounts (`accid`, `name`, `balance`, `branchid`, `address`)
                               SELECT n, SUBSTRING(CONCAT(n,'ABCDEFGHIJKLMNOPQRST'),1,20),n,n,SUBSTRING(CONCAT(n,'ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNO'),1,68)
                                 FROM
                               (
                               select 1*a.N + b.N * 10 + c.N * 100 + d.N * 1000 + e.N * 10000 +  """ + (n*100000+1) + " " + """
                                    N
                               from (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) a
                                     , (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) b
                                     , (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) c
                                     , (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) d
                                   , (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) e
                               order by n
                               ) t""";
        executeQuerry(query);
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
        try {
            Connection connection = DriverManager.getConnection(
                    url, username, password);
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

    public static void fillDatabase(final int n) {
        String fillString = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKL";
        try {
            PreparedStatement branchstmt = conn.prepareStatement("INSERT INTO `branches` (`branchid`, `branchname`, `balance`, `address`) VALUES (?, ?, ?, ?)");
            PreparedStatement accountstmt = conn.prepareStatement("INSERT INTO `accounts` (`accid`, `name`, `balance`, `branchid`, `address`) VALUES (?, ?, ?, ?, ?)");
            PreparedStatement tellerstmt = conn.prepareStatement("INSERT INTO `tellers` (`tellerid`, `tellername`, `balance`, `branchid`,`address`) VALUES (?, ?, ?, ?, ?)");

            int acccounter = 0;
            int tellercounter = 0;

            for (int i = 0; i < 1 * n; i++) {
                branchstmt.setInt(1, i);
                branchstmt.setString(2, fillString.substring(0,20));
                branchstmt.setInt(3, 0);
                branchstmt.setString(4, fillString.substring(0,72));
                branchstmt.execute();


                //Accounts
                for (int j = 0;j < 100000;j++) {
                    accountstmt.setInt(1, acccounter);
                    accountstmt.setString(2, fillString.substring(0,20));
                    accountstmt.setInt(3, 0);
                    accountstmt.setInt(4, i);
                    accountstmt.setString(5, fillString.substring(0,68));
                    accountstmt.execute();
                    acccounter++;
                }
                //tellers
                for (int j = 0;j< 10;j++) {
                    tellerstmt.setInt(1, tellercounter);
                    tellerstmt.setString(2, fillString.substring(0,20));
                    tellerstmt.setInt(3, 0);
                    tellerstmt.setInt(4, i);
                    tellerstmt.setString(5, fillString.substring(0,68));
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
    public static void waitTillDatabaseFinished(final int n) throws SQLException, InterruptedException {

        PreparedStatement checkCount = conn.prepareStatement("SELECT Count(*) FROM `accounts` ");
        while(true) {
            ResultSet myRes = checkCount.executeQuery();
            while(!myRes.next()) {
                if(myRes.getInt(1) == 100000 * n) {
                    return;
                }
            }
            //TimeUnit.SECONDS.sleep(1);
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex)  {
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void closeConnection() throws SQLException {
        conn.close();
    }
}

