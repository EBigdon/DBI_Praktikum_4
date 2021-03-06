package gui;

import program.LoadDriver;
import program.Parameters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StartButtonScript implements ActionListener {

    /**
     * Does transactions when start button is pressed.
     * @param e the event, waits until its pressed.
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        Parameters.frame.startProgram();
        System.out.println("Load Driver started...");
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("Start Time: " + time.format(formatter));
        time = time.plusSeconds(Parameters.timeToRunInSec);
        System.out.println("Estimated End Time: " + time.format(formatter));
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 6; j++) {
                LoadDriver runnableLoadDriver = new LoadDriver(
                        "Thread." + i + "." + j);
                runnableLoadDriver.start();
            }
        }
        ProgressRunnable progressRunnable = new ProgressRunnable(
                Parameters.frame.progressBar1, "Progress bar Thread");
        progressRunnable.start();


    }
}
