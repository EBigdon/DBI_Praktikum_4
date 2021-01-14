package gui;

import progam.Parameters;

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
        setLayout(null);
        startButton.setBounds(Parameters.windowWidth / 4, 50,
                Parameters.windowWidth / 2, Parameters.windowHeight / 4);
        startButton.setFont(startButton.getFont().deriveFont(64.0f));
        add(startButton);
        StartAction startAction = new StartAction();
        startButton.addActionListener(startAction);
    }
}
