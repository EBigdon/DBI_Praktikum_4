package GUI;
import com.company.LoadDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartAction implements ActionListener {
    public void actionPerformed(ActionEvent event){
        LoadDriver ld = new LoadDriver(100);
    }
}
