package gui;

import program.LoadingScreen;
import program.LoadingScreenRunnable;
import program.Parameters;
import program.TableManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

public class SetupButtonScript implements ActionListener {
    /**
     * Method that gets performed when setup button is pressed.
     * @param e waits for the button press
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        LoadingScreenRunnable loadingScreenRunnable = new LoadingScreenRunnable("Loading");
        loadingScreenRunnable.start();
        Parameters.myLoadingScreen = new LoadingScreen();
    }
}
