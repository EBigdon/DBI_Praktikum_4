package GUI;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class ButtonFrame extends JFrame{
    public ButtonFrame(){
        setTitle("Load-Driver");
        setSize(Default_Width, Default_Height);

        ButtonPanel panel = new ButtonPanel();
        add(panel);
    }
    public static final  int Default_Width = 300;
    public static final int Default_Height = 200;
}
