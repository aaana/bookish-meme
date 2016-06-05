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

    ADDSUCCESS, //加组成功

    ADDFAIL,    //加组失败

    SOMEONEADDGROUP,    //有人加组

    REGISTERSUCCESS,    //注册成功

    REGISTERFAIL,   //注册失败

    ACCOUNTEXIST, //创建小组失败

    ENTERGROUP, //进入小组成功

    CREATEGROUPSUCCESS, //创建小组成功

    GROUPALREADYEXIST,  //创建小组失败

    ENTERGROUPFAIL  //进入小组失败
}
