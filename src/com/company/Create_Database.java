package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_Database {
    public static void create_database(Connection con){
        String query = "create Database bench_database";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table branches created");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "create table branches\n" +
                "( branchid int not null,\n" +
                " branchname char(20) not null,\n" +
                " balance int not null,\n" +
                " address char(72) not null,\n" +
                " primary key (branchid) );";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table branches created");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "create table accounts\n" +
                "( accid int not null,\n" +
                " name char(20) not null,\n" +
                " balance int not null,\n" +
                "branchid int not null,\n" +
                "address char(68) not null,\n" +
                "primary key (accid),\n" +
                "foreign key (branchid) references bench_database.branches(branchid)\n" +
                ");";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table Accounts created");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "create table tellers\n" +
                "( tellerid int not null,\n" +
                " tellername char(20) not null,\n" +
                " balance int not null,\n" +
                " branchid int not null,\n" +
                " address char(68) not null,\n" +
                " primary key (tellerid),\n" +
                " foreign key (branchid) references branches(branchid)\n" +
                "); ";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table Tellers created");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "create table history\n" +
                "( accid int not null,\n" +
                " tellerid int not null,\n" +
                " delta int not null,\n" +
                " branchid int not null,\n" +
                " accbalance int not null,\n" +
                " cmmnt char(30) not null,\n" +
                " foreign key (accid) references accounts(accid),\n" +
                " foreign key (tellerid) references tellers(tellerid),\n" +
                " foreign key (branchid) references branches(branchid) ); ";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table history created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
