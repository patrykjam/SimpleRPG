package connection_test;

import pl.edu.uj.wzorce.Mage;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Model model = new Model();
        Controler controler = new Controler();
        Mage mage = new Mage(10, 10, 10, 10, 5);
        View view = new View();
        view.addControler(controler);
        controler.addModel(model);
        controler.addView(view);
        controler.addPlayer(mage);
        controler.showView();
//        controler.run();
    }
}
