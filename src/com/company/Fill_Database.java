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
        int acccounter = 0;
        int tellercounter = 0;

            for (int i = 0; i < 1 * n; i++) {
                String name = Integer.toString(i) + fillString.substring(0, 20 - int_string_length(i));
                int branchId = counter;
                int balance = 0;
                String address = Integer.toString(i) + fillString.substring(0, 72 - int_string_length(i));
                String querybranch = "INSERT INTO `branches` (`branchid`, `branchname`, `balance`, `address`) VALUES (" + branchId + ", '" + name + "'," + balance + ", '" + address + "')";
                myDatabase.executeQuerry(querybranch);
                counter++;

                //Accounts
                for (int j = 0;j < 100000;j++) {
                    String accname = Integer.toString(j) + fillString.substring(0, 20 - int_string_length(j));
                    int accId = acccounter;
                    int accbalance = 0;
                    String accaddress = Integer.toString(j) + fillString.substring(0,68 - int_string_length(j));
                    String accquerybranch = "INSERT INTO `accounts` (`accid`, `name`, `balance`, `branchid`, `address`) VALUES (" + accId + ", '" + accname + "'," + accbalance + ", " + branchId + ", '"+ accaddress + "')";
                    myDatabase.executeQuerry(accquerybranch);
                    acccounter++;
                }
                //tellers
                for (int j = 0;j< 10;j++) {
                    int tellerid = tellercounter;
                    String tellername = Integer.toString(j) + fillString.substring(0, 20 - int_string_length(j));
                    int tellerbalance = 0;
                    String telleraddress = Integer.toString(j) + fillString.substring(0,68 - int_string_length(j));
                    String tellerquerybranch = "INSERT INTO `tellers` (`tellerid`, `tellername`, `balance`, `branchid`,`address`) VALUES (" + tellerid + ", '" + tellername + "'," + tellerbalance + ","+ branchId + ", '" + telleraddress + "')";
                    myDatabase.executeQuerry(tellerquerybranch);
                    tellercounter++;
                }
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


