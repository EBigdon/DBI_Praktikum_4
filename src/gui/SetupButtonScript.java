package gui;

import program.Parameters;
import program.TableManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Scanner;

public class SetupButtonScript implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Parameters.frame.startProgram();
        Parameters.frame.writeTextField("Füllt Datenbank mit 100-TPs.");
        System.out.println("Füllt Datenbank mit 100-TPs.");
        new TableManager();
        TableManager.createTables();
        long start = System.currentTimeMillis();
        try {
            TableManager.fillDatabase(100);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Laufzeit in Millisekunden: " + timeElapsed);
        Parameters.frame.finishProgram();
    }
}
