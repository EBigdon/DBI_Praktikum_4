package com.company;

import java.sql.*;

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
    public static float balanceTx(final int accid) throws SQLException {
        String query = "SELECT balance FROM accounts WHERE accid = " + accid;
        ResultSet rs = executeQuery(query);
        while(rs.next()) {
            return rs.getInt("balance");
        }
        System.out.println("\u001B[31mNo Account with accid " + accid + " found.\u001B[0m");
        return 0;
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
    public static float depositTx(final int accid, final int tellerid, final int branchid, final float depositAmount) {
        return (float) 1;
    }

    /**
     * returns value of transactions with depositAmount value.
     * @param depositAmount DELTA
     * @return Number of previously logged deposits with exactly this amount
     */
    public static int analyseTx(final float depositAmount) {
        return 1;
    }

    private static ResultSet executeQuery(final String query) {
        try {
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            System.out.println("ERROR while executing querry:" + query);
            e.printStackTrace();
        }
        return null;
    }
}
