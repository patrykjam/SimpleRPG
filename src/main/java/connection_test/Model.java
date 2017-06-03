package connection_test;

import pl.edu.uj.wzorce.Field;
import pl.edu.uj.wzorce.MainFrame;

public class Model {

    private Field[][] fields;
    private MainFrame mainFrame;
    int X_;
    int Y_;

    Model() {
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

    public void moveUp() {
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
