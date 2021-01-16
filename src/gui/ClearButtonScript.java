package gui;

import program.Parameters;
import program.TableManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearButtonScript implements ActionListener {
    /**
     * Clears History Table when clear button gets pushed.
     * @param e the event.
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        new TableManager();
        TableManager.clearHistory();
        Parameters.frame.clearTextField();
    }
}
