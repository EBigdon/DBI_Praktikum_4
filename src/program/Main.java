package program;

import com.mysql.cj.xdevapi.Table;
import gui.StartPanel;

import javax.swing.WindowConstants;
import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

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
        TableManager tm = new TableManager();
        try {
            tm.conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            System.out.println(tm.conn.getTransactionIsolation());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
