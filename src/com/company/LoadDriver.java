package com.company;

import java.util.TimerTask;

public class LoadDriver {

    private static final TXManager txManager = new TXManager();
    private static int n;
    public static long transactionsDone = 0;
    public static int timeToRunInSec = 60;

    LoadDriver(final int n) {
        LoadDriver.n = n;
        long startTime = System.currentTimeMillis();
        int timeToRun = timeToRunInSec*100;
        long end = System.currentTimeMillis() + timeToRun * 4;

        while(System.currentTimeMillis() < end){
            swingPhase();
        }
        printTransactions(4);
        end = System.currentTimeMillis() + timeToRun * 5;
        while(System.currentTimeMillis() < end){
            messPhase();
        }
        printTransactions(5);
        end = System.currentTimeMillis() + timeToRun;
        while(System.currentTimeMillis() < end){
            swingPhase();
        }
        printTransactions(1);
    }

    public static void printTransactions(int modifier) {
        System.out.println("End Transaktionen: "
                + transactionsDone);
        System.out.println("Transaktionen pro Sekunden: " + (float)transactionsDone/((float)timeToRunInSec*(float)modifier/10));
        System.out.println();
        transactionsDone = 0;
    }

    private static void swingPhase() {
        double randomNumber = Math.random();
        if (randomNumber < 0.35) {
            try {
                balanceTX(randAccid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (randomNumber < 0.85) {
            final int tellerid = randTellerid();
            depositTX(randAccid(), tellerid,
                    randBranchid(tellerid), randomDelta());
        } else if (randomNumber < 1) {
            try {
                analyseTX(randomDelta());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("RANDOM NUMBER OUT OF BOUNDS");
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        transactionsDone++;

    }

    private static void messPhase() {
        swingPhase();
        transactionsDone++;
    }

    private static int randAccid() {
        return (int)
                (Math.random() * ((n + 1) * 100000 - 100001 + 1) + 1);
    }

    private static int randTellerid() {
        return (int) (Math.random() * (n * 10) + 1);
    }

    private static int randBranchid(final int randomTellerid) {
       return (randomTellerid % n) + 1;
    }

    private static int randomDelta() {
        return (int) (Math.random() * 1000 + 1);
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
