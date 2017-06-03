package pl.edu.uj.wzorce;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MainFrame extends JPanel {

    MainFrame(int dimension) {
        int fieldSize = 80;
        Random random = new Random();
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                gridBagConstraints.gridx = col;
                gridBagConstraints.gridy = row;

                Field panel = new Field(fieldSize, Color.WHITE);
                if (random.nextBoolean()) {
                    panel.setBackgroundImage("images/trees.png");
                } else {
                    panel.setBackgroundImage("images/water.png");
                }
                if (row == dimension / 2 && col == dimension / 2)
                    panel.setPlayer();
                Border border;
                if (row < dimension - 1) {
                    if (col < dimension - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else if (col < dimension - 1) {
                    border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                } else {
                    border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                }
                panel.setBorder(border);
                add(panel, gridBagConstraints);
            }
        }
    }

    public MainFrame(Field[][] fields){
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        for (int row = 0; row < fields.length; row++) {
            for (int col = 0; col < fields.length; col++) {
                gridBagConstraints.gridx = col;
                gridBagConstraints.gridy = row;
                add(fields[row][col], gridBagConstraints);
            }
        }
    }
}
