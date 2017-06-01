package connection_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerResponder extends Thread {
    Socket socket = new Socket();
    BufferedReader input;
    PrintWriter out;

    int User_id = -1;
    int X_Asix = 1;
    int Y_Asix = 1;
    List<String> equipment = new ArrayList<String>();
    String proffesion = "";

    public  ServerResponder(Socket socket){this.socket=socket;}
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out   = new PrintWriter(socket.getOutputStream(), true);

            boolean flag = true;

            while(flag) {
                String question = input.readLine(); // pierwsza linia logowanie.
            }


            while (true) {

                String question = input.readLine();
                System.out.println(question);
                out.println(question);
            }
        }catch (Exception e){
            try {socket.close();} catch (IOException e1) {}
        }
    }

}