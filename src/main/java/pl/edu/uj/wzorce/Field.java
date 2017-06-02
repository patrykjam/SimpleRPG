package pl.edu.uj.wzorce;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Field extends JPanel {

    private int size;
    private BufferedImage bufferedImage = null;
    private Image image;


    public Field(int size, Color color) {
        this.size = size;
        setBackground(color);
    }

    public void setPlayer() {
        URL resource = getClass().getClassLoader().getResource("images/stickman.png");
        if(resource != null) {
            Image scaled = new ImageIcon(resource).getImage()
                    .getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(scaled);
            JLabel label = new JLabel(image);
            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
        }
    }


    public void setBackgroundImage(String path) {
        URL resource = getClass().getClassLoader().getResource(path);
        if(resource != null) {
            try {
                bufferedImage = ImageIO.read(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image = bufferedImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }

    @Override
    public void paintComponent(Graphics g) {
        if(image != null)
            g.drawImage(image, 0, 0, null);
        else
            super.paintComponent(g);
    }
}
