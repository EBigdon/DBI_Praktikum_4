package com.company;

import java.util.Scanner;

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
        /*
        TableManager tableManager = new TableManager();
        tableManager.createTables();
        System.out.println("Welcher Skalierungsfaktor soll verwendet werden?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long start = System.currentTimeMillis();
        tableManager.fillDatabase(n);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Laufzeit in Millisekunden: " + timeElapsed);
        */
        TXManager txManager = new TXManager();
        float balance = txManager.balanceTx(2);
    }
}