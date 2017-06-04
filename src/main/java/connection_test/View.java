package connection_test;


import pl.edu.uj.wzorce.MainFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class View extends JFrame implements KeyListener {
    private Controller controler;
    void addControler(Controller controler){this.controler = controler;}

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
            addKeyListener(this);
            setVisible(true);
        });

    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch(keyEvent.getKeyChar()){
            case 'w':
            case 'W':
                controler.moveUp();
                break;
            case 'a':
            case 'A':
                controler.moveLeft();
                break;
            case 's':
            case 'S':
                controler.moveDown();
                break;
            case 'd':
            case 'D':
                controler.moveRight();
        }
        switch(keyEvent.getKeyCode()){
            case 37:
                controler.moveLeft();
                break;
            case 38:
                controler.moveUp();
                break;
            case 39:
                controler.moveRight();
                break;
            case 40:
                controler.moveDown();
                break;

        }
    }
}
