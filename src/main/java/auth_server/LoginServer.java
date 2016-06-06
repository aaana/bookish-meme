package auth_server;

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
                PreparedStatement preparedStatement = connection.prepareStatement("select * from user where name = ?");
                preparedStatement.setString(1,name);
                ResultSet resultSet = preparedStatement.executeQuery();

                //登陆成功同时返回groupId
                if(resultSet.next()&&resultSet.getString(2).equals(password)){
                    result = true;
                }
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    private boolean isExist(String name) throws Exception {
        Connection connection = threadLocal.get();
        boolean result = false;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from user where name = ?");
                preparedStatement.setString(1,name);
                ResultSet resultSet = preparedStatement.executeQuery();

                result = resultSet.next();
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    public int register(String name,String password) throws Exception{
        Connection connection = threadLocal.get();

        if (isExist(name)){
            return -1;
        }

        int result = 0;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql="insert into user values(?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2,password);
                result = preparedStatement.executeUpdate();
//                sql = "insert into user_group VALUES(?,?)";
//                preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setString(1,name);
//                preparedStatement.setString(2,"0");
//                int temp = preparedStatement.executeUpdate();
//                result = result<temp?result:temp;

            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    public int modifyPwd(String name, String password) throws Exception {
        Connection connection = threadLocal.get();

        if (!isExist(name)){
            return -1;
        }

        int result = 0;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql="UPDATE user set password = ? where name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, password);
                preparedStatement.setString(2,name);
                result = preparedStatement.executeUpdate();
//                sql = "insert into user_group VALUES(?,?)";
//                preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setString(1,name);
//                preparedStatement.setString(2,"0");
//                int temp = preparedStatement.executeUpdate();
//                result = result<temp?result:temp;

            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

}