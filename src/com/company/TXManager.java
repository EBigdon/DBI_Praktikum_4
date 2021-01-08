package com.company;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import static com.company.TableManager.openSqlCon;

public class TXManager {
    /**
     * Connection.
     */
    private static Connection conn;

    TXManager() {
        String url = "jdbc:mysql://localhost/bench_database";
        String username = "root";
        String password = "";
        conn = openSqlCon(url, username, password);
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

    private static int branchBalanceTx(final int branchid) throws Exception {
        String query = "SELECT balance FROM branches WHERE branchid = "
                + branchid;
        ResultSet rs = executeQuery(query);
        rs.next();
        return rs.getInt("balance");
    }

    private static int tellerBalanceTx(final int tellerid) throws Exception {
        String query = "SELECT balance FROM tellers WHERE tellerid = "
                + tellerid;
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
        int accBalance;
        int branchBalance;
        int tellerBalance;

        //read
        try {
            accBalance = balanceTx(accid);
            branchBalance = branchBalanceTx(branchid);
            tellerBalance = tellerBalanceTx(tellerid);

        } catch (Exception e) {
            throw new Exception("read_exception");
        }

        //process
        int newAccBalance = accBalance + depositAmount;
        int newBranchBalance = branchBalance + depositAmount;
        int newTellerBalance = tellerBalance + depositAmount;

        //write
        try {
            PreparedStatement historyStmt = conn.prepareStatement(
                    "INSERT INTO history (accid, tellerid,"
                            + " delta, branchid, accbalance, cmmnt) "
                            + "VALUES (?, ?, ?, ?, ?, ?)");

            String query;
            query = "UPDATE accounts SET balance = '" + newAccBalance
                    + "' WHERE accid = '" + accid + "'";
            updateQuery(query);
            query = "UPDATE branches SET balance = '" + newBranchBalance
                    + "' WHERE branchid = '" + branchid + "'";
            updateQuery(query);
            query = "UPDATE tellers SET balance = '" + newTellerBalance
                    + "' WHERE tellerid = '" + tellerid + "'";
            updateQuery(query);
            String cmmnt = "DEP:" + depositAmount + ";BAL:" + newAccBalance
                    + ";ACC:" + accid + ";TEL:" + tellerid + ";BRA:"
                    + branchid + ".";

            historyStmt.setInt(1, accid);
            historyStmt.setInt(2, tellerid);
            historyStmt.setInt(3, depositAmount);
            historyStmt.setInt(4, branchid);
            historyStmt.setInt(5, newAccBalance);
            historyStmt.setString(6, cmmnt.substring(0, 30));
            historyStmt.execute();

        } catch (Exception e) {
            throw new Exception("write_exception");
        }
        return newAccBalance;
    }

    /**
     * returns value of transactions with depositAmount value.
     * @param depositAmount DELTA
     * @return Number of previously logged deposits with exactly this amount
     */
    public static int analyseTx(final float depositAmount) throws Exception {
        String query = "SELECT Count(accid) as Anz FROM history WHERE delta = "
                + depositAmount + " GROUP BY delta";
        ResultSet rs = executeQuery(query);
        if(rs.next()) {
            return rs.getInt("Anz");
        }
        return 0;
    }

    private static ResultSet executeQuery(final String query) throws Exception {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery(query);
        } catch (SQLException e) {
            throw new Exception("write_exception");
        }
    }

    private static void updateQuery(final String query) throws Exception {
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);

        } catch (SQLException e) {
            throw new Exception("read_exception");
        }
    }
}
