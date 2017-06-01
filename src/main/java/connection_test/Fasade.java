package connection_test;

import org.json.JSONObject;
import org.json.JSONException;



public class Fasade {
    public int login(String data){
        try {
            JSONObject clientRequest = new JSONObject(data);
        }catch (JSONException e){return -1;}

        return -1;
    }




}
