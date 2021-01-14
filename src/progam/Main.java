package progam;

import gui.ButtonFrame;
import gui.ButtonPanel;

import javax.swing.JFrame;
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
        Parameters.frame = new ButtonFrame();
        Parameters.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Parameters.frame.setVisible(true);
        ButtonPanel panel = new ButtonPanel();
        Parameters.frame.add(panel);
        fillDatabase();
    }

    /**
     * Asks if we should reset the database or not.
     *
     * returns the expected value of 100.
     * @throws SQLException throws SQL-Exception.
     */
    public static void fillDatabase() throws SQLException {
        System.out.println("Welcher Skalierungsfaktor soll verwendet werden?"
                + "(0 um aktuellen Zustand beizubehalten):");
        Scanner scanner = new Scanner(System.in);
        new TableManager();
        int n = scanner.nextInt();
        if (n != 0) {
            TableManager.createTables();
            long start = System.currentTimeMillis();
            TableManager.fillDatabase(n);
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("Laufzeit in Millisekunden: " + timeElapsed);
        } else {
            TableManager.clearHistory();
        }
    }
}
