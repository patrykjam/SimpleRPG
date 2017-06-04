package connection_test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Fasade {
    public int login(JSONObject data) throws SQLException, JSONException {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT USER_ID,PASSWORD FROM users where LOGIN='%s'",data.getString("login"));
            ResultSet rs = stmt.executeQuery(sql);
            connectionPool.releaseConnection(connection);
            if(!rs.next())return -1;
            String pass = rs.getString("PASSWORD");
            if(!pass.equals(data.getString("password"))){return -1;}
            return rs.getInt("USER_ID");
    }

    public String getProffesion(int user_id) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement stmt = connection.createStatement();
        String sql = String.format("SELECT proffesion FROM users where user_id='%s'", user_id);
        ResultSet rs = stmt.executeQuery(sql);
        connectionPool.releaseConnection(connection);
        rs.next();
        return rs.getString("proffesion");
    }

    public void moveUser(int user_id, String direction) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement stmt = connection.createStatement();
        int x;
        int y;
        switch (direction) {
            case "right":
                x = 1;
                y = 0;
                break;
            case "left":
                x = -1;
                y = 0;
                break;
            case "up":
                x = 0;
                y = 1;
                break;
            case "down":
                x = 0;
                y = -1;
                break;
            default:
                return;
        }

        String sql = String.format("UPDATE users_positions SET X_Axis = X_Axis + '%s', Y_Axis = Y_Axis + '%s' WHERE user_id = '%s'",x, y, user_id);
        stmt.executeUpdate(sql);
        connectionPool.releaseConnection(connection);
    }

    public int getXposition(int user_id) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement stmt = connection.createStatement();
        String sql = String.format("SELECT X_Axis FROM users_positions where user_id='%s'", user_id);
        ResultSet rs = stmt.executeQuery(sql);
        connectionPool.releaseConnection(connection);
        rs.next();
        return rs.getInt("X_Axis");
    }
    public int getYposition(int user_id) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement stmt = connection.createStatement();
        String sql = String.format("SELECT Y_Axis FROM users_positions where user_id='%s'", user_id);
        ResultSet rs = stmt.executeQuery(sql);
        connectionPool.releaseConnection(connection);
        rs.next();
        return rs.getInt("Y_Axis");
    }

    public JSONObject getMap(int user_id) throws SQLException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement stmt = connection.createStatement();
        JSONObject map = new JSONObject();
        String sql = String.format("SELECT X_Axis FROM users_positions where user_id='%s'", user_id);
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int X =  rs.getInt("X_Axis");
        sql = String.format("SELECT Y_Axis FROM users_positions where user_id='%s'", user_id);
        rs = stmt.executeQuery(sql);
        rs.next();
        int Y = rs.getInt("Y_Axis");
        //SELECT * FROM rpg.map WHERE X_Axis BETWEEN 20 AND 30 AND Y_AXIS BETWEEN 25 AND 35;
        sql = String
                .format("SELECT * FROM map WHERE X_AXIS BETWEEN %d AND %d AND Y_AXIS BETWEEN %d AND %d",
                X - 5, X + 5, Y - 5, Y + 5);
        //TODO: dodac do sql ^^^ przedmioty i potwory, sql powinien być posortowany: najpierw lewe dolne pole, później pole na górze od lewego dolnego itd.
        rs = stmt.executeQuery(sql);
        int i = 0;
        try {
            while (rs.next()) {
                System.out.println(rs.getString("type"));
                JSONArray items = new JSONArray();
                JSONArray monsters = new JSONArray();
                JSONObject field = new JSONObject()
                        .accumulate("type", rs.getString("type"))
                        .accumulate("items", items)
                        .accumulate("monsters", monsters);
                // TODO: dodać dwa jsonArray(?) do field^: jeden z przedmiotami drugi z potworami
                map.accumulate(Integer.toString(i++), field);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
        return map;
    }
}
