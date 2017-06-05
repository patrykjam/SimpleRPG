package pl.edu.uj.wzorce.server;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;

public class ServerResponder extends Thread {
    private Socket socket = new Socket();
    private BufferedReader input;
    private PrintWriter out;

    private int User_id = -1;
    private int X_Axis = 1;
    private int Y_Axis = 1;
    private String profession = "";
    private Facade facade = new Facade();
    private Timer timer;

    public ServerResponder(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            boolean logged = false;
            boolean flag = true;

            while (!logged) {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String request = input.readLine();
                System.out.println(request);

                JSONObject JSONRequest = new JSONObject(request);
                String action = JSONRequest.getString("action");
                if (action.equals("login")) {
                    User_id = facade.login(JSONRequest);
                    if (User_id != -1) {
                        logged = true;
                        profession = facade.getProfession(User_id);
                        X_Axis = facade.getXposition(User_id);
                        Y_Axis = facade.getYposition(User_id);
                        JSONObject JSONResponse = new JSONObject()
                                .accumulate("logged", true)
                                .accumulate("x_axis", X_Axis)
                                .accumulate("y_axis", Y_Axis)
                                .accumulate("profession", profession)
                                .accumulate("player_id", User_id);
                        out.println(JSONResponse);
                        while (flag) {
                            if (User_id != -1) {
                                timer = new Timer();
                                timer.schedule(new SenderTask(User_id, out), 0, 1000); //uruchomienie za 0s, a potem co 1s
                                // ustawiamy wysyłanie refreshu mapy co sekundę
                                flag = false;
                            }
                        }
                    } else {
                        out.println(new JSONObject().accumulate("logged", false));
                    }
                }

                while (true) {
                    String req = input.readLine();
                    JSONObject JSONReq = new JSONObject(req);
                    if (JSONReq.getString("action").equals("move")) {
                        facade.moveUser(User_id, JSONReq.getString("move"));
                    }
                }
            }

        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException e1) {
            }
            timer.cancel();
        }
    }

}