package message;

/**
 * Created by tanjingru on 3/23/16.
 */
public enum MessageStatus {

    NEEDHANDLED, // 还需要被处理

    LOGINFAIL, // 登陆失败
    
    OVERRANGE,  // 发送超过最大数量

    TOOFREQUENT, // 发送过于频繁

    ACCOUNTEXSIT, //注册时账号已存在

    REGISTERFAIL,  //其它原因注册失败

    ALREADYINTHEGROUP,  //加入小组时已经在该小组中

    GROUPALREADYEXIST,  //创建小组时小组已存在

    ENTERGROUPFAIL      //进入小组失败

}
