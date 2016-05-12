package server;

import message.ChatContent;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/11.
 */
public class DBOperate {

    public static void main(String[] args) throws InterruptedException{
        DBOperate dbOperate = new DBOperate();
        List<ChatContent> chatContents = new ArrayList<ChatContent>();
//        try {
//            chatContents=dbOperate.getALLMessageByGid(1);
//            for(int i=0;i<chatContents.size();i++){
//                System.out.println(chatContents.get(i).getMessage());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            dbOperate.delete(1,3);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    public  List<ChatContent> getALLMessageByGid(int gid) throws Exception {
        Connection connection = threadLocal.get();
        int result = 0;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql="select * from message where gid = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,gid);
                ResultSet resultSet = preparedStatement.executeQuery();

                List<ChatContent> chatContents = new ArrayList<ChatContent>();
                //登陆成功同时返回groupId
                while(resultSet.next()){
                    ChatContent chatContent = new ChatContent(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
                    chatContents.add(chatContent);
                }
                return chatContents;
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return null;
    }

    public int delete(int gid,int number) throws Exception {
        Connection connection = threadLocal.get();
        int result = 0;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql="delete from message where rowid in(select rowid from message where gid = ? limit ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,gid);
                preparedStatement.setInt(2,number);
                result = preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    public int insert(ChatContent chatContent) throws Exception {
        Connection connection = threadLocal.get();
        int result = 0;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql="insert into message values(?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,chatContent.getGroupId());
                preparedStatement.setString(2,chatContent.getAccount());
                preparedStatement.setString(3, chatContent.getSendDate());
                preparedStatement.setString(4, chatContent.getMessage());
                result = preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    public  Map<String,Integer> getGidAndUid(ChatContent chatContent) throws Exception {
        Connection connection = threadLocal.get();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql="SELECT username,gid from user";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                Map<String,Integer> result = new HashMap<String, Integer>();
                while(resultSet.next()){
                    result.put(resultSet.getString(2),resultSet.getInt(1));
                }
                return result;
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return null;
    }
}
