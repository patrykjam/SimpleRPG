package pl.edu.uj.wzorce;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Field extends JPanel {

    private int size;
    private BufferedImage bufferedImage = null;
    private Image image;


    Field(int size, Color color) {
        this.size = size;
        setBackground(color);
    }

    void setPlayer() {
        try {
            bufferedImage = ImageIO.read(new File("images/stickman.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaled = bufferedImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(scaled);
        JLabel label = new JLabel(image);
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }


    void setBackgroundImage(String path) {
        try {
            bufferedImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = bufferedImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
