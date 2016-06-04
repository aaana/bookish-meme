package protocol;

/**
 * Created by tanjingru on 3/23/16.
 */

public enum ACKType {

    LOGINFAIL, // 登陆失败

    LOGINSUCCESS, // 登陆成功

    REDOLOGIN, // 需要重新登陆

    TOOFRENQUENT, // 消息发送太过频繁

    SENDSUCCESS, // 发送成功

    OTHERSMESSAGE, // 收到的别人的消息

    SOMEONEONLINE ,//同组成员上线

    SOMEONEOFFLINE, //同组成员下线

    ADDSUCCESS,

    ADDFAIL,    //加租失败

    SOMEONEADDGROUP,

    REGISTERSUCCESS,

    REGISTERFAIL,

    ACCOUNTEXIST, //创建小组失败

    ENTERGROUP,

    CREATEGROUPSUCCESS,

    GROUPALREADYEXIST,

    ENTERGROUPFAIL
}
