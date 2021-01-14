package com.company;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility Class to call stored procedures.
 */
public class StoredProceduresManager {
    /**
     * Connection to database to use.
     */
    private static Connection conn;

    /**
     * Constructor of Class.
     *
     * @param connection Connection to set.
     */
    public StoredProceduresManager(final Connection connection) {
        conn = connection;
    }

    /**
     * Methode to call the deposit procedure.
     *
     * @param accid         the account id
     * @param tellerid      the teller id
     * @param branchid      the branch id
     * @param depositAmount delta
     * @return int
     */
    public static int depositProcedure(final int accid,
                                       final int tellerid,
                                       final int branchid,
                                       final int depositAmount) {
        String cmmnt = "DEP:" + depositAmount + ";BAL:"
                + (balanceProcedure(accid) + depositAmount)
                + ";ACC:" + accid + ";TEL:" + tellerid + ";BRA:"
                + branchid + ".";
        int newBal = (balanceProcedure(accid) + depositAmount);
        try {
            CallableStatement stmt = conn.prepareCall(
                    "{call depositProcedure(" + accid + ","
                            + depositAmount + ","
                            + branchid + ","
                            + tellerid + ","
                            + newBal + ",'"
                            + cmmnt.substring(0, 30)
                            + "')}");
            boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet res = stmt.getResultSet();
                if (res.next()) {
                    return res.getInt("balance");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            String query = "ROLLBACK;";
            try {
                Statement updateStmt = conn.createStatement();
                updateStmt.addBatch(query);
                updateStmt.executeBatch();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * Methode to call the balance procedure.
     *
     * @param accountid the account id
     * @return balance of account
     */
    public static int balanceProcedure(final int accountid) {
        try {
            CallableStatement stmt = conn.prepareCall(
                    "{call balanceProcedure(" + accountid + ")}");
            boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet res = stmt.getResultSet();
                if (res.next()) {
                    return res.getInt("balance");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * Methode to call the analyse procedure.
     *
     * @param depositAmount delta
     * @return number of deposits with this deposit amount.
     */
    public static int analyseProcedure(final float depositAmount) {
        try {
            CallableStatement stmt = conn.prepareCall(
                    "{call analyseProcedure(" + depositAmount + " )}");
            boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet res = stmt.getResultSet();
                if (res.next()) {
                    return res.getInt("Anz");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * Methode with sql commands to create Stored Procedures..
     */
    public static void stringsToCreateProcedures() {
        String analyse = "CREATE PROCEDURE `analyseProcedure`(IN `depositAmount` INT) COMMENT 'represents the analyse transaction' NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER BEGIN SELECT Count(accid) as Anz FROM history WHERE delta = depositAmount GROUP BY delta; END";
        String balance = "CREATE PROCEDURE `balanceProcedure`(IN `accountid` INT) COMMENT 'represents the balance transaction' NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER BEGIN SELECT balance FROM accounts WHERE accid = accountid; END";
        String deposit = "CREATE PROCEDURE `depositProcedure`(IN `accountId` INT, IN `depositAmount` INT, IN `braId` INT, IN `telId` INT, IN `newBal` INT, IN `comm` CHAR(30)) COMMENT 'represents deposit transaction' NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER BEGIN UPDATE accounts SET balance = (SELECT balance FROM accounts WHERE accid = accountId) + depositAmount WHERE accid = accountId; UPDATE branches SET balance = (SELECT balance FROM branches WHERE branchid = braId) + depositAmount WHERE branchid = braId; UPDATE tellers SET balance = (SELECT balance FROM tellers WHERE tellerid = telId) + depositAmount WHERE tellerid = telId; INSERT INTO history (accid, tellerid, delta, branchid, accbalance, history.cmmnt) VALUES (accountId,telId,depositAmount,braId,newBal,comm); END";
    }
}
