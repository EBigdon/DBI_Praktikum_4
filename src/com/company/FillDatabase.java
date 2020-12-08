package com.company;

import java.util.Scanner;

public class FillDatabase {
    /**
     * To fill our bench_database.
     * @return 0 or 1
     */
    public static int fill_database() {
        System.out.println("Ausgabe? (branch/account/tellers)");
        Scanner scan = new Scanner(System.in);
        String auswahleingabe = scan.nextLine();

        if (auswahleingabe.equals("account")) {

        } else if (auswahleingabe.equals("branch")) {
            int nextbid = 1;
            System.out.println("Geben Sie den branchnamen ein:");
            String branchname = scan.nextLine();
            System.out.println("Geben Sie die balance ein:");
            int balance = scan.nextInt();
            System.out.println("Geben Sie die Adresse ein:");
            String adress = scan.nextLine();
            String querybranch = "INSERT INTO branch " + "(" + nextbid + "," + branchname + "," + balance + "," + adress + ")";
            System.out.println(querybranch);
            nextbid = nextbid + 1;
        } else if (auswahleingabe.equals("tellers")) {

        } else {
            return 1;
        }
        return 0;
    }

    /**
     * makes Tupels.
     *
     * @param n n Tupel
     */
    public static void doTupel(final int n) {
        String fillString = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKL" +
                "RSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJK";

        int counter = 0;

        for (int i = 0; i < n; i++) {
            //Branch
            for (int j = 0; j < n; j++) {
                String name = Integer.toString(i) + Integer.toString(j)
                        + fillString.substring(0, 20 - int_string_length(i, j));
                int branchId = counter;
                int balance = 0;
                String address = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 70);
                String querybranch = "INSERT INTO `branches` (`branchid`, `branchname`, `balance`, `address`) VALUES "
                        + branchId + ", '" + name + "'," + balance + ", '" + address + "')";
                counter++;
            }
            counter = 0;
            //Accounts
            for (int j = 0; j < n; j++) {
                String name = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 18);
                int accId = counter;
                int balance = 0;
                int branchId = counter;
                String address = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 66);
                String querybranch = "INSERT INTO branch (" + accId + "," + name + "," + balance + "," + address + ")";
                counter++;
            }
            counter = 0;
            //tellers
            for (int j = 0; j < n; j++) {
                int tellerid = counter;
                String tname = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 18);
                int balance = 0;
                int branchid = counter;
                String address = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 66);
                counter++;
            }
            counter = 0;
            //history
            for (int j = 0; j < n; j++) {

            }
        }
    }

    public static int intStringLength (int i, int j) {
        return (Integer.toString(i).length() + Integer.toString(j).length());
    }

}


