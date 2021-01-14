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

    /**
     * Constructor of Table Manager.
     */
    TableManager() {
        conn = openSqlCon(Parameters.url, Parameters.username,
                Parameters.password);
        String analyse = """
                CREATE PROCEDURE IF NOT EXISTS `analyseProcedure`(
                    IN `depositAmount` INT
                ) COMMENT 'represents the analyse transaction'
                NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER
                BEGIN
                    SELECT COUNT(accid) AS Anz
                    FROM history
                    WHERE delta = depositAmount
                    GROUP BY delta;
                END
                """;
        String balance = """
                CREATE PROCEDURE IF NOT EXISTS `balanceProcedure`(
                    IN `accountid` INT
                ) COMMENT 'represents the balance transaction'\s
                    NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER
                BEGIN
                    SELECT balance
                    FROM accounts
                    WHERE accid = accountid;
                END
                """;
        String deposit = """
                CREATE PROCEDURE IF NOT EXISTS `depositProcedure`(
                    IN `accountId` INT,
                    IN `depositAmount` INT,
                    IN `braId` INT,
                    IN `telId` INT,
                    IN `newBal` INT,
                    IN `comm` CHAR(30)
                ) COMMENT 'represents deposit transaction' 
                NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER
                BEGIN
                    UPDATE accounts
                    SET balance = (
                        SELECT balance
                        FROM accounts
                        WHERE accid = accountId
                    ) + depositAmount
                    WHERE accid = accountId;
                    UPDATE branches
                    SET balance = (
                        SELECT balance
                        FROM branches
                        WHERE branchid = braId
                    ) + depositAmount
                    WHERE branchid = braId;
                    UPDATE tellers
                    SET balance = (
                        SELECT balance
                        FROM tellers
                        WHERE tellerid = telId
                    ) + depositAmount
                    WHERE tellerid = telId;
                    INSERT INTO history(
                        accid,
                        tellerid,
                        delta,
                        branchid,
                        accbalance,
                        cmmnt
                    )
                    VALUES(
                        accountId,
                        telId,
                        depositAmount,
                        braId,
                        newBal,
                        comm
                    );
                END
                """;
        try {
            System.out.println(executeUpdate(analyse));
            System.out.println(executeUpdate(balance));
            System.out.println(executeUpdate(deposit));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * executes update query to database.
     *
     * @param query query to execute
     * @return returns table
     * @throws Exception throws exception when error in query
     */
    private static int executeUpdate(final String query) throws Exception {
        Statement st = conn.createStatement();
        return st.executeUpdate(query);
    }

    /**
     * Creates the tables needed for the test.
     */
    public static void createTables() {
        clearTables();
    }

    private static void fillAccounts(final int currentPos)
            throws SQLException {
        preparedQuery("SET @@session.unique_checks = 0;");
        preparedQuery("SET @@session.foreign_key_checks = 0;");
        String query = """
                INSERT INTO accounts (
                     `accid`,
                     `name`,
                     `balance`,
                     `branchid`,
                     `address`
                )
                SELECT n,
                 SUBSTRING(CONCAT(n, 'ABCDEFGHIJKLMNOPQRST'),1,20),
                 0,
                (SELECT FLOOR(RAND() *(
                """
                + Parameters.n + """
                )+1)),SUBSTRING(CONCAT(n,
                'ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNO'
                ),1,68)
                FROM
                (
                select 1*a.N + b.N * 10 + c.N * 100 + d.N * 1000
                + e.N * 10000 +
                """
                + ((currentPos - 1) * 100000 + 1) + " " + """
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
     *
     * @param n our n-tps count.
     * @throws SQLException Our SQLException when trying to fill tables.
     */
    public static void fillDatabase(final int n) throws SQLException {

        String fillString = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKL";
        PreparedStatement branchstmt = conn.prepareStatement(
                "INSERT INTO branches (branchid, branchname,"
                        + " balance, address) VALUES (?, ?, ?, ?)");
        PreparedStatement tellerstmt = conn.prepareStatement(
                "INSERT INTO tellers (tellerid, tellername,"
                        + " balance, branchid,address) VALUES (?, ?, ?, ?, ?)");
        int tellercounter = 1;
        for (int i = 1; i <= n; i++) {
            branchstmt.setInt(1, i);
            branchstmt.setString(2, fillString.substring(0, 20));
            branchstmt.setInt(3, 0);
            branchstmt.setString(4, fillString.substring(0, 72));
            branchstmt.execute();
            fillAccounts(i);
            //tellers
            for (int j = 1; j <= 10; j++) {
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
     *
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

    /**
     * Clears the History table.
     */
    public static void clearHistory() {
        ClearTables.clearHistory();
    }
}
