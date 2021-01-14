package progam;

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
     * An Instanciation of TXManager class.
     */
    private static final TXManager txManager = new TXManager();

    /**
     * public Constructor.
     *
     * @param name      name for the Thread
     * @param typeToSet type of transaction
     */
    public TransactionsRunnable(final String name,
                                final int typeToSet) {
        threadName = name;
        type = typeToSet;
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
        doTransaction();
        t.stop();
        Thread.currentThread().interrupt();
        t = null;
    }

    /**
     * Doing a transaction from type.
     */
    public void doTransaction() {
        if (type == 1) {
            try {
                txManager.sqlTransaction();
                final int tellerid = randTellerid();
                depositTX(randAccid(), tellerid,
                        randBranchid(tellerid), randomDelta());
                txManager.sqlCommit();
            } catch (Exception e) {
                txManager.sqlRollback();
                e.printStackTrace();
            }
        } else if (type == 2) {
            try {
                balanceTX(randAccid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type == 3) {
            try {
                analyseTX(randomDelta());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR FOR type " + type);
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
     */
    public static int balanceTX(final int accid) {
        return txManager.balanceTx(accid);
    }

    /**
     * Gets Amount of Transactions with specifically this amount deposited.
     *
     * @param delta the amount to check
     * @return returns the balance of the account
     */
    public static int analyseTX(final int delta) {
        return txManager.analyseTx(delta);
    }

    /**
     * Gets a random account id.
     *
     * @return random account id
     */
    private static int randAccid() {
        return 1 + (int)
                (Math.random() * ((Parameters.n + 1) * 100000 - 100001 + 1));
    }

    /**
     * Gets a random teller id.
     *
     * @return random teller id
     */
    private static int randTellerid() {
        return (int) (Math.random() * (Parameters.n * 10) + 1);
    }

    /**
     * Geta a random branch id.
     *
     * @param randomTellerid a teller id to calculate branch id
     * @return random branch id
     */
    private static int randBranchid(final int randomTellerid) {
        return (randomTellerid % Parameters.n) + 1;
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
