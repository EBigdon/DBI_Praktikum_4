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

        long start_1 = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++){
            int random_accid = (int)(Math.random() * ((n + 1) * 100000 - 100001 + 1) + 100001);
            int random_tellerid = (int)(Math.random() * (n * 10 - 1 + 1) + 1);
            int random_branchid = (random_tellerid/10) + 1;
            if(random_branchid == n + 1){
                random_branchid = n;
            }
            int random_delta = (int)(Math.random() * (1000 - 1 + 1) + 1);
            deposit_TX(random_accid, random_tellerid, random_branchid, random_delta);
        }
        long finish_1 = System.currentTimeMillis();
        long timeElapsed_1 = finish_1 - start_1;
        System.out.println("Laufzeit in Millisekunden: " + timeElapsed_1);
    }


    public static int deposit_TX(int accid, int tellerid, int branchid, int delta){
        try{
            return txManager.depositTx(accid, tellerid, branchid, delta);
        }catch (Exception e){
            System.out.println(e);
            System.out.println(accid + " " + tellerid + " " + branchid + " " + delta);
        }
        return 0;
    }
}