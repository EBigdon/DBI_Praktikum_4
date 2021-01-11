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
    public static Statement stmt = null;

    TXManager() {
        conn = openSqlCon(Parameters.url, Parameters.username,
                Parameters.password);
        try{
            stmt = conn.createStatement();
        }catch(Exception e){
            System.out.println(e);
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

        String balance1 = "SELECT balance FROM accounts WHERE accid = '" + accid + "'";
        String balance2 = "SELECT balance FROM branches WHERE branchid = '" + branchid + "'";
        String balance3 = "SELECT balance FROM tellers WHERE tellerid = '" + tellerid + "'";

        stmt.addBatch(balance1);
        stmt.addBatch(balance2);
        stmt.addBatch(balance3);

        String cmmnt = "DEP:" + depositAmount + ";BAL:" + (balanceTx(accid) + depositAmount)
                + ";ACC:" + accid + ";TEL:" + tellerid + ";BRA:"
                + branchid + ".";

        String historyStmt =
                "INSERT INTO history (accid, tellerid,"
                        + " delta, branchid, accbalance, cmmnt) "
                        + "VALUES ('"+ accid +"', '" + tellerid + "', '" + depositAmount + "', '"
                        + branchid + "', '"+ (balanceTx(accid) + depositAmount) + "', '" + cmmnt.substring(0,30) + "')";


        String query1 = "UPDATE accounts as ac SET ac.balance = ac.balance + '" + depositAmount + "'";
        String query2 = "UPDATE branches as br SET br.balance = br.balance + '" + depositAmount + "'";
        String query3 = "UPDATE tellers as te SET te.balance = te.balance + '" + depositAmount + "'";


        stmt.addBatch(query1);
        stmt.addBatch(query2);
        stmt.addBatch(query3);
        stmt.addBatch(historyStmt);

        return balanceTx(accid);
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
        if (rs.next()) {
            return rs.getInt("Anz");
        }
        return 0;
    }
    public static void sql_transaction(){
        try{
            String query = "START TRANSACTION";
            stmt.addBatch(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sql_rollback(){
        try{
            String query = "ROLLBACK";
            stmt.addBatch(query);
            stmt.executeBatch();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sql_commit(){
        try{
            String query = "COMMIT";
            stmt.addBatch(query);
            stmt.executeBatch();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static ResultSet executeQuery(final String query) throws Exception {

        Statement st = conn.createStatement();
        return st.executeQuery(query);

    }

}
