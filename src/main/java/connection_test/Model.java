package connection_test;

import pl.edu.uj.wzorce.Field;
import pl.edu.uj.wzorce.MainFrame;
import pl.edu.uj.wzorce.Player;

public class Model {

    private Field[][] fields;
    private MainFrame mainFrame;
    private Player player;
    private int playerId;

    Model() {
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public Field[][] getFields() {
        return fields;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player, String direction) {
        this.player = player;
        if (fields != null) {
            fields[fields.length / 2][fields.length / 2].setPlayer(player, direction);
        }
    }

    public void moveUp() {
        player.setY_Axis(player.getY_Axis() - 1);
        setPlayer(player, "up");
        for (int i = fields.length - 1; i > 0; i--) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].copyFrom(fields[i - 1][j]);
            }
        }

        for (int i = 0; i < fields.length; i++) {
            fields[0][i].clearImage();
        }

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].revalidate();
                fields[i][j].repaint();
            }
        }
    }

    public void moveLeft() {
        player.setX_Axis(player.getX_Axis() - 1);
        setPlayer(player, "left");
        for (int i = 0; i < fields.length; i++) {
            for (int j = fields.length - 1; j > 0; j--) {
                fields[i][j].copyFrom(fields[i][j - 1]);
            }
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i][0].clearImage();
        }

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].revalidate();
                fields[i][j].repaint();
            }
        }
    }

    public void moveRight() {
        player.setX_Axis(player.getX_Axis() + 1);
        setPlayer(player, "right");
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length - 1; j++) {
                fields[i][j].copyFrom(fields[i][j + 1]);
            }
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i][fields.length - 1].clearImage();
        }

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].revalidate();
                fields[i][j].repaint();
            }
        }
    }

    public void moveDown() {
        setPlayer(player, "down");
        player.setY_Axis(player.getY_Axis() + 1);
        for (int i = 0; i < fields.length - 1; i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].copyFrom(fields[i + 1][j]);
            }
        }

        for (int i = 0; i < fields.length; i++) {
            fields[fields.length - 1][i].clearImage();
        }

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].revalidate();
                fields[i][j].repaint();
            }
        }
    }

    public void refreshMap() {

    }
}
