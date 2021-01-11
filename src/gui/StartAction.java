package gui;
import com.company.LoadDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 5; j++) {
                LoadDriver runnableLoadDriver = new LoadDriver(
                        "Thread." + (i + 1)
                              + "." + (j + 1), 100);
                runnableLoadDriver.start();
            }
        }
    }
}
