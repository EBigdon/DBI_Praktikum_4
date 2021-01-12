package gui;
import com.company.LoadDriver;
import com.company.Parameters;
import com.company.TXManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * An actionslistener for our button.
 */
public final class StartAction implements ActionListener {
    /**
     * action that gets performed when button is pressed.
     *
     * @param event wait for button press
     */
    public void actionPerformed(final ActionEvent event) {
        TXManager txManager = new TXManager();
        System.out.println("Load Driver started...");
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("Start Time: " + time.format(formatter));
        time = time.plusSeconds(Parameters.timeToRunInSec);
        System.out.println("Estimated End Time: " + time.format(formatter));
        for (int i = 1; i < 2; i++) {
            for (int j = 1; j < 6; j++) {
                LoadDriver runnableLoadDriver = new LoadDriver(
                        "Thread." + i + "." + j);
                runnableLoadDriver.start();
            }
        }
        int timeToRun = Parameters.timeToRunInSec * 100;
        long end = System.currentTimeMillis() + timeToRun * 10L;
        while (System.currentTimeMillis() < end);
        long totalTransactionsDone = Parameters.resultOne
                + Parameters.resultTwo + Parameters.resultThree
                + Parameters.resultFour + Parameters.resultFive;
        System.out.println("Transactions done during measurement phase: " + (totalTransactionsDone));
        System.out.println("Transactions per Seconds: " + ((float) totalTransactionsDone / ((float) Parameters.timeToRunInSec) / (float) 2));
    }
}
