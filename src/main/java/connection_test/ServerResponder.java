package connection_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ServerResponder extends Thread {
    Socket socket = new Socket();
    public  ServerResponder(Socket socket){this.socket=socket;}
    public void run() {
        try {
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
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