package connection_test;


import org.json.JSONException;
import org.json.JSONObject;
import pl.edu.uj.wzorce.*;

import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Controler {
    Model model;
    View view;
    Socket s;
    BufferedReader input;
    PrintWriter out;
    boolean loggedIn = false;
    Player player;

    public Controler() {
        String serverAddress = "localhost";
        try {
            s = new Socket(serverAddress, 9090);
            input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException e) {
        }
        while (!loggedIn) {
            login();
        }
    }//Constructor

    public void addModel(Model model) {
        this.model = model;
        initializeFields(11, 80);
        addPlayer(player);
    }

    public void addView(View view) {
        this.view = view;
    }

    public void login() {
        try {
            Scanner scanner = new Scanner(System.in);
            String login;
            String pwd;
            System.out.print("Login: ");
            login = scanner.next();
            System.out.print("Password: ");
            pwd = scanner.next();
            JSONObject jsonObject = new JSONObject();
            jsonObject
                    .accumulate("action", "login")
                    .accumulate("login", login)
                    .accumulate("password", pwd);
            out.println(jsonObject.toString());
            String answer = input.readLine();
            JSONObject JSONAnswer = new JSONObject(answer);
            System.out.println(answer);
            if (JSONAnswer.getBoolean("logged")) {
                loggedIn = true;
                if (JSONAnswer.getString("profession").equals("mage")) {
                    player = new Mage(10, 10, 10, 10, 5); //TODO: dodać hp, mp, atk do db?
                    player.setX_Axis(JSONAnswer.getInt("x_axis"));
                    player.setY_Axis(JSONAnswer.getInt("y_axis"));
                    player.setId(JSONAnswer.getInt("player_id"));
                }
            } else {
                System.out.println("Wrong credentials");
            }
        } catch (IOException | JSONException e) {
        }

    }


    public void initializeFields(int dimension, int fieldSize) {
        Field[][] fields = new Field[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                fields[i][j] = new Field(fieldSize, Color.WHITE);
                Random random = new Random();
                if (random.nextBoolean()) {
                    fields[i][j].setFieldType(FIELD_TYPE.GRASS);
                } else {
                    fields[i][j].setFieldType(FIELD_TYPE.WATER);
                }

                Border border;
                if (i < dimension - 1) {
                    if (j < dimension - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else if (j < dimension - 1) {
                    border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                } else {
                    border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                }
                fields[i][j].setBorder(border);
            }
        }
        model.setFields(fields);
    }

    public void showView() {
        if (model.getMainFrame() == null)
            model.setMainFrame(new MainFrame(model.getFields()));
        view.showFrame(model.getMainFrame());
    }

    public void moveUp() {
        try {
            out.println(buildMoveJson("up"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int fieldLen = model.getFields().length;
        FIELD_TYPE fieldType = model.getFields()[fieldLen / 2 - 1][fieldLen / 2].getFieldType();
        if (fieldType != FIELD_TYPE.WALL && fieldType != FIELD_TYPE.WATER)
            model.moveUp();
    }

    public void moveLeft() {
        try {
            out.println(buildMoveJson("left"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int fieldLen = model.getFields().length;
        FIELD_TYPE fieldType = model.getFields()[fieldLen / 2][fieldLen / 2 - 1].getFieldType();
        if (fieldType != FIELD_TYPE.WALL && fieldType != FIELD_TYPE.WATER)
            model.moveLeft();
    }

    public void moveRight() {
        try {
            out.println(buildMoveJson("right"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int fieldLen = model.getFields().length;
        FIELD_TYPE fieldType = model.getFields()[fieldLen / 2][fieldLen / 2 + 1].getFieldType();
        if (fieldType != FIELD_TYPE.WALL && fieldType != FIELD_TYPE.WATER)
            model.moveRight();
    }

    public void moveDown() {
        try {
            out.println(buildMoveJson("down"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int fieldLen = model.getFields().length;
        FIELD_TYPE fieldType = model.getFields()[fieldLen / 2 + 1][fieldLen / 2].getFieldType();
        if (fieldType != FIELD_TYPE.WALL && fieldType != FIELD_TYPE.WATER)
            model.moveDown();
    }

    public void addPlayer(Player player) {
        model.setPlayer(player, "down");
    }

    private JSONObject buildMoveJson(String direction) throws JSONException {
        return new JSONObject()
                .accumulate("action", "move")
                .accumulate("move", direction);
    }

}
