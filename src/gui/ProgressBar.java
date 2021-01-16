package gui;

import com.company.Parameters;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProgressBar extends JPanel {
    JProgressBar pbar;

    static final int MY_MINIMUM = 0;

    static final int MY_MAXIMUM = 100;

    public ProgressBar(int x,int y, int width,int height) {
        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        //setBounds(500 , 500 , 8000 , 4000);
        //setLayout(new GridLayout(1, 1));
        //setPreferredSize(new Dimension(2000, 4000));
        //pbar.setSize(new Dimension(Parameters.windowWidth,Parameters.windowHeight/4));
        setLayout(null);
        //pbar.setSize(Parameters.windowWidth,Parameters.windowHeight/4);
        pbar.setBounds(x,y,width,height);
        add(pbar);
    }

    public void updateBar(int newValue) {
        pbar.setValue(newValue);
    }
}
