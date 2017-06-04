package connection_test;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class ServerResponder extends Thread {
    Socket socket = new Socket();
    BufferedReader input;
    PrintWriter out;

    int User_id = -1;
    int X_Asix = 1;
    int Y_Asix = 1;
    List<String> equipment = new ArrayList<String>();
    String proffesion = "";
    Fasade fasade = new Fasade();

    public ServerResponder(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            boolean logged = false;
            boolean flag = true;

            while(!logged) {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String request = input.readLine();
                System.out.println(request);

                JSONObject JSONRequest = new JSONObject(request);
                String action = JSONRequest.getString("action");
                if (action.equals("login")) {
                    User_id = fasade.login(JSONRequest);
                    if (User_id != -1) {
                        proffesion = fasade.getProffesion(User_id);
                        X_Asix = fasade.getXposition(User_id);
                        Y_Asix = fasade.getYposition(User_id);
                        JSONObject JSONResponse = new JSONObject()
                                .accumulate("logged", true)
                                .accumulate("x_axis", X_Asix)
                                .accumulate("y_axis", Y_Asix)
                                .accumulate("profession", proffesion)
                                .accumulate("player_id", User_id);
                        out.println(JSONResponse);
                        logged = true;
                        while (flag) {
                            if (User_id != -1) {
                                Timer timer = new Timer();
                                timer.schedule(new SenderTask(User_id, out), 0, 1000); //uruchomienie za 0s, a potem co 1s
                                // ustawiamy wysyłanie refreshu mapy co sekundę
                                flag = false;
                            }
                        }
                    } else {
                        out.println(new JSONObject().accumulate("logged", false));
                    }
                }
            }

        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException e1) {
            }
        }
    }

}