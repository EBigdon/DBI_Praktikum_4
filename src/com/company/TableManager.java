package com.company;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import static com.company.ClearTables.clearTables;

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
        clearTables();
    }

    private static void fillAccounts(final int n) throws SQLException {
        preparedQuery("SET @@session.unique_checks = 0;");
        preparedQuery("SET @@session.foreign_key_checks = 0;");
        String query = """
               INSERT INTO accounts (`accid`, `name`, `balance`, `branchid`, `address`)
               SELECT n, SUBSTRING(CONCAT(n,'ABCDEFGHIJKLMNOPQRST'),1,20),n,n,SUBSTRING(CONCAT(n,'ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNO'),1,68)
               FROM
               (
               select 1*a.N + b.N * 10 + c.N * 100 + d.N * 1000 + e.N * 10000 +  """
                + (n * 100000 + 1) + " " + """
               N
               from (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) a
                      , (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) b
                      , (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) c
                      , (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) d
                    , (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) e
                order by n
                ) t""";
        preparedQuery(query);
    }


    /**
     * Returns Connection of Database.
     *
     * @param url      url of database
     * @param username username to database
     * @param password password to database
     * @return Connection to Database
     */
    static Connection openSqlCon(
            final String url, final String username, final String password) {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Cannot connect to the database!", e);
        }
    }

    /**
     * Fills our Database with the Tupel.
     * @param n our n-tps count.
     * @throws SQLException Our SQLException when trying to fill tables.
     */
    public static void fillDatabase(final int n) throws SQLException {

        String fillString = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKL";
        PreparedStatement branchstmt = conn.prepareStatement(
                "INSERT INTO `branches` (`branchid`, `branchname`,"
                        + " `balance`, `address`) VALUES (?, ?, ?, ?)");
        PreparedStatement tellerstmt = conn.prepareStatement(
                "INSERT INTO `tellers` (`tellerid`, `tellername`,"
                        + " `balance`, `branchid`,`address`) VALUES (?, ?, ?, ?, ?)");
        int tellercounter = 0;
        for (int i = 0; i < n; i++) {
            branchstmt.setInt(1, i);
            branchstmt.setString(2, fillString.substring(0, 20));
            branchstmt.setInt(3, 0);
            branchstmt.setString(4, fillString.substring(0, 72));
            branchstmt.execute();
            fillAccounts(i);
            //tellers
            for (int j = 0; j < 10; j++) {
                tellerstmt.setInt(1, tellercounter);
                tellerstmt.setString(2, fillString.substring(0, 20));
                tellerstmt.setInt(3, 0);
                tellerstmt.setInt(4, i);
                tellerstmt.setString(5, fillString.substring(0, 68));
                tellerstmt.execute();
                tellercounter++;
            }
        }
    }

    /**
     * Takes a Query and executes it by a prepared Statement.
     * @param query the sql query
     * @throws SQLException the Exception
     */
    public static void preparedQuery(final String query) throws SQLException {
        PreparedStatement myQuery = conn.prepareStatement(query);
        myQuery.execute();
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

