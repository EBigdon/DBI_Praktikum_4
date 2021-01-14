package com.company;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoredProceduresManager {
    private static Connection conn;

    public StoredProceduresManager(Connection conn) {
        this.conn = conn;
        testProcedure();
        analyseProcedure(100);
        balanceProcedure(519847);
    }

    public static void testProcedure() {
        try {
            CallableStatement stmt = conn.prepareCall("{call testProcedure(175000)}");
            Boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet res = stmt.getResultSet();
                while (res.next()) {
                    System.out.println(res.getInt("balance"));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

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
        String test = "CREATE PROCEDURE `testProcedure`(IN `balanceToCheck` INT) COMMENT 'procedure to test stored procedures' NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER Begin SELECT branchid, balance FROM `branches` WHERE balance < balanceToCheck ORDER BY `balance` ASC; END";
        String analyse = "CREATE PROCEDURE `analyseProcedure`(IN `depositAmount` INT) COMMENT 'The analyse transaction' NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER BEGIN SELECT Count(accid) as Anz FROM history WHERE delta = depositAmount GROUP BY delta; END";
        String balance = "CREATE PROCEDURE `balanceProcedure`(IN `accountid` INT) COMMENT 'represents the balance transaction' NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER BEGIN SELECT balance FROM accounts WHERE accid = accountid; END";
    }
}
