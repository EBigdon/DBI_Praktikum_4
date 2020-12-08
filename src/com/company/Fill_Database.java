package com.company;

import java.util.Scanner;

public class Fill_Database
{
    public static int fill_database()
    {
        System.out.println("Was soll eingelesen werden?");
        Scanner scan = new Scanner(System.in);
        String auswahleingabe = scan.nextLine();

        if (auswahleingabe.equals("account"))
        {
        }
        else if (auswahleingabe.equals("branch"))
        {
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
}
