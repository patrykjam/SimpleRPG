package connection_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";
        Socket s = new Socket(serverAddress, 9090);
        BufferedReader input =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out =
                new PrintWriter(s.getOutputStream(), true);
        out.println(123);
        String answer = input.readLine();
        System.out.println(answer);
        System.exit(0);
    }
}