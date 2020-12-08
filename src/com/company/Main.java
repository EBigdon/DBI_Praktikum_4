package com.company;

import java.util.Scanner;
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
    public static void main(final String[] args) {
        TableManager myDatabase = new TableManager();
        myDatabase.dropTable();
        myDatabase.createTables();
        System.out.println("Welcher Skalierungsfaktor soll verwendet werden?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Fill_Database myFill = new Fill_Database();
        myFill.doTupel(n);
    }

}
