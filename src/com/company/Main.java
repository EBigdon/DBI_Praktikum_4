package com.company;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static com.company.TableManager.*;

/**
 * Our Main class for the benchmark database.
 */
public class Main {
    /**
     * Our main function that gets called when program is executed.
     *
     * @param args String of supplied command-line-arguments
     */
    public static void main(final String[] args) throws Exception {

        TableManager myDatabase = new TableManager();
        Runnable createTableRunnable = () -> {
            myDatabase.createTables();
        };
        Thread createTheTables = new Thread(createTableRunnable);
        createTheTables.start();
        System.out.println("Welcher Skalierungsfaktor soll verwendet werden?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        long start = System.currentTimeMillis();
        /*
        myDatabase.fillDatabase();
        */

        for(int i = 0; i < n; i++) {
            TableManager testConn = new TableManager();
            int finalI = i;
            Runnable runnable = () -> {
                testConn.fastFillAccounts(finalI);
                try {
                    Thread.sleep(100);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                try {
                    testConn.closeConnection();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            };
            Thread myThread = new Thread(runnable);
            try {
                myThread.start();
            } catch(Exception e) {
                System.out.print("");
            }
        }
        //myDatabase.waitTillDatabaseFinished(n);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Laufzeit in ms: " + timeElapsed);
        TimeUnit.SECONDS.sleep(10);
    }

}
