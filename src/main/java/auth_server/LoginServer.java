package auth_server;

import java.sql.*;

/**
 * Created by Administrator on 2016/3/19.
 */
public class LoginServer {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    public int login(String name,String password) throws Exception {
        int result = -1;
        Connection connection = threadLocal.get();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from user where name = ?");
                preparedStatement.setString(1,name);
                ResultSet resultSet = preparedStatement.executeQuery();

                //登陆成功同时返回groupId
                if(resultSet.next()&&resultSet.getString(2).equals(password)){
                    result = resultSet.getInt(3);
                }
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }
}