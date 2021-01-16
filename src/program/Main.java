package program;

import gui.StartPanel;

import javax.swing.WindowConstants;


/**
* Our Main class for the benchmark database.
*/
@SuppressWarnings({"checkstyle:magicnumber"})
public class Main {

    /**
     * Our main function that gets called when program is executed.
     *
     * @param args String of supplied command-line-arguments
     */
    public static void main(final String[] args) {
        Parameters.frame = new StartPanel();
        Parameters.frame.setVisible(true);
        Parameters.frame.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE);
    }
}
