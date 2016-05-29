package DBserver;

import channel.Manager;
import message.ChatContent;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huanganna on 16/5/29.
 */
public class DBServerImpl implements DBServer {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    @Override
    public List<ChatContent> getALLMessageByGid(int gid) throws Exception {

        System.out.println("in getAllMessage");
        Connection connection = threadLocal.get();

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

    @Override
    public int delete(int gid, int number) throws Exception {

        System.out.println("in delete");
        Connection connection = threadLocal.get();
        int result = 0;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);

                String sql = "select count() from message where gid = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,gid);
                ResultSet resultSet = preparedStatement.executeQuery();
                int sum = 0;
                if(resultSet.next()){
                    sum = resultSet.getInt(1);
                } else{
                    return -1;
                }

                number = sum - number;
                if(number > 0) {
                    sql = "delete from message where rowid in(select rowid from message where gid = ? limit ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, gid);
                    preparedStatement.setInt(2, number);
                    result = preparedStatement.executeUpdate();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    @Override
    public int insert(ChatContent chatContent) throws Exception {
        System.out.println("in insert");
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
                preparedStatement.setString(2,chatContent.getSender());
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

    @Override
    public Map<String,Integer> getGidAndUid() throws Exception {
        System.out.println("in getGidAndUid");

        Connection connection = threadLocal.get();
        Map<String,Integer> result = new HashMap<String,Integer>();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql="SELECT name,groupId from user ";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while(resultSet.next()){
                    System.out.println(resultSet.getString(1));
                    System.out.println(resultSet.getInt(2));
                    result.put(resultSet.getString(1),resultSet.getInt(2));
                }

            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    @Override
    public int getGidByAcc(int account) throws Exception {

        System.out.println("in getGidByAcc");
        Connection connection = threadLocal.get();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql="SELECT groupId from user where name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,account);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) return resultSet.getInt(1);
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return -1;    }
}
