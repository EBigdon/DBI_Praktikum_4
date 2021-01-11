package gui;

import javax.swing.JPanel;
import javax.swing.JButton;

/**
 * Button panel class.
 */
public class ButtonPanel extends JPanel {
    /**
     * Panel for our Buttons.
     */
    public ButtonPanel() {
        JButton startButton = new JButton("START");
        add(startButton);

        StartAction startAction = new StartAction();
        startButton.addActionListener(startAction);
    }
}
