package gui;

import program.Parameters;

import javax.swing.*;
import java.awt.event.ActionListener;

public class StartPanel extends JFrame{
    public JButton startButton;
    public JButton clearButton;
    public JButton setupButton;
    public JProgressBar progressBar1;
    private JFormattedTextField formattedTextField1;
    private JTextArea History;
    private JPanel rootPanel;

    public StartPanel(){
        add(rootPanel);

        formattedTextField1.setValue("10");
        StartButtonScript startAction = new StartButtonScript();
        startButton.addActionListener(startAction);
        ClearButtonScript clearAction = new ClearButtonScript();
        clearButton.addActionListener(clearAction);
        SetupButtonScript setupAction = new SetupButtonScript();
        setupButton.addActionListener(setupAction);

        setTitle("Server-Benchmark");
        setSize(800,600);
    }
    public void startProgram(){
        startButton.setEnabled(false);
        clearButton.setEnabled(false);
        setupButton.setEnabled(false);
        formattedTextField1.setEnabled(false);
        Parameters.timeToRunInSec = getTimeValue();
    };
    public void finishProgram(){
        startButton.setEnabled(true);
        setupButton.setEnabled(true);
        clearButton.setEnabled(true);
        formattedTextField1.setEnabled(true);
        progressBar1.setValue(0);
    };
    public int getTimeValue(){
        try {
            return Integer.parseInt(formattedTextField1.getValue().toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public void writeTextField(String Text){
        History.setText(Text);
    }
}
