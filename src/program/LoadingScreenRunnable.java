package program;

import java.sql.SQLException;

public class LoadingScreenRunnable implements Runnable{
    /**
     * The Thread.
     */
    private Thread t;
    /**
     * Name of the Thread.
     */
    private final String threadName;

    /**
     * public Constructor.
     *
     * @param name      name for the Thread
     */
    public LoadingScreenRunnable(final String name) {
        threadName = name;
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
        Parameters.frame.startProgram();
        Parameters.frame.writeTextField("Füllt Datenbank mit 100-TPs.");
        System.out.println("Füllt Datenbank mit 100-TPs.");
        new TableManager();
        TableManager.createTables();
        long start = System.currentTimeMillis();
        try {
            TableManager.fillDatabase(100);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Laufzeit in Millisekunden: " + timeElapsed);
        Parameters.frame.writeTextField("Datenbank vollständig gefüllt in " + timeElapsed + " Sekunden.");
        System.out.println("Datenbank vollständig gefüllt.");
        Parameters.frame.finishProgram();
        Parameters.frame.fixTextField();
        Parameters.myLoadingScreen.close();
    }
}
