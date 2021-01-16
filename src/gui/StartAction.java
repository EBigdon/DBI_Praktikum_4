package gui;
import com.company.LoadDriver;
import com.company.Parameters;

import javax.swing.*;
import java.awt.*;
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
    @SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:WhitespaceAfter"})
    public void actionPerformed(final ActionEvent event) {
        JLabel lable = new JLabel("Programm gestartet");
        Parameters.frame.setVisible(false);
        Parameters.frame = new ButtonFrame();
        Parameters.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Parameters.frame.setVisible(true);
        ProgressBar progressBar = new ProgressBar(10, Parameters.windowHeight - 100,
                Parameters.windowWidth - 20, 40);
        Parameters.frame.setContentPane(progressBar);
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
        ProgressRunnable progressRunnable = new ProgressRunnable(progressBar, "Progress bar Thread");
        progressRunnable.start();
    }
}
