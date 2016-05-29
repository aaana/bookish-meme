package server;

import channel.Manager;
import message.ChatContent;

import java.sql.*;
import java.util.*;

/**
 * Created by Administrator on 2016/5/11.
 */
public class DBOperate {

//    public static void main(String[] args) throws InterruptedException{
//        DBOperate dbOperate = new DBOperate();
//        List<ChatContent> chatContents = new ArrayList<ChatContent>();
////        try {
////            chatContents=dbOperate.getALLMessageByGid(1);
////            for(int i=0;i<chatContents.size();i++){
////                System.out.println(chatContents.get(i).getMessage());
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        try {
//            System.out.println(dbOperate.getGidByAcc(101));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
//
//    public  List<ChatContent> getALLMessageByGid(int gid) throws Exception {
//        Connection connection = threadLocal.get();
//
//        if (connection == null || connection.isClosed()) {
//            try {
//                Class.forName("org.sqlite.JDBC");
//                connection = DriverManager.getConnection("jdbc:sqlite:test.db");
//
//                threadLocal.set(connection);
//                String sql="select * from message where gid = ?";
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setInt(1,gid);
//                ResultSet resultSet = preparedStatement.executeQuery();
//
//                List<ChatContent> chatContents = new ArrayList<ChatContent>();
//                while(resultSet.next()){
//                    ChatContent chatContent = new ChatContent(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
//                    chatContents.add(chatContent);
//                }
//                return chatContents;
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        connection.close();
//        return null;
//    }
//
//    //保留第gid组的后number个messge，删除之前所有的；返回删除个数，-1表示失败
//    public int delete(int gid,int number) throws Exception {
//        Connection connection = threadLocal.get();
//        int result = 0;
//
//        if (connection == null || connection.isClosed()) {
//            try {
//                Class.forName("org.sqlite.JDBC");
//                connection = DriverManager.getConnection("jdbc:sqlite:test.db");
//
//                threadLocal.set(connection);
//
//                String sql = "select count() from message where gid = ?";
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setInt(1,gid);
//                ResultSet resultSet = preparedStatement.executeQuery();
//                int sum = 0;
//                if(resultSet.next()){
//                    sum = resultSet.getInt(1);
//                } else{
//                    return -1;
//                }
//
//                number = sum - number;
//                if(number > 0) {
//                    sql = "delete from message where rowid in(select rowid from message where gid = ? limit ?)";
//                    preparedStatement = connection.prepareStatement(sql);
//                    preparedStatement.setInt(1, gid);
//                    preparedStatement.setInt(2, number);
//                    result = preparedStatement.executeUpdate();
//                }
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        connection.close();
//        return result;
//    }
//
//    public int insert(ChatContent chatContent) throws Exception {
//        Connection connection = threadLocal.get();
//        int result = 0;
//
//        if (connection == null || connection.isClosed()) {
//            try {
//                Class.forName("org.sqlite.JDBC");
//                connection = DriverManager.getConnection("jdbc:sqlite:test.db");
//
//                threadLocal.set(connection);
//                String sql="insert into message values(?,?,?,?)";
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setInt(1,chatContent.getGroupId());
//                preparedStatement.setString(2,chatContent.getSender());
//                preparedStatement.setString(3, chatContent.getSendDate());
//                preparedStatement.setString(4, chatContent.getMessage());
//                result = preparedStatement.executeUpdate();
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        connection.close();
//        return result;
//    }
//
//    public  boolean getGidAndUid() throws Exception {
//        Connection connection = threadLocal.get();
//
//        if (connection == null || connection.isClosed()) {
//            try {
//                Class.forName("org.sqlite.JDBC");
//                connection = DriverManager.getConnection("jdbc:sqlite:test.db");
//
//                threadLocal.set(connection);
//                String sql="SELECT name,groupId from user order by groupid";
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(sql);
//
////                while(resultSet.next()){
////                    System.out.println(resultSet.getString(1));
////                    System.out.println(resultSet.getInt(2));
////                }
//                int gid = -1;
//                if(resultSet.next()) gid = resultSet.getInt(2);
//                else return false;
//                while(true){
//                    Map<String,Integer> t = new HashMap<String, Integer>();
//                    t.put(resultSet.getString(1),0);
//                    while(resultSet.next()&&resultSet.getInt(2)==gid){
//                        t.put(resultSet.getString(1),0);
//                    }
//                    if(!resultSet.isAfterLast()){
//                        Manager.groupClientsMissingNum.put(gid,t);
//                        gid = resultSet.getInt(2);
//                    }else{
//                        Manager.groupClientsMissingNum.put(gid,t);
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        connection.close();
//        return true;
//    }
//
//    //返回-1表示错误
//    public  int getGidByAcc(int account) throws Exception {
//        Connection connection = threadLocal.get();
//
//        if (connection == null || connection.isClosed()) {
//            try {
//                Class.forName("org.sqlite.JDBC");
//                connection = DriverManager.getConnection("jdbc:sqlite:test.db");
//
//                threadLocal.set(connection);
//                String sql="SELECT groupId from user where name = ?";
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setInt(1,account);
//                ResultSet resultSet = preparedStatement.executeQuery();
//
//                if(resultSet.next()) return resultSet.getInt(1);
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        connection.close();
//        return -1;
//    }
}
