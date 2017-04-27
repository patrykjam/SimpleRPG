import sun.awt.windows.ThemeReader;

import java.sql.Connection;

public class test {

    public static void main(String[] args) throws InterruptedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();

        System.out.println(connectionPool.getFreeConnectionNumber());

        Thread.sleep(5000);

        System.out.println(connectionPool.getFreeConnectionNumber());

        connectionPool.releaseConnection(connection);

        System.out.println(connectionPool.getFreeConnectionNumber());

    }

}
