package pl.edu.uj.wzorce.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static void main(String[] args) {
        ServerSocket listener = null;
        ConnectionPool.getInstance();

        try {
            listener = new ServerSocket(9090);
            while (true) {
                Socket socket = listener.accept();
                new ServerResponder(socket).start();
            }
        } catch (Exception e) {
            try {
                listener.close();
            } catch (IOException e1) {
            }
        }
    }


}
        
        
