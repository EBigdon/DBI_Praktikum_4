package com.company;

public class NTpsManager extends Thread {
    public static int n;


    public NTpsManager(final int myN) throws Exception {
        n=myN;
        createOneTps();
    }

    public static void createOneTps() throws Exception {
        TableManager myDatabase = new TableManager();
        myDatabase.fastFillAccounts(n);
    }
}

