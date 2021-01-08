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
    public static int balanceTx(final int accid) throws SQLException {

        ResultSet rs = getBalance(accid, "acc");

       while(rs.next()){
            return rs.getInt("balance");
       }
        return 0;
    }

    private static int branchBalanceTx(int branchid) throws SQLException{

        ResultSet rs = getBalance(branchid, "branch");
        while(rs.next()){
            return rs.getInt("balance");
        }
        return 0;
    }

    private static int tellerBalanceTx(int tellerid) throws SQLException{

        ResultSet rs = getBalance(tellerid, "teller");
        while(rs.next()){
            return rs.getInt("balance");
        }
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
    public static int depositTx(final int accid, final int tellerid, final int branchid, final int depositAmount) throws SQLException{
        PreparedStatement historyStmt = conn.prepareStatement(
                "INSERT INTO history (accid, tellerid,"
                        + " delta, branchid, accbalance, cmmnt) VALUES (?, ?, ?, ?, ?, ?)");

        int accBalance = balanceTx(accid);
        int branchBalance = branchBalanceTx(branchid);
        int tellerBalance = tellerBalanceTx(tellerid);

        int newAccBalance = accBalance + depositAmount;
        int newBranchBalance = branchBalance + depositAmount;
        int newTellerBalance = tellerBalance + depositAmount;

        String query;

        query = "UPDATE accounts SET balance = '" + newAccBalance + "' WHERE accid = '" + accid + "'";
        updateQuery(query);
        query = "UPDATE branches SET balance = '" + newBranchBalance + "' WHERE branchid = '" + branchid + "'";
        updateQuery(query);
        query = "UPDATE tellers SET balance = '" + newTellerBalance + "' WHERE tellerid = '" + tellerid + "'";
        updateQuery(query);
        String cmmnt="DEP:" + depositAmount + ";BAL:" + newAccBalance + ";ACC:" + accid + ";TEL:" + tellerid + ";BRA:" + branchid +".";
        historyStmt.setInt(1, accid);
        historyStmt.setInt(2, tellerid);
        historyStmt.setInt(3, depositAmount);
        historyStmt.setInt(4, branchid);
        historyStmt.setInt(5, newAccBalance);
        historyStmt.setString(6, cmmnt.substring(0, 30));
        historyStmt.execute();

        return newAccBalance;
    }

    /**
     * returns value of transactions with depositAmount value.
     * @param depositAmount DELTA
     * @return Number of previously logged deposits with exactly this amount
     */
    public static int analyseTx(final float depositAmount) {
        return 1;
    }

    private static ResultSet getBalance(int id, String type){

        String query = null;

        if(type == "acc"){
            query = "SELECT balance FROM accounts WHERE accid = " + id;
        }else if(type == "branch"){
            query = "SELECT balance FROM branches WHERE branchid = " + id;
        }else if(type == "teller"){
            query = "SELECT balance FROM tellers WHERE tellerid = " + id;
        }else{
            query = null;
        }

        ResultSet rs = executeQuery(query);
        return rs;

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

    private static void updateQuery(final String query){
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println("ERROR while executing querry:" + query);
            e.printStackTrace();
        }
    }
}
