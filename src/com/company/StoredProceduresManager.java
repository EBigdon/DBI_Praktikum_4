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

    public static void createTestProcedure() {
        String toCreate = "CREATE PROCEDURE `testProcedure`(IN `balanceToCheck` INT) NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER SELECT branchid, balance FROM `branches` WHERE balance < balanceToCheck ORDER BY `balance` ASC";
    }
}
