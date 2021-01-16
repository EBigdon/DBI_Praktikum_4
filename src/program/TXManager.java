package program;

import java.sql.Connection;
import java.sql.Statement;

public class TXManager {
    /**
     * Connection.
     */
    private static Connection conn;
    /**
     * Our Transaction Statement.
     */
    private static Statement updateStmt = null;
    /**
     * Instance of stored procedure manager.
     */
    private static StoredProceduresManager proceduresManager = null;

    /**
     * Constructor of TXManager class.
     */
    public TXManager() {
        conn = TableManager.openSqlCon(Parameters.url, Parameters.username,
                Parameters.password);
        try {
            updateStmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Couldn't get Connection in TXManager: " + e);
        }
        proceduresManager = new StoredProceduresManager(conn);
    }

    /**
     * Gets balance from Database with corresponding account id.
     *
     * @param accid id of account
     * @return balance of account
     */
    public static int balanceTx(final int accid) {
        return proceduresManager.balanceProcedure(accid);
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
                                final int branchid, final int depositAmount) {
        return proceduresManager.depositProcedure(
                accid, tellerid, branchid, depositAmount);
    }

    /**
     * returns value of transactions with depositAmount value.
     *
     * @param depositAmount DELTA
     * @return Number of previously logged deposits with exactly this amount
     */
    public static int analyseTx(final float depositAmount) {
        return proceduresManager.analyseProcedure(depositAmount);
    }

    /**
     * Adds Transaction Start query to database.
     */
    public static void sqlTransaction() {
        try {
            String query1 = "SET foreign_key_checks = 0;";
            String query2 = "START TRANSACTION;";
            updateStmt.addBatch(query1);
            updateStmt.addBatch(query2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Rollback database when there was an error.
     */
    public static void sqlRollback() {
        try {
            String query1 = "ROLLBACK;";
            String query2 = "SET foreign_key_checks = 1;";
            updateStmt.addBatch(query1);
            updateStmt.addBatch(query2);
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
            String query1 = "COMMIT;";
            String query2 = "SET foreign_key_checks = 0;";
            updateStmt.addBatch(query1);
            updateStmt.addBatch(query2);
            updateStmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
