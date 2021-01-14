package com.company;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StoredProceduresManager {
    private static Connection conn;

    public StoredProceduresManager(Connection conn) {
        this.conn = conn;
        analyseProcedure(100);
        balanceProcedure(519847);
        depositProcedure(519847, 111, 90
                , 200);
    }

    public static int depositProcedure(final int accid, final int tellerid,
                                       final int branchid, final int depositAmount) {
        String cmmnt = "DEP:" + depositAmount + ";BAL:"
                + (balanceProcedure(accid) + depositAmount)
                + ";ACC:" + accid + ";TEL:" + tellerid + ";BRA:"
                + branchid + ".";
        int newBal = (balanceProcedure(accid) + depositAmount);
        try {
            CallableStatement stmt = conn.prepareCall("{call depositProcedure(" + accid + ","
                    + depositAmount + ","
                    + branchid + ","
                    + tellerid + ","
                    + newBal + ",'"
                    + cmmnt.substring(0, 30)
                    + "')}");
            Boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet res = stmt.getResultSet();
                while (res.next()) {
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    public static int balanceProcedure(final int accountid) {
        try {
            CallableStatement stmt = conn.prepareCall("{call balanceProcedure(" + accountid + ")}");
            Boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet res = stmt.getResultSet();
                while (res.next()) {
                    return res.getInt("balance");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    public static int analyseProcedure(final int depositAmount) {
        try {
            CallableStatement stmt = conn.prepareCall("{call analyseProcedure(" + depositAmount + " )}");
            Boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet res = stmt.getResultSet();
                while (res.next()) {
                    return res.getInt("Anz");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static void StringsToCreateProcedures() {
        String analyse = "CREATE PROCEDURE `analyseProcedure`(IN `depositAmount` INT) COMMENT 'represents the analyse transaction' NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER BEGIN SELECT Count(accid) as Anz FROM history WHERE delta = depositAmount GROUP BY delta; END";
        String balance = "CREATE PROCEDURE `balanceProcedure`(IN `accountid` INT) COMMENT 'represents the balance transaction' NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER BEGIN SELECT balance FROM accounts WHERE accid = accountid; END";
        String deposit = "CREATE PROCEDURE `depositProcedure`(IN `accountId` INT, IN `depositAmount` INT, IN `braId` INT, IN `telId` INT, IN `newBal` INT, IN `comm` CHAR(30)) COMMENT 'represents deposit transaction' NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER BEGIN UPDATE accounts SET balance = (SELECT balance FROM accounts WHERE accid = accountId) + depositAmount WHERE accid = accountId; UPDATE branches SET balance = (SELECT balance FROM branches WHERE branchid = braId) + depositAmount WHERE branchid = braId; UPDATE tellers SET balance = (SELECT balance FROM tellers WHERE tellerid = telId) + depositAmount WHERE tellerid = telId; INSERT INTO history (accid, tellerid, delta, branchid, accbalance, history.cmmnt) VALUES (accountId,telId,depositAmount,braId,newBal,comm); END";
    }
}
