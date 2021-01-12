package com.company;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;

import static com.company.TableManager.openSqlCon;

public class TXManager {
    /**
     * Connection.
     */
    private static Connection conn;
    /**
     * Our Transaction Statement.
     */
    public static Statement updateStmt = null;

    /**
     * Constructor of TXManager class.
     */
    public TXManager() {
        conn = openSqlCon(Parameters.url, Parameters.username,
                Parameters.password);
        try {
            updateStmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Couldn't get Connection in TXManager: " + e);
        }
    }

    /**
     * Gets balance from Database with corresponding account id.
     *
     * @param accid id of account
     * @return balance of account
     */
    public static int balanceTx(final int accid) throws Exception {
        String query = "SELECT balance FROM accounts WHERE accid = " + accid;
        ResultSet rs = executeQuery(query);
        rs.next();
        return rs.getInt("balance");
    }

    /**
     * updates Balance of Tellers, Accounts and History with corresponding IDs.
     *
     * @param accid         account id
     * @param tellerid      tellers id
     * @param branchid      branch id
     * @param depositAmount amount which updates the balances.
     * @return updated balance
     */
    public static int depositTx(final int accid, final int tellerid,
                                final int branchid, final int depositAmount)
            throws Exception {

        conn.setAutoCommit(false);

        String cmmnt = "DEP:" + depositAmount + ";BAL:"
                + (balanceTx(accid) + depositAmount)
                + ";ACC:" + accid + ";TEL:" + tellerid + ";BRA:"
                + branchid + ".";

        String historyStmt =
                "INSERT INTO history (accid, tellerid,"
                        + " delta, branchid, accbalance, cmmnt) "
                        + "VALUES ('" + accid + "', '" + tellerid + "', '"
                        + depositAmount + "', '"
                        + branchid + "', '" + (balanceTx(accid) + depositAmount)
                        + "', '" + cmmnt.substring(0, 30) + "');";


        String query1 = "UPDATE accounts SET balance = "
                + "(SELECT balance FROM accounts WHERE accid = '"
                + accid + "') + '" + depositAmount
                + "' WHERE accid = '" + accid + "';";
        String query2 = "UPDATE branches SET balance = "
                + "(SELECT balance FROM branches WHERE branchid = '"
                + branchid + "')+ '" + depositAmount
                + "' WHERE branchid = '" + branchid + "';";
        String query3 = "UPDATE tellers  SET balance = "
                + "(SELECT balance FROM tellers WHERE tellerid = '"
                + tellerid + "')+ '" + depositAmount
                + "' WHERE tellerid = '" + tellerid + "';";


        updateStmt.addBatch(query1);
        updateStmt.addBatch(query2);
        updateStmt.addBatch(query3);
        updateStmt.addBatch(historyStmt);

        return balanceTx(accid);
    }

    /**
     * returns value of transactions with depositAmount value.
     *
     * @param depositAmount DELTA
     * @return Number of previously logged deposits with exactly this amount
     */
    public static int analyseTx(final float depositAmount) throws Exception {
        String query = "SELECT Count(accid) as Anz FROM history WHERE delta = "
                + depositAmount + " GROUP BY delta";
        ResultSet rs = executeQuery(query);
        if (rs.next()) {
            return rs.getInt("Anz");
        }
        return 0;
    }

    /**
     * Adds Transaction Start query to database.
     */
    public static void sqlTransaction() {
        try {
            String query = "START TRANSACTION;";
            updateStmt.addBatch(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Rollback database when there was an error.
     */
    public static void sqlRollback() {
        try {
            String query = "ROLLBACK;";
            updateStmt.addBatch(query);
            updateStmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Commits Batch to database.
     */
    public static void sqlCommit() {
        try {
            String query = "COMMIT;";
            updateStmt.addBatch(query);
            updateStmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * exectutes Query to database.
     *
     * @param query query to execute
     * @return returns table
     * @throws Exception throws exception when error in query
     */
    private static ResultSet executeQuery(final String query) throws Exception {

        Statement st = conn.createStatement();
        return st.executeQuery(query);

    }
}
