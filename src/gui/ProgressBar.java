package gui;

import program.Parameters;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBar extends JPanel {
    /**
     * The progress bar.
     */
    private JProgressBar pbar;
    /**
     * Minimum value of progress bar.
     */
    static final int MY_MINIMUM = 0;
    /**
     * Maximum value of progress bar.
     */
    static final int MY_MAXIMUM = 100;

    /**
     * Progress bar constructor.
     */
    public ProgressBar(int x,int y, int width,int height) {
        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        //setBounds(500 , 500 , 8000 , 4000);
        // setLayout(new GridLayout(1, 1));
        // setPreferredSize(new Dimension(2000, 4000));
        // pbar.setSize(new Dimension(Parameters.windowWidth,Parameters.windowHeight/4));
        setLayout(null);
        // pbar.setSize(Parameters.windowWidth,Parameters.windowHeight/4);
        // pbar.setBounds(x,y,width,height);
        add(pbar);     }

    /**
     * Sets progress bar to given value.
     *
     * @param newValue value to set
     */
    public void updateBar(final int newValue) {
        pbar.setValue(newValue);
    }
}
