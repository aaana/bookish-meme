package server;

import java.sql.*;

/**
 * Created by Administrator on 2016/3/19.
 */
public class LoginServer {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    public boolean login(String name,String password) throws Exception {
        boolean result = false;
        Connection connection = threadLocal.get();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                PreparedStatement preparedStatement = connection.prepareStatement("select password from user where name = ?");
                preparedStatement.setString(1,name);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()&&resultSet.getString(1).equals(password)){
                    result = true;
                }
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }
}