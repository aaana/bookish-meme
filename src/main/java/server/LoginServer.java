package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Administrator on 2016/3/19.
 */
public class LoginServer {
//    public static void main(String[] args){
//        String name = "100",password="123456";
//        LoginServer loginServer = new LoginServer();
//        try {
//            System.out.println(loginServer.login(name,password));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    public boolean login(String name,String password) throws Exception {
        boolean result = false;
        Connection connection = threadLocal.get();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                Statement stat = connection.createStatement();
                ResultSet resultSet = stat.executeQuery("select password from user where name = '"+name+"'");
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