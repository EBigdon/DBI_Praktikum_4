package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartPanel extends JFrame{
    private JButton startButton;
    private JButton clearButton;
    private JButton setupButton;
    public JProgressBar progressBar1;
    private JFormattedTextField formattedTextField1;
    private JTextArea History;
    private JTextArea TMs7;
    private JPanel rootPanel;

    public StartPanel(){
        add(rootPanel);

        StartButtonScript startAction = new StartButtonScript();
        startButton.addActionListener(startAction);
        ClearButtonScript clearAction = new ClearButtonScript();
        clearButton.addActionListener(clearAction);
        SetupButtonScript setupAction = new SetupButtonScript();
        setupButton.addActionListener(setupAction);

        setTitle("Server-Benchmark");
        setSize(800,600);
    }
}
