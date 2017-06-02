package connection_test;


import pl.edu.uj.wzorce.Field;
import pl.edu.uj.wzorce.MainFrame;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private Controler controler;
    void addControler(Controler controler){this.controler = controler;}

    View(){

    }

    public void showFrame(MainFrame mainFrame){
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }
            add(mainFrame);
            setSize(1000, 1000);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        });

    }

}
