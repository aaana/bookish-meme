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
    public List<ChatContent> getALLMessageByGid(String gid) throws Exception {

        System.out.println("in getAllMessage");
        Connection connection = threadLocal.get();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql = "select * from message where gid = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, gid);
                ResultSet resultSet = preparedStatement.executeQuery();

                List<ChatContent> chatContents = new ArrayList<ChatContent>();
                while (resultSet.next()) {
                    ChatContent chatContent = new ChatContent(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
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
    public int delete(String gid, int number) throws Exception {

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
                preparedStatement.setString(1, gid);
                ResultSet resultSet = preparedStatement.executeQuery();
                int sum = 0;
                if (resultSet.next()) {
                    sum = resultSet.getInt(1);
                } else {
                    return -1;
                }

                number = sum - number;
                if (number > 0) {
                    sql = "delete from message where rowid in(select rowid from message where gid = ? limit ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, gid);
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
                String sql = "insert into message values(?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, chatContent.getGroupId());
                preparedStatement.setString(2, chatContent.getSender());
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
    public Map<String, List<String>> getGidAndUid() throws Exception {
        System.out.println("in getGidAndUid");

        Connection connection = threadLocal.get();
        Map<String, List<String>> result = new HashMap<String, List<String>>();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql = "SELECT name,groupId from user_group ";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                    System.out.println(resultSet.getString(2));
                    String account = resultSet.getString(1);
                    String groupId = resultSet.getString(2);
                    if (result.containsKey(account)) {
                        result.get(account).add(groupId);
                    } else {
                        List<String> groups = new ArrayList<String>();
                        groups.add(groupId);
                        result.put(account, groups);
                    }

                }

            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    @Override
    //result == 0 失败
    public int groupNumIncreaseByOne(String groupId) throws Exception {
        Connection connection = threadLocal.get();
        int result = 0;
        int num = 0;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);

                PreparedStatement preparedStatement = connection.prepareStatement("SELECT num FROM groupInfo WHERE groupId = ?");
                preparedStatement.setString(1, groupId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    num = resultSet.getInt(1);
                    break;
                }
                preparedStatement = connection.prepareStatement("UPDATE groupInfo SET num = ? WHERE groupId = ?");
                preparedStatement.setInt(1, num + 1);
                preparedStatement.setString(2, groupId);
                result = preparedStatement.executeUpdate();

            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    @Override
    //result==0 失败
    public int addGroup(String name, String groupId) throws Exception {
        if(isInTheGroup(name,groupId))
            return -1;
        Connection connection = threadLocal.get();
        int result = 0;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user_group VALUES(?,?)");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, groupId);
                result = preparedStatement.executeUpdate();

            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        int temp = groupNumIncreaseByOne(groupId);
        result = result < temp ? result : temp;
        return result;
    }

    @Override
    public List<String> getGidByAcc(String account) throws Exception {

        System.out.println("in getGidByAcc");
        Connection connection = threadLocal.get();
        List<String> result = new ArrayList<String>();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql = "SELECT groupId from user_group where name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    result.add(resultSet.getString(1));
                }
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    @Override
    public List<String> getAllGroupIds() throws Exception {
        System.out.println("in getAllGroupIds");
        Connection connection = threadLocal.get();
        List<String> result = new ArrayList<String>();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql = "SELECT groupId from groupInfo";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    result.add(resultSet.getString(1));
                }
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    @Override
    public int createGroup(String groupId) throws Exception {
        System.out.println("in createGroup");

        if(groupIsExist(groupId)){
            return -1;
        }
        Connection connection = threadLocal.get();
        int result = 0;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql = "insert into groupInfo values (?,0);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,groupId);
                result = preparedStatement.executeUpdate();


            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;

    }

    private boolean groupIsExist(String groupId) throws Exception {
        Connection connection = threadLocal.get();
        boolean result = false;

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from groupInfo where groupId = ?");
                preparedStatement.setString(1,groupId);
                ResultSet resultSet = preparedStatement.executeQuery();

                result = resultSet.next();
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        return result;
    }

    @Override
    public boolean isInTheGroup(String account, String groupId) throws Exception{
        System.out.println("in isInTheGroup");
        Connection connection = threadLocal.get();
        List<String> result = new ArrayList<String>();

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:test.db");

                threadLocal.set(connection);
                String sql = "SELECT groupId from user_group where name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    result.add(resultSet.getString(1));
                }
            } catch (Exception e) {
                throw e;
            }
        }
        connection.close();
        if(result.contains(groupId)){
            return true;
        }
        return false;
    }

}
