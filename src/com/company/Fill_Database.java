package com.company;

import java.util.Scanner;

public class Fill_Database {
    public static int fillDatabase() {
        System.out.println("Was soll eingelesen werden?");
        Scanner scan = new Scanner(System.in);
        String auswahleingabe = scan.nextLine();

        if (auswahleingabe.equals("account")) {

        }
        else if (auswahleingabe.equals("branch")) {
            int nextid = 1;
            System.out.println("Geben Sie den branchnamen ein:");
            String branchname = scan.nextLine();
            System.out.println("Geben Sie die balance ein:");
            int balance = scan.nextInt();
            System.out.println("Geben Sie die Adresse ein:");
            String adress = scan.nextLine();
            String querybranch = "INSERT INTO branch ("+ nextid + "," + branchname + "," + balance + "," + adress + ")" ;

        }
        else if(auswahleingabe.equals("tellers"))
        {
        }
        else
            {
                return 1;
            }
        return 0;
    }

    /**
     * makes Tupels.
     * @param n n Tupel
     */
    public static void doTupel(final int n) {
        String fillString = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKL" +
                "RSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJK";

        int counter = 0;

        for (int i = 0;i<n;i++) {
            //Branch
            for (int j = 0;j<n;j++) {
                String name = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 18);
                int branchId = counter;
                int balance = 0;
                String address = Integer.toString(i) + Integer.toString(j) + fillString.substring(0,70);
                counter++;
            }
            //Accounts
            for (int j = 0;j<n;j++) {

            }
            for (int j = 0;j<n;j++) {

            }
            for (int j = 0;j<n;j++) {

            }
        }
    }
}
