package com.company;

public class TransactionsRunnable implements Runnable{
    private Thread t;
    private String threadName;
    private int type;
    private static int n;
    private static final TXManager txManager = new TXManager();

    public TransactionsRunnable(final String name, final int type, final int n) {
        threadName = name;
        this.type = type;
        this.n = n;
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start ();
        }
    }

    public void run() {
        //System.out.println("Running " +  threadName );
        doTransaction(this.type);
    }

    public void doTransaction(int myType){
        if(myType==1){
            final int tellerid = randTellerid();
            depositTX(randAccid(), tellerid,
                    randBranchid(tellerid), randomDelta());
        } else if (myType==2) {
            try {
                balanceTX(randAccid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (myType==3) {
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
}
