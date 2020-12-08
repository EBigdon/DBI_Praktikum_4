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


            for (int j = 0; j < 1 * n; j++) {
                String name = Integer.toString(j) + fillString.substring(0, 20 - int_string_length(j));
                int branchId = counter;
                int balance = 0;
                String address = Integer.toString(j) + fillString.substring(0, 72 - int_string_length(j));
                String querybranch = "INSERT INTO `branches` (`branchid`, `branchname`, `balance`, `address`) VALUES (" + branchId + ", '" + name + "'," + balance + ", '" + address + "')";
                myDatabase.executeQuerry(querybranch);
                counter++;
            }

            counter = 0;
            //Accounts
            for (int j = 0;j<100000 * n;j++) {
                String name = Integer.toString(j) + fillString.substring(0, 20 - int_string_length(j));
                int accId = counter;
                int balance = 0;
                int branchId = counter;
                String address = Integer.toString(j) + fillString.substring(0,68 - int_string_length(j));
                String querybranch = "INSERT INTO `accounts` (`accid`, `name`, `balance`, `branchid`, `address`) VALUES (" + accId + ", '" + name + "'," + balance + ", '" + branchId + ", '"+ address + "')";
                myDatabase.executeQuerry(querybranch);
                counter++;
            }
            counter = 0;
            //tellers
            for (int j = 0;j< 10 * n;j++) {
                int tellerid = counter;
                String tname = Integer.toString(j) + fillString.substring(0, 20 - int_string_length(j));
                int balance = 0;
                int branchid = counter;
                String address = Integer.toString(j) + fillString.substring(0,68 - int_string_length(j));
                String querybranch = "INSERT INTO `tellers` (`tellerid`, `tellername`, `balance`, `branchid`,`address`) VALUES (" + tellerid + ", '" + tname + "'," + balance + "',"+ branchid + ", '" + address + "')";
                myDatabase.executeQuerry(querybranch);
                counter++;
            }
            /*counter = 0;
            //history
            for (int j = 0;j<n;j++) {

            }*/



    }

    public static int int_string_length(int i) {
        return Integer.toString(i).length();
    }
}


