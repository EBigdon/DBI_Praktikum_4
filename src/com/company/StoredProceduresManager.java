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
        System.out.println("\n\n\n\n");
        analyseProcedure(100);
    }

    public static void testProcedure() {
        //SELECT branchid, balance FROM `branches` WHERE balance < 175000 ORDER BY `balance`  ASC
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

    public static void analyseProcedure(final int depositAmount) {
        try {
            CallableStatement stmt = conn.prepareCall("{call analyseProcedure(" + depositAmount + " )}");
            Boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet res = stmt.getResultSet();
                while (res.next()) {
                    System.out.println(res.getInt("Anz"));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void StringsToCreateProcedures() {
        String test = "CREATE PROCEDURE `testProcedure`(IN `balanceToCheck` INT) NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER SELECT branchid, balance FROM `branches` WHERE balance < balanceToCheck ORDER BY `balance` ASC";
        String analyseProcedure = "CREATE PROCEDURE `analyseProcedure`(IN `depositAmount` INT) NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER SELECT Count(accid) as Anz FROM history WHERE delta = depositAmount GROUP BY delta; ";
    }
}
