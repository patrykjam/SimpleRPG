package pl.edu.uj.wzorce;

import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {

    private int size;
    private Image image;
    private String imgPath;


    public Field(int size, Color color) {
        this.size = size;
        setBackground(color);
    }

    public void setPlayer() {
        Image scaled = ImageCollection.getInstance().getImage("images/stickman.png", size);
        ImageIcon image = new ImageIcon(scaled);
        JLabel label = new JLabel(image);
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }


    public void setBackgroundImage(String path) {
        imgPath = path;
        image = ImageCollection.getInstance().getImage(path, size);
    }

    public void clearImage() {
        imgPath = null;
        image = null;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (image != null)
            g.drawImage(image, 0, 0, null);
        else
            super.paintComponent(g);
    }

    public void copyFrom(Field field) {
        if (field.imgPath != null)
            this.setBackgroundImage(field.imgPath);
        else
            this.clearImage();

        //TODO: copy

//        this.repaint();

    }
}
