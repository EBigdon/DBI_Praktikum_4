package com.company;

public class LoadDriver implements Runnable {
    /**
     * The Thread.
     */
    private Thread t;
    /**
     * The name of the Thread.
     */
    private final String threadName;
    /**
     * n from n-tps database.
     */
    private static int n;

    /**
     * Constructor of LoadDriver instance.
     *
     * @param name   name of the Thread
     * @param nToSet n from n-tps
     */
    public LoadDriver(final String name, final int nToSet) {
        threadName = name;
        LoadDriver.n = nToSet;
    }

    /**
     * The Runnable start method.
     */
    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    /**
     * The Runnable run method.
     */
    public void run() {
        loadDriver(n);
    }

    /**
     * the Load driver constructor.
     *
     * @param nToSet n from n-tps.
     */
    public void loadDriver(final int nToSet) {
        long transactionsDone = 0;
        LoadDriver.n = nToSet;
        int timeToRun = Parameters.timeToRunInSec * 100;
        long end = System.currentTimeMillis() + timeToRun * 4L;
        while (System.currentTimeMillis() < end) {
            doPhase();
        }
        end = System.currentTimeMillis() + timeToRun * 5L;
        while (System.currentTimeMillis() < end) {
            doPhase();
            transactionsDone++;
        }
        System.out.println("TXs: " + transactionsDone + "; TXs/s: "
                + (float) transactionsDone
                / ((float) Parameters.timeToRunInSec * (float) 5 / 10));
        end = System.currentTimeMillis() + timeToRun;
        while (System.currentTimeMillis() < end) {
            doPhase();
        }
    }

    private static void doPhase() {
        double randomNumber = Math.random();
        if (randomNumber < 0.35) {
            try {
                TransactionsRunnable transactionsRunnable
                        = new TransactionsRunnable("tx", 2, n);
                transactionsRunnable.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (randomNumber < 0.85) {
            TransactionsRunnable transactionsRunnable
                    = new TransactionsRunnable("tx", 1, n);
            transactionsRunnable.start();
        } else if (randomNumber < 1) {
            TransactionsRunnable transactionsRunnable
                    = new TransactionsRunnable("tx", 3, n);
            transactionsRunnable.start();
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
