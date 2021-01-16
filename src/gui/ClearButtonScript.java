package gui;

import program.Parameters;
import program.TableManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearButtonScript implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new TableManager();
        TableManager.clearHistory();
        Parameters.frame.clearTextField();
    }
}
