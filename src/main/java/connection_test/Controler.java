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
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controler {
    private Model model;
    private View view;
    private Socket s;
    private BufferedReader input;
    private PrintWriter out;
    private boolean loggedIn = false;
    private Player player;
    private MapReceiver mapReceiver = new MapReceiver();


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
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(mapReceiver);
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
            if (JSONAnswer.getBoolean("logged")) {
                loggedIn = true;
                if (JSONAnswer.getString("profession").equals("mage")) {
                    player = new Mage(10, 10, 10, 10, 5); //TODO: dodać hp, mp, atk do db?
                    player.setId(JSONAnswer.getInt("player_id"));
                }
                if (JSONAnswer.getString("profession").equals("archer")) {
                    player = new Archer(10, 10, 10, 10, 5); //TODO: dodać hp, mp, atk do db?
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
        int fieldLen = model.getFields().length;
        FIELD_TYPE fieldType = model.getFields()[fieldLen / 2 - 1][fieldLen / 2].getFieldType();
        if (fieldType != FIELD_TYPE.WALL && fieldType != FIELD_TYPE.WATER) {
            model.moveUp();
            try {
                out.println(buildMoveJson("up"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveLeft() {
        int fieldLen = model.getFields().length;
        FIELD_TYPE fieldType = model.getFields()[fieldLen / 2][fieldLen / 2 - 1].getFieldType();
        if (fieldType != FIELD_TYPE.WALL && fieldType != FIELD_TYPE.WATER) {
            model.moveLeft();
            try {
                out.println(buildMoveJson("left"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveRight() {
        int fieldLen = model.getFields().length;
        FIELD_TYPE fieldType = model.getFields()[fieldLen / 2][fieldLen / 2 + 1].getFieldType();
        if (fieldType != FIELD_TYPE.WALL && fieldType != FIELD_TYPE.WATER) {
            model.moveRight();
            try {
                out.println(buildMoveJson("right"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveDown() {
        int fieldLen = model.getFields().length;
        FIELD_TYPE fieldType = model.getFields()[fieldLen / 2 + 1][fieldLen / 2].getFieldType();
        if (fieldType != FIELD_TYPE.WALL && fieldType != FIELD_TYPE.WATER) {
            model.moveDown();
            try {
                out.println(buildMoveJson("down"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlayer(Player player) {
        model.setPlayerWithDirection(player, "down");
    }

    private JSONObject buildMoveJson(String direction) throws JSONException {
        return new JSONObject()
                .accumulate("action", "move")
                .accumulate("move", direction);
    }

    private class MapReceiver implements Runnable {
        @Override
        public void run() {
            while(true){
                try {
                    String s = input.readLine();
                    if(model != null)
                        model.refreshMap(s);
                } catch (IOException e) {
                    break;
                }
            }
        }
    }
}
