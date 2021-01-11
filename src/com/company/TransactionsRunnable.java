package com.company;

public class TransactionsRunnable implements Runnable {
    /**
     * The Thread.
     */
    private Thread t;
    /**
     * Name of the Thread.
     */
    private final String threadName;
    /**
     * Type which Transaction is done.
     * Allowed Values:
     * 1: Makes an deposit transaction.
     * 2: Makes an balance transaction.
     * 3: Makes an analyse transaction.
     */
    private final int type;
    /**
     * the n of n-Tps-database.
     * Standard value would be 100.
     */
    private static int n;
    /**
     * An Instanciation of TXManager class.
     */
    private static final TXManager txManager = new TXManager();

    /**
     * public Constructor.
     *
     * @param name      name for the Thread
     * @param typeToSet type of transaction
     * @param nToSet    n-tps n.
     */
    public TransactionsRunnable(final String name,
                                final int typeToSet, final int nToSet) {
        threadName = name;
        type = typeToSet;
        n = nToSet;
    }

    /**
     * Runnable start.
     */
    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    /**
     * Runnable run.
     */
    public void run() {
        //System.out.println("Running " +  threadName );
        doTransaction(this.type);
    }

    /**
     * Doing a transaction from type.
     *
     * @param myType type of Transaction
     */
    public void doTransaction(final int myType) {
        if (myType == 1) {
            final int tellerid = randTellerid();
            depositTX(randAccid(), tellerid,
                    randBranchid(tellerid), randomDelta());
        } else if (myType == 2) {
            try {
                balanceTX(randAccid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (myType == 3) {
            try {
                analyseTX(randomDelta());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR FOR type " + this.type);
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

    /**
     * Gets a random account id.
     *
     * @return random account id
     */
    private static int randAccid() {
        return (int)
                (Math.random() * ((n + 1) * 100000 - 100001 + 1) + 1);
    }

    /**
     * Gets a random teller id.
     *
     * @return random teller id
     */
    private static int randTellerid() {
        return (int) (Math.random() * (n * 10) + 1);
    }

    /**
     * Geta a random branch id.
     *
     * @param randomTellerid a teller id to calculate branch id
     * @return random branch id
     */
    private static int randBranchid(final int randomTellerid) {
        return (randomTellerid % n) + 1;
    }

    /**
     * Geta a random deposit amount.
     *
     * @return random deposit amount
     */
    private static int randomDelta() {
        return (int) (Math.random() * 1000 + 1);
    }
}
