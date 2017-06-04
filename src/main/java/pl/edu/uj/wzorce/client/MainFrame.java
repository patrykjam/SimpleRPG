package pl.edu.uj.wzorce.client;

import pl.edu.uj.wzorce.client.Field;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JPanel {

    public MainFrame(Field[][] fields) {
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
