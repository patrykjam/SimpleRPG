package connection_test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controler {
    Model model;
    View view;
    Socket s;
    BufferedReader input;
    PrintWriter out;
    public Controler(){
        String serverAddress = "localhost";
        try {
            s = new Socket(serverAddress, 9090);
            input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException e){}
    }//Constructor
    public void addModel(Model model){this.model = model;}
    public void addView(View view){this.view = view;}
    public void run(){
        while (true){
            try{
                Thread.sleep(1000);
                out.println(123);
                String answer = null;
                answer = input.readLine();
                System.out.println(answer);
            } catch (IOException|InterruptedException e) {}
        }

    }


}
