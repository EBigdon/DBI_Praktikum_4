package GUI;
import com.company.LoadDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartAction implements ActionListener {
    public void actionPerformed(ActionEvent event){
        //LoadDriver ld = new LoadDriver(100);
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 5; j++) {
                LoadDriver runnableLoadDriver = new LoadDriver("Thread." + Integer.toString(i+1) + "." + Integer.toString(j+1), 100);
                runnableLoadDriver.start();
            }
        }
    }
}
