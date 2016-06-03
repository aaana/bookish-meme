package message;

/**
 * Created by tanjingru on 3/23/16.
 */
public enum MessageStatus {

    NEEDHANDLED, // 还需要被处理

    LOGINFAIL, // 登陆失败
    
    OVERRANGE,  // 发送超过最大数量

    TOOFREQUENT, // 发送过于频繁

    ACCOUNTEXSIT,

    REGISTERFAIL

}
