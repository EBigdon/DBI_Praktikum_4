package progam;

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
}
