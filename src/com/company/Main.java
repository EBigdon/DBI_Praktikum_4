package com.company;

import java.sql.SQLException;
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
    static TXManager txManager = new TXManager();

    public static void main(final String[] args) throws Exception {


        /*TableManager tableManager = new TableManager();
        tableManager.createTables();
        System.out.println("Welcher Skalierungsfaktor soll verwendet werden?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long start = System.currentTimeMillis();
        tableManager.fillDatabase(n);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Laufzeit in Millisekunden: " + timeElapsed);*/


        deposit_TX(1, 0, 0, 1000);

    }


    public static int deposit_TX(int accid, int tellerid, int branchid, int delta){
        try{
            return txManager.depositTx(accid, tellerid, branchid, delta);
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
}