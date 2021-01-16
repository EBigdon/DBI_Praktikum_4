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
        System.out.println("Welcher Skalierungsfaktor soll verwendet werden?"
                + "(0 um aktuellen Zustand beizubehalten):");
        Scanner scanner = new Scanner(System.in);
        new TableManager();
        int n = scanner.nextInt();
        if (n != 0) {
            TableManager.createTables();
            long start = System.currentTimeMillis();
            try {
                TableManager.fillDatabase(n);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("Laufzeit in Millisekunden: " + timeElapsed);
        } else {
            TableManager.clearHistory();
        }
        Parameters.frame.finishProgram();
    }
}
