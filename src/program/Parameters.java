package program;

import gui.StartPanel;

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
    public static int timeToRunInSec = 10;
    /**
     * Width of window.
     */
    public static final  int windowWidth = 1600;
    /**
     * Height of Window.
     */
    public static final int windowHeight = 900;
    /**
     * n from n-tps database.
     */
    public static int n = 100;
    /**
     * The Swing gui Buttonframe instance.
     */
    public static StartPanel frame = null;
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
    /**
     * Loading screen for setup.
     */
    public static LoadingScreen myLoadingScreen = null;
}
