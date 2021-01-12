package gui;

import com.company.Parameters;
import com.company.TXManager;

import javax.swing.*;

public class ProgressRunnable implements Runnable {
    /**
     * The Thread.
     */
    private Thread t;
    /**
     * Name of the Thread.
     */
    private final String threadName;

    /**
     * Runnable start.
     */
    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public ProgressBar it;

    /**
     * Constructor of runnable class.
     * @param myProgressBar Progressbar to modify
     * @param name name of the thread
     */
    public ProgressRunnable(ProgressBar myProgressBar, final String name) {
        it = myProgressBar;
        threadName = name;
    }
    /**
     * Runnable run.
     */
    public void run() {
        waitTillLoadDriverEnd();
        Parameters.frame.setVisible(false);
        Parameters.frame = new ButtonFrame();
        Parameters.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Parameters.frame.setVisible(true);
        ButtonPanel panel = new ButtonPanel();
        Parameters.frame.add(panel);
        t.stop();
        Thread.currentThread().interrupt();
        t = null;
    }

    /**
     * Shows Progress on Progressbar.
     */
    private void waitTillLoadDriverEnd() {
        int timeToRun = Parameters.timeToRunInSec * 100;
        long end = System.currentTimeMillis() + timeToRun * 10L;
        long timeTillEnd = end - System.currentTimeMillis();
        while (System.currentTimeMillis() < end + 150) {
            long timeYo = end - System.currentTimeMillis();
            float percentageFinished = (1 - ((float) timeYo
                    / (float) timeTillEnd)) * 100;
            SwingUtilities.invokeLater(() -> it.updateBar((int) percentageFinished));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long totalTransactionsDone = Parameters.resultOne
                + Parameters.resultTwo + Parameters.resultThree
                + Parameters.resultFour + Parameters.resultFive;
        System.out.println("Transactions done during measurement phase: "
                + (totalTransactionsDone));
        System.out.println("Transactions per Seconds: "
                + ((float) totalTransactionsDone
                / ((float) Parameters.timeToRunInSec
                * (float) 5 / 10)));
    }
}
