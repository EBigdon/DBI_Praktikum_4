package com.company;

import gui.ButtonFrame;

import javax.swing.*;

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
    public static final int timeToRunInSec = 10 * 20;
    /**
     * Width of window.
     */
    public static final  int windowWidth = 800;
    /**
     * Height of Window.
     */
    public static final int windowHeight = 400;
    /**
     * n from n-tps database.
     */
    public static int n = 100;
    /**
     * The Swing gui Buttonframe instance.
     */
    public static ButtonFrame frame = null;
    /**
     * Retval for first LoadDriver Instance.
     */
    public static long resultOne = 0;
    /**
     * Retval for first LoadDriver Instance.
     */
    public static long resultTwo = 0;
    /**
     * Return value for first LoadDriver Instance.
     */
    public static long resultThree = 0;
    /**
     * Return value for first LoadDriver Instance.
     */
    public static long resultFour = 0;
    /**
     * Return value for first LoadDriver Instance.
     */
    public static long resultFive = 0;
}
