package pl.edu.uj.wzorce;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Random;

class MainFrame extends JPanel {

    MainFrame() {
        int DIM = 17;
        int fieldSize = 40;
        Random random = new Random();
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        for (int row = 0; row < DIM; row++) {
            for (int col = 0; col < DIM; col++) {
                gridBagConstraints.gridx = col;
                gridBagConstraints.gridy = row;

                Field panel = new Field(fieldSize, Color.WHITE);
                if (random.nextBoolean()) {
                    panel.setBackgroundImage(getClass().getResource("/images/trees.png").getPath());
                } else {
                    panel.setBackgroundImage(getClass().getResource("/images/water.png").getPath());
                }
                if (row == DIM / 2 && col == DIM / 2)
                    panel.setPlayer();
                Border border;
                if (row < DIM - 1) {
                    if (col < DIM - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else if (col < DIM - 1) {
                    border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                } else {
                    border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                }
                panel.setBorder(border);
                add(panel, gridBagConstraints);
            }
        }
    }
}
