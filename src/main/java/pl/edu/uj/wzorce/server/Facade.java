package pl.edu.uj.wzorce.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.edu.uj.wzorce.common.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Facade {
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

    public String getProfession(int user_id) throws SQLException {
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

        sql = String.format("Select *  from users_positions where user_id = '%s'", user_id);
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int temp_x = rs.getInt("X_Axis");
        int temp_y = rs.getInt("Y_Axis");

        sql = String.format("Select * from souls where X_Axis = %d and Y_Axis = %d", temp_x, temp_y);
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            pickUpItem(user_id, rs.getString("name"), rs.getInt("size"), temp_x, temp_y);
        }

        sql = String.format("Select * from monsters where X_Axis = %d and Y_Axis = %d", temp_x, temp_y);
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            Monster monster;
            String name = rs.getString("name");
            if (name == "Ladybug") monster = new Ladybug(rs.getInt("hp"));
            else monster = new Griffin(rs.getInt("hp"));
            fight(user_id, monster, temp_x, temp_y);
        }


        connectionPool.releaseConnection(connection);
    }

    public void fight(int user_id, Monster monster, int x, int y) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement stmt = connection.createStatement();

        String sql = String.format("Select * from users where user_id = %d", user_id);
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        String proffesion = rs.getString("proffesion");
        Player player;
        System.out.println(proffesion);

        sql = String.format("Select * from users_stats where user_id = %d", user_id);
        rs = stmt.executeQuery(sql);
        rs.next();
        int hp_current = rs.getInt("hp_current");
        int hp_max = rs.getInt("hp_max");
        int mp_current = rs.getInt("mp_current");
        int mp_max = rs.getInt("mp_max");

        if (proffesion.equals("mage")) player = new Mage(hp_max, hp_current, mp_max, mp_current, 20);
        else player = new Archer(hp_max, hp_current, mp_max, mp_current, 10);

        System.out.println(player.getPLAYER_CLASS());

        while (player.getCURRENT_HP() > 0 && monster.getCurrHp() > 0) {
            player.addHP(-monster.getAtkVal());
            monster.addHp(-player.dealDmg());
        }

        if (monster.getCurrHp() <= 0) {
            sql = String.format("DELETE from monsters where X_Axis = %d and Y_Axis = %d", x, y);
            stmt.executeUpdate(sql);


            System.out.println(player.getCURRENT_MP());
            sql = String.format("UPDATE users_stats Set hp_max = hp_max+10, hp_current = %d, mp_current = %d where user_id = %d", player.getCURRENT_HP(), player.getCURRENT_MP(), user_id);
            stmt.executeUpdate(sql);
        }
        if (player.getCURRENT_HP() <= 0) {
            sql = String.format("UPDATE users_stats Set hp_max = hp_max-10, hp_current = hp_max, mp_current = mp_max where user_id = %d", user_id);
            stmt.executeUpdate(sql);

            sql = String.format("UPDATE users_positions Set X_Axis = 5, Y_Axis = 5  where user_id = %d", user_id);
            stmt.executeUpdate(sql);

        }


    }

    public void pickUpItem(int user_id, String name, int size, int x, int y) throws SQLException {


        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement stmt = connection.createStatement();
        String sql = String.format("Delete from souls where X_Axis = '%s' and Y_Axis = '%s'", x, y);
        stmt.executeUpdate(sql);

        sql = String.format("Select * from users_stats where user_id = '%s'", user_id);
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int hp_max = rs.getInt("hp_max");
        int mp_max = rs.getInt("mp_max");
        int hp_current = rs.getInt("hp_current");
        int mp_current = rs.getInt("mp_current");

        if (name.equals("mp")) mp_current += size;
        else hp_current += size;

        if (hp_current > hp_max) hp_current = hp_max;
        if (mp_current > mp_max) mp_current = mp_max;

        sql = String.format("UPDATE users_stats SET hp_current ='%s', mp_current = '%s' WHERE user_id = '%s'", hp_current, mp_current, user_id);
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
                        .put("items", items)
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
