package com.company;

public class Parameters {
    /**
     * url of Database.
     */
    public static String url = "jdbc:mysql://localhost/bench_database";
    /**
     * user of Database.
     */
    public static String username = "root";
    /**
     * password of Database.
     */
    public static String password = "";
    /**
     * Time to run in seconds for the LoadDriver.
     */
    public static final int timeToRunInSec = 10 * 60;
    /**
     * Width of window.
     */
    public static final  int windowWidth = 900;
    /**
     * Height of Window.
     */
    public static final int windowHeight = 600;
    /**
     * n from n-tps database.
     */
    public static int n = 100;

    public static long resultOne = 0;
    public static long resultTwo = 0;
    public static long resultThree = 0;
    public static long resultFour = 0;
    public static long resultFive = 0;
}
