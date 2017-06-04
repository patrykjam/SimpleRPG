package connection_test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        if(direction.equals("right")){x = 1; y = 0;}
        else if(direction.equals("left")){x = -1; y = 0;}
        else if(direction.equals("up")){x = 0; y = 1;}
        else if(direction.equals("down")){x = 0; y = -1;}
        else {return;}

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




}
