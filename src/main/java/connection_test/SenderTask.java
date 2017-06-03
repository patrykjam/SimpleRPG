package connection_test;
import java.io.PrintWriter;
import java.util.TimerTask;

class SenderTask extends TimerTask {
    int User_id;
    PrintWriter out;
    public SenderTask(int user_id,PrintWriter out){}
    public void run() {
        //wysyłanie mapy

    }
}//koniec klasy CustomTask