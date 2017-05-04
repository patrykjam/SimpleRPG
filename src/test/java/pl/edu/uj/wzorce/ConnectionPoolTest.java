//package pl.edu.uj.wzorce;
//
//import org.junit.Test;
//
//import java.sql.Connection;
//
//import static org.junit.Assert.*;
//
//public class ConnectionPoolTest {
//
//    /**
//     *Test if connection pool correctly releases connections
//     */
//    @Test
//    public void ConnectionNumberTest() {
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        Connection connection = connectionPool.getConnection();
//
//        int MAX_DB_CONNECTIONS = 20;
//
//        assertEquals(MAX_DB_CONNECTIONS - 1, connectionPool.getFreeConnectionNumber());
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(MAX_DB_CONNECTIONS, connectionPool.getFreeConnectionNumber());
//
//        connectionPool.releaseConnection(connection);
//
//        assertEquals(MAX_DB_CONNECTIONS, connectionPool.getFreeConnectionNumber());
//
//    }
//}