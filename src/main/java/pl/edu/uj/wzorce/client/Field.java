package pl.edu.uj.wzorce.client;

import com.sun.istack.internal.NotNull;
import pl.edu.uj.wzorce.common.Monster;
import pl.edu.uj.wzorce.common.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Field extends JPanel {

    private int size;
    private Image image;
    private FIELD_TYPE fieldType;
    private JLabel label;
    private Monster monster;
    private ArrayList<Item> itemArrayList = new ArrayList<>();


    public Field(int size, Color color) {
        this.size = size;
        setBackground(color);
    }

    public void addMonster(Monster monster) {
        this.monster = monster;
        Image scaled = ImageCollection.getInstance()
                .getImage("images/" + monster.getName() + ".png", size);
        ImageIcon image = new ImageIcon(scaled);
        JLabel label = new JLabel(image);
        setLayout(new BorderLayout());
        add(label, BorderLayout.LINE_START);
        repaint();
    }

    public void addItem(Item item) {
        itemArrayList.add(item);
        Image scaled = ImageCollection.getInstance()
                .getImage("images/" + item.getName() + ".png", size);
        ImageIcon image = new ImageIcon(scaled);
        JLabel label = new JLabel(image);
        setLayout(new BorderLayout());
        add(label, BorderLayout.LINE_START);
        repaint();
    }

    public void removeMonsterAndItems() {
        monster = null;
        itemArrayList = new ArrayList<>();
    }

    public void setPlayer(Player player, String direction) {
        if (label != null)
            remove(label);
        Image scaled = ImageCollection.getInstance()
                .getImage("images/" + player.getPLAYER_CLASS() + direction + ".png", size);
        ImageIcon image = new ImageIcon(scaled);
        label = new JLabel(image);
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        repaint();
    }


    public void setFieldType(FIELD_TYPE fieldType) {
        this.fieldType = fieldType;
        setBackgroundImage(fieldType);
    }

    private void setBackgroundImage(@NotNull FIELD_TYPE fieldType) {
        image = ImageCollection.getInstance().getImage(FIELD_TYPE.getPath(fieldType), size);
    }

    public void clearImage() {
        fieldType = null;
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
        if (field.fieldType != null) {
            this.setFieldType(field.fieldType);
        } else
            this.clearImage();
        if (field.monster != null)
            this.monster = (Monster) field.monster.copy();
    }

    public FIELD_TYPE getFieldType() {
        return fieldType;
    }
}
