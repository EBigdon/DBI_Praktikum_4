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
        myDatabase.drop_table();
        myDatabase.createTables();
        System.out.println("Wieviele Tupel sollen ausgegeben werde?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
    }

}
