package connection_test;


import pl.edu.uj.wzorce.Field;
import pl.edu.uj.wzorce.MainFrame;

import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

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
    public void addModel(Model model){
        this.model = model;
        initializeFields(11, 80);
    }
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


    public void initializeFields(int dimension, int fieldSize){
        Field[][] fields = new Field[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                fields[i][j] = new Field(fieldSize, Color.WHITE);
//                fields[i][j].setBackgroundImage("images/trees.png");
                Random random = new Random();
                if (random.nextBoolean()) {
                    fields[i][j].setBackgroundImage("images/trees.png");
                } else {
                    fields[i][j].setBackgroundImage("images/water.png");
                }

                if (i == dimension / 2 && j == dimension / 2)
                    fields[i][j].setPlayer();
                Border border;
                if (i < dimension - 1) {
                    if (j < dimension - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else if (j < dimension - 1) {
                    border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                } else {
                    border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                }
                fields[i][j].setBorder(border);
            }
        }
        model.setFields(fields);
    }

    public void showView(){
        if(model.getMainFrame() == null)
            model.setMainFrame(new MainFrame(model.getFields()));
        view.showFrame(model.getMainFrame());
    }

    public void moveUp(){
        //TODO: sprawdzic czy mozna ruszyc sie dana strone
        model.moveUp();

    }

    public void moveLeft(){
        model.moveLeft();


    }

    public void moveRight(){
        model.moveRight();


    }

    public void moveDown(){
        model.moveDown();


    }



}
