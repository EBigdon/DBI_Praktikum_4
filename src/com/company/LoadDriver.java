package com.company;

import java.util.TimerTask;

public class LoadDriver implements Runnable{
    private Thread t;
    private String threadName;
    private static final TXManager txManager = new TXManager();
    private static int n;
    public static int timeToRunInSec = 10*60/60;

    public LoadDriver(final String name, final int n) {
        threadName = name;
        System.out.println("Creating Thread " +  threadName );
        LoadDriver.n = n;
    }

    public void start () {
        //System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

    public void run() {
        //System.out.println("Running " +  threadName );
        loadDriver(100);
    }

    public void loadDriver(final int n) {
        long transactionsDone = 0;
        LoadDriver.n = n;
        int timeToRun = timeToRunInSec * 100;
        long end = System.currentTimeMillis() + timeToRun * 4L;
        while (System.currentTimeMillis() < end) {
            swingPhase();
        }
        end = System.currentTimeMillis() + timeToRun * 5L;
        while (System.currentTimeMillis() < end) {
            messPhase();
            transactionsDone++;
        }
        System.out.println("Mess Transaktionen: " + transactionsDone +"\nTransaktionen pro Sekunden: " + (float) transactionsDone / ((float) timeToRunInSec * (float) 5 / 10) + "\n");
        end = System.currentTimeMillis() + timeToRun;
        while(System.currentTimeMillis() < end) {
            swingPhase();
        }
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
    }

    private static void messPhase() {
        swingPhase();
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
