package com.company;

import gui.ButtonFrame;
import gui.StartPanel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Scanner;

/**
* Our Main class for the benchmark database.
*/
@SuppressWarnings({"SpellCheckingInspection", "checkstyle:magicnumber"})
public class Main {

    /**
     * Our main function that gets called when program is executed.
     *
     * @param args String of supplied command-line-arguments
     */
    public static void main(final String[] args) throws Exception {
        /*Parameters.frame = new ButtonFrame();
        Parameters.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Parameters.frame.setVisible(true);*/
        Parameters.frame = new StartPanel();
        Parameters.frame.setVisible(true);
        Parameters.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Parameters.frame.add(panel);
    }
}
