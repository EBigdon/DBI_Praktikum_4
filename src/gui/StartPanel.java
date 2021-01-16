package gui;

import program.Parameters;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class StartPanel extends JFrame {
    /**
     * Button to start transaction.
     */
    private JButton startButton;
    /**
     * Button to clear history table.
     */
    private JButton clearButton;
    /**
     * Button to create 100-tps-database.
     */
    private JButton setupButton;
    /**
     * Progress bar to show progress.
     */
    JProgressBar progressBar1;
    /**
     * Text field to set time to run.
     */
    private JFormattedTextField formattedTextField1;
    /**
     * Text area.
     */
    private JTextArea History;
    /**
     * The Panel.
     */
    private JPanel rootPanel;

    /**
     * The constructor of the class.
     */
    public StartPanel() {
        add(rootPanel);
        formattedTextField1.setValue("10");
        StartButtonScript startAction = new StartButtonScript();
        startButton.addActionListener(startAction);
        ClearButtonScript clearAction = new ClearButtonScript();
        clearButton.addActionListener(clearAction);
        SetupButtonScript setupAction = new SetupButtonScript();
        setupButton.addActionListener(setupAction);
        setTitle("Server-Benchmark");
        setSize(Parameters.windowWidth, Parameters.windowHeight);
    }

    /**
     * Deactivates buttons and text field.
     */
    public void startProgram() {
        startButton.setEnabled(false);
        clearButton.setEnabled(false);
        setupButton.setEnabled(false);
        formattedTextField1.setEnabled(false);
        Parameters.timeToRunInSec = getTimeValue();
    }

    /**
     * Reactivates buttons and text field.
     */
    public void finishProgram() {
        startButton.setEnabled(true);
        setupButton.setEnabled(true);
        clearButton.setEnabled(true);
        formattedTextField1.setEnabled(true);
        progressBar1.setValue(0);
    }

    /**
     * gets value of text field (Time to run in seconds).
     *
     * @return returns value of text field
     */
    public int getTimeValue() {
        try {
            return Integer.parseInt(formattedTextField1.getValue().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Writes into Textarea.
     *
     * @param Text text to write
     */
    public void writeTextField(final String Text) {
        String current = History.getText();
        History.setText(current + Text + "\n");
    }

    /**
     * Clears the Textarea.
     */
    public void clearTextField() {
        History.setText("");
    }
}
