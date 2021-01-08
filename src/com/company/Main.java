package com.company;

import java.sql.SQLException;
import java.util.Scanner;

        /**
        * Our Main class for the benchmark database.
        */
@SuppressWarnings({"SpellCheckingInspection","MagicNumber"})
public class Main {
    /**
     * Instance of TXManager.
     */
    private static final TXManager txManager = new TXManager();

    /**
     * Our main function that gets called when program is executed.
     *
     * @param args String of supplied command-line-arguments
     */
    public static void main(final String[] args) throws Exception {

        try{
            int n = fillDatabasePls();
            final int transactions = 10000;
            long startTransactions = System.currentTimeMillis();
            for (int i = 0; i < transactions; i++) {
                int randomAccid = (int)
                        (Math.random() * ((n + 1) * 100000 - 100001 + 1) + 1);
                int randomTellerid = (int) (Math.random() * (n * 10) + 1);
                int randomBranchid = (randomTellerid % n) + 1;
                int randomDelta = (int) (Math.random() * 1000 + 1);
                depositTX(randomAccid, randomTellerid,
                        randomBranchid, randomDelta);
            }
            long finishTransactions = System.currentTimeMillis();
            long timeElapsedTransactions = finishTransactions - startTransactions;
            System.out.println("Laufzeit in Millisekunden: "
                    + timeElapsedTransactions);
            System.out.println(transactions
                    / (timeElapsedTransactions / 1000) + " TX/s");

        }catch(Exception e) {
            if (e.toString() == "read_exception") {
                //not yet implemented
            } else if (e.toString() == "write_exception") {
                //not yet implemented
            } else {
                System.out.println(e);
            }
        }
    }

    /**
     * Asks if we should reset the database or not.
     *
     * @return the n value or, if 0 is inserted,
     * returns the expected value of 100.
     * @throws SQLException throws SQL-Exception.
     */
    public static int fillDatabasePls() throws SQLException {
        System.out.println("Welcher Skalierungsfaktor soll verwendet werden?"
                + "(0 um aktuellen Zustand beizubehalten):");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        TableManager tableManager = new TableManager();
        if (n != 0) {
            tableManager.createTables();
            long start = System.currentTimeMillis();
            tableManager.fillDatabase(n);
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("Laufzeit in Millisekunden: " + timeElapsed);
            return n;
        } else {
            tableManager.clearHistory();
            return 100;
        }
    }

    /**
     * Deposits a amount into our database.
     *
     * @param accid    Account Id
     * @param tellerid Teller Id
     * @param branchid Branch Id
     * @param delta    Inserted Money
     * @return returns an int value
     */
    public static int depositTX(final int accid, final int tellerid,
                                final int branchid, final int delta) {
        try {
            return txManager.depositTx(accid, tellerid, branchid, delta);
        } catch (Exception e) {
            System.out.println("Exception while deposit Money: " + e);
            System.out.println(accid + " " + tellerid + " "
                    + branchid + " " + delta);
        }
        return 0;
    }

    public static int balanceTX(int accid)throws Exception{
        return txManager.balanceTx(accid);
    }

    public static int analyseTX(int delta)throws Exception{
        return txManager.analyseTx(delta);
    }

    
}
