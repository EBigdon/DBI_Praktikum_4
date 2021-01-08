package com.company;

import java.sql.SQLException;
import java.util.Scanner;
import GUI.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
* Our Main class for the benchmark database.
*/
@SuppressWarnings({"SpellCheckingInspection", "checkstyle:magicnumber"})
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

        ButtonFrame frame = new ButtonFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        
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
            long timeElapsedTransactions = finishTransactions
                    - startTransactions;
            System.out.println("Laufzeit in Millisekunden: "
                    + timeElapsedTransactions);
            System.out.println(transactions
                    / (timeElapsedTransactions / 1000) + " TX/s");

        } catch (Exception e) {
            if (e.toString().equals("read_exception")) {
                System.out.println(
                        "Read exception while trying to simulate Transactions: "
                                + e);
            } else if (e.toString().equals("write_exception")) {
                System.out.println(
                        "Write exception while trying to simulate TXs: "
                                + e);
            } else {
                System.out.println("ERROR: " + e);
            }
        }
        System.out.println(analyseTX(100));
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

    /**
     * Gets balance for corresponding account id.
     *
     * @param accid the account id
     * @return returns the balance of the account
     * @throws Exception throws an exception
     */
    public static int balanceTX(final int accid) throws Exception {
        return txManager.balanceTx(accid);
    }

    /**
     * Gets Amount of Transactions with specifically this amount deposited.
     *
     * @param delta the amount to check
     * @return returns the balance of the account
     * @throws Exception throws an exception
     */
    public static int analyseTX(final int delta) throws Exception {
        return txManager.analyseTx(delta);
    }
}
