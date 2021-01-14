package gui;

import progam.Parameters;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBar extends JPanel {
    /**
     * The progress bar.
     */
    private JProgressBar progressBar;
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
    public ProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setMinimum(MY_MINIMUM);
        progressBar.setMaximum(MY_MAXIMUM);
        setLayout(null);
        progressBar.setBounds(10, 10,
                Parameters.windowWidth - 20,
                Parameters.windowHeight / 4);
        add(progressBar);
    }

    /**
     * Sets progress bar to given value.
     *
     * @param newValue value to set
     */
    public void updateBar(final int newValue) {
        progressBar.setValue(newValue);
    }
}
