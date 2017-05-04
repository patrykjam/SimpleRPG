package connection_test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException {
        Model model = new Model();
        Controler controler = new Controler();
        View view = new View();
        view.addControler(controler);
        controler.addModel(model);
        controler.addView(view);
        controler.run();
    }
}
