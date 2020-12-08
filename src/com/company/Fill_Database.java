package com.company;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Fill_Database {

    public static TableManager myDatabase = new TableManager();

    public static void Fill_Database() {


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
            for (int j = 0; j < 1; j++) {
                String name = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 20 - int_string_length(i, j));
                int branchId = counter;
                int balance = 0;
                String address = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 72 - int_string_length(i, j));
                String querybranch = "INSERT INTO `branches` (`branchid`, `branchname`, `balance`, `address`) VALUES (" + branchId + ", '" + name + "'," + balance + ", '" + address + "')";
                myDatabase.executeQuerry(querybranch);
                counter++;
            }
            /*
            counter = 0;
            //Accounts
            for (int j = 0;j<n;j++) {
                String name = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 20 - int_string_length(i, j));
                int accId = counter;
                int balance = 0;
                int branchId = counter;
                String address = Integer.toString(i) + Integer.toString(j) + fillString.substring(0,68 - int_string_length(i, j));
                String querybranch = "INSERT INTO branch ("+ accId + "," + name + "," + balance + "," + address + ")" ;

                counter++;
            }
            counter = 0;
            //tellers
            for (int j = 0;j<n;j++) {
                int tellerid = counter;
                String tname = Integer.toString(i) + Integer.toString(j) + fillString.substring(0, 20 - int_string_length(i, j));
                int balance = 0;
                int branchid = counter;
                String address = Integer.toString(i) + Integer.toString(j) + fillString.substring(0,68 - int_string_length(i, j));
                counter++;
            }
            counter = 0;
            //history
            for (int j = 0;j<n;j++) {

            }

             */
        }
    }

    public static int int_string_length(int i, int j) {
        return (Integer.toString(i).length() + Integer.toString(j).length());
    }
}


