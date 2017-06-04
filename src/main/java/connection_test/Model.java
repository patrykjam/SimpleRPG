package connection_test;

import org.json.JSONException;
import org.json.JSONObject;
import pl.edu.uj.wzorce.FIELD_TYPE;
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

    public void setPlayerWithDirection(Player player) {
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

    public void setPlayerWithDirection(Player player, String direction) {
        this.player = player;
        if (fields != null) {
            fields[fields.length / 2][fields.length / 2].setPlayer(player, direction);
        }
    }

    public void moveUp() {
        setPlayerWithDirection(player, "up");
        for (int i = fields.length - 1; i > 0; i--) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].copyFrom(fields[i - 1][j]);
            }
        }

        for (int i = 0; i < fields.length; i++) {
            fields[0][i].clearImage();
        }

        repaintMap();

    }

    public void moveLeft() {
        setPlayerWithDirection(player, "left");
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
        setPlayerWithDirection(player, "right");
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length - 1; j++) {
                fields[i][j].copyFrom(fields[i][j + 1]);
            }
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i][fields.length - 1].clearImage();
        }

        repaintMap();

    }

    public void moveDown() {
        setPlayerWithDirection(player, "down");
        for (int i = 0; i < fields.length - 1; i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].copyFrom(fields[i + 1][j]);
            }
        }

        for (int i = 0; i < fields.length; i++) {
            fields[fields.length - 1][i].clearImage();
        }

        repaintMap();
    }

    private void repaintMap() {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].revalidate();
                fields[i][j].repaint();
            }
        }
    }

    public void refreshMap(String json) {
        JSONObject map = null;
        try {
            map = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int f = 0;
        for (int j = 0; j < fields.length; j++) {
            for (int i = fields.length - 1; i >= 0; i--) {
                try {
                    if (map != null) {
                        JSONObject fieldInfo = map.getJSONObject(Integer.toString(f++));
                        switch (fieldInfo.getString("type")) {
                            case "grass":
                                fields[i][j].setFieldType(FIELD_TYPE.GRASS);
                                break;
                            case "sand":
                                fields[i][j].setFieldType(FIELD_TYPE.SAND);
                                break;
                            case "wall":
                                fields[i][j].setFieldType(FIELD_TYPE.WALL);
                                break;
                            case "water":
                                fields[i][j].setFieldType(FIELD_TYPE.WATER);
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        repaintMap();
    }
}
