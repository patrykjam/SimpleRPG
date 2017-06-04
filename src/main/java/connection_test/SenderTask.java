package connection_test;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.TimerTask;

class SenderTask extends TimerTask {
    int User_id;
    PrintWriter out;
    Fasade fasade = new Fasade();

    public SenderTask(int user_id, PrintWriter out) {
        this.User_id = user_id;
        this.out = out;
    }

    public void run() {
        try {
            out.println(fasade.getMap(User_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //wysyłanie mapy

    }
}//koniec klasy CustomTask