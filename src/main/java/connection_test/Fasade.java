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
        String sql = String.format("SELECT USER_ID,PASSWORD FROM users where LOGIN='%s'", data.getString("login"));
        ResultSet rs = stmt.executeQuery(sql);
        connectionPool.releaseConnection(connection);
        if (!rs.next()) return -1;
        String pass = rs.getString("PASSWORD");
        if (!pass.equals(data.getString("password"))) {
            return -1;
        }
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

        String sql = String.format("UPDATE users_positions SET X_Axis = X_Axis + '%s', Y_Axis = Y_Axis + '%s' WHERE user_id = '%s'", x, y, user_id);
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
        Statement stmt2 = connection.createStatement();
        JSONObject map = new JSONObject();
        String sql = String.format("SELECT X_Axis FROM users_positions where user_id='%s'", user_id);
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int X = rs.getInt("X_Axis");
        sql = String.format("SELECT Y_Axis FROM users_positions where user_id='%s'", user_id);
        rs = stmt.executeQuery(sql);
        rs.next();
        int Y = rs.getInt("Y_Axis");

        sql = String
                .format("SELECT * FROM users_stats WHERE user_id = %d",
                        user_id);
        rs = stmt.executeQuery(sql);
        rs.next();

        try {
            map.accumulate("hp_current", rs.getInt("hp_current"));
            map.accumulate("mp_current", rs.getInt("mp_current"));
            map.accumulate("hp_max", rs.getInt("hp_max"));
            map.accumulate("mp_max", rs.getInt("mp_max"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sql = String
                .format("SELECT * FROM map WHERE X_AXIS BETWEEN %d AND %d AND Y_AXIS BETWEEN %d AND %d",
                        X - 5, X + 5, Y - 5, Y + 5);
        rs = stmt.executeQuery(sql);
        int i = 0;
        try {
            while (rs.next()) {
                JSONArray items = new JSONArray();
                int temp_x = rs.getInt("X_AXIS");
                int temp_y = rs.getInt("Y_AXIS");
                sql = String
                        .format("SELECT * FROM monsters WHERE X_AXIS = %d AND Y_AXIS = %d",
                                temp_x, temp_y);
                ResultSet Temp_result_set = stmt2.executeQuery(sql);
                JSONObject monster = new JSONObject();
                if (Temp_result_set.next()) {
                    monster.accumulate("name", Temp_result_set.getString("name"))
                            .accumulate("hp", Temp_result_set.getInt("hp"));
                }

                sql = String
                        .format("SELECT * FROM souls WHERE X_AXIS = %d AND Y_AXIS = %d",
                                temp_x, temp_y);
                Temp_result_set = stmt2.executeQuery(sql);
                JSONObject item;
                if (Temp_result_set.next()) {
                    item = new JSONObject()
                            .accumulate("name", Temp_result_set.getString("name"))
                            .accumulate("size", Temp_result_set.getInt("size"));
                    items.put(item);
                }
                JSONObject field = new JSONObject()
                        .accumulate("type", rs.getString("type"))
                        .accumulate("items", items)
                        .accumulate("monsters", monster);
                map.accumulate(Integer.toString(i++), field);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
        return map;
    }
}
