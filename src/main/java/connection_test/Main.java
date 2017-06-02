package connection_test;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Model model = new Model();
        Controler controler = new Controler();
        View view = new View();
        view.addControler(controler);
        controler.addModel(model);
        controler.addView(view);
        controler.showView();
//        controler.run();
    }
}
