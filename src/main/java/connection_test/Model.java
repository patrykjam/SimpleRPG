package connection_test;

import pl.edu.uj.wzorce.Field;
import pl.edu.uj.wzorce.MainFrame;

public class Model {

    private Field[][] fields;
    private MainFrame mainFrame;
    int X_;
    int Y_;

    Model(){
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
}
