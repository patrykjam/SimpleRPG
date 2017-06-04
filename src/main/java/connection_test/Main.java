package connection_test;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        Model model = new Model();
        Controller controler = new Controller();
        View view = new View();
        view.addControler(controler);
        controler.addModel(model);
        controler.addView(view);
        controler.showView();
    }
}
