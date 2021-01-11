package gui;

import com.company.Parameters;

import javax.swing.JFrame;

public class ButtonFrame extends JFrame {
    /**
     * Creates Button to start the load driver.
     */
    public ButtonFrame() {
        setTitle("Load-Driver");
        setSize(Parameters.windowWidth, Parameters.windwoHeight);

        ButtonPanel panel = new ButtonPanel();
        add(panel);
    }
}
