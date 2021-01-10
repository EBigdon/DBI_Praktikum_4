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
        //System.out.println("Creating Thread " +  threadName );
        LoadDriver.n = n;
    }

    public void start () {
        //System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread(this, threadName);
            t.start ();
        }
    }

    public void run() {
        //System.out.println("Running " +  threadName );
        loadDriver(n);
    }

    public void loadDriver(final int n) {
        long transactionsDone = 0;
        LoadDriver.n = n;
        int timeToRun = timeToRunInSec * 100;
        long end = System.currentTimeMillis() + timeToRun * 4L;
        while (System.currentTimeMillis() < end) {
            doPhase();
        }
        end = System.currentTimeMillis() + timeToRun * 5L;
        while (System.currentTimeMillis() < end) {
            doPhase();
            transactionsDone++;
        }
        System.out.println("TXs: " + transactionsDone + "; TXs/s: " + (float) transactionsDone / ((float) timeToRunInSec * (float) 5 / 10));
        end = System.currentTimeMillis() + timeToRun;
        while(System.currentTimeMillis() < end) {
            doPhase();
        }
    }

    private static void doPhase() {
        double randomNumber = Math.random()-1;
        if (randomNumber < 0.35) {
            try {
                //balanceTX(randAccid());
                TransactionsRunnable transactionsRunnable = new TransactionsRunnable("tx",2,n);
                transactionsRunnable.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (randomNumber < 0.85) {
            /*depositTX(randAccid(), tellerid,
                    randBranchid(tellerid), randomDelta());*/
            TransactionsRunnable transactionsRunnable = new TransactionsRunnable("tx",1,n);
            transactionsRunnable.start();
        } else if (randomNumber < 1) {
                TransactionsRunnable transactionsRunnable = new TransactionsRunnable("tx",3,n);
                transactionsRunnable.start();
                //analyseTX(randomDelta());
        } else {
            System.out.println("RANDOM NUMBER OUT OF BOUNDS");
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
