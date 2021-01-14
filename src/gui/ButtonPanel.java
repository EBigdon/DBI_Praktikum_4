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
        //setBounds(500 , 500 , 8000 , 4000);
        //setLayout(new GridLayout(1, 1));
        //setPreferredSize(new Dimension(2000, 4000));
        //startButton.setSize(Parameters.windowWidth,Parameters.windowHeight/4);
        //setLayout(new GridBagLayout());
        setLayout(null);
        startButton.setBounds(Parameters.windowWidth/4,50,Parameters.windowWidth/2,Parameters.windowHeight/4);
        startButton.setFont(startButton.getFont().deriveFont(64.0f));
        //startButton.setSize(Parameters.windowWidth/2,Parameters.windowHeight/4);
        add(startButton);

        StartAction startAction = new StartAction();
        startButton.addActionListener(startAction);
    }
}
