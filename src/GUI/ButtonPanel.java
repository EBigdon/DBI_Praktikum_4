package GUI;

import javax.swing.*;

public class ButtonPanel extends JPanel {
    public ButtonPanel(){
        JButton start_button = new JButton("START");
        add(start_button);

        StartAction startAction = new StartAction();
        start_button.addActionListener(startAction);
    }
}
