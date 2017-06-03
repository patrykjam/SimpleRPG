package pl.edu.uj.wzorce;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

public class Field extends JPanel {

    private int size;
    private Image image;
    private String imgPath;


    public Field(int size, Color color) {
        this.size = size;
        setBackground(color);
    }

    public void setPlayer() {
        URL resource = getClass().getClassLoader().getResource("images/stickman.png");
        if (resource != null) {
            Image scaled = new ImageIcon(resource).getImage()
                    .getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(scaled);
            JLabel label = new JLabel(image);
            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
        }
    }


    public void setBackgroundImage(String path) {
        imgPath = path;
        BufferedImage bufferedImage = ImageCollection.getInstance().getBufferedImage(path);
        if (bufferedImage != null) {
            image = bufferedImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        }
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
