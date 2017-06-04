package connection_test;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.TimerTask;

class SenderTask extends TimerTask {
    private int User_id;
    private PrintWriter out;
    private Facade facade = new Facade();

    public SenderTask(int user_id, PrintWriter out) {
        this.User_id = user_id;
        this.out = out;
    }

    public void run() {
        try {
            out.println(facade.getMap(User_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //wysyłanie mapy

    }
}//koniec klasy CustomTask