package progam;

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
     * Constructor of LoadDriver instance.
     *
     * @param name   name of the Thread
     */
    public LoadDriver(final String name) {
        threadName = name;
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
        doLoadDriver();
        t.stop();
        Thread.currentThread().interrupt();
        t = null;
    }

    /**
     * the Load driver.
     */
    public void doLoadDriver() {
        long transactionsDone = 0;
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
        end = System.currentTimeMillis() + timeToRun;
        switch (threadName.substring(threadName.length() - 1)) {
            case "1" -> Parameters.resultOne = transactionsDone;
            case "2" -> Parameters.resultTwo = transactionsDone;
            case "3" -> Parameters.resultThree = transactionsDone;
            case "4" -> Parameters.resultFour = transactionsDone;
            case "5" -> Parameters.resultFive = transactionsDone;
            default -> System.out.println("Thread error,"
                    + " Thread got named wrong: " + threadName);
        }
        while (System.currentTimeMillis() < end) {
            doPhase();
        }
        System.out.println(" TXs: " + transactionsDone + "; TXs/s: "
                + ((float) transactionsDone
                / (((float) Parameters.timeToRunInSec * (float) 5 / 10))));
    }

    private static void doPhase() {
        double randomNumber = Math.random();
        if (randomNumber < 0.50) {
            TransactionsRunnable transactionsRunnable
                    = new TransactionsRunnable("tx1", 1);
            transactionsRunnable.start();
        } else if (randomNumber < 0.85) {
            try {
                TransactionsRunnable transactionsRunnable
                        = new TransactionsRunnable("tx2", 2);
                transactionsRunnable.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (randomNumber < 1) {
            TransactionsRunnable transactionsRunnable
                    = new TransactionsRunnable("tx3", 3);
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
