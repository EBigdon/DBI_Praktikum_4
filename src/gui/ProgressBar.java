package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProgressBar extends JPanel {
    JProgressBar pbar;

    static final int MY_MINIMUM = 0;

    static final int MY_MAXIMUM = 100;

    public ProgressBar() {
        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        setBounds(50 , 50 , 800 , 400);
        setLayout(new GridLayout(10, 20));
        setPreferredSize(new Dimension(2000, 4000));
        add(pbar);
    }

    public void updateBar(int newValue) {
        pbar.setValue(newValue);
    }
}
