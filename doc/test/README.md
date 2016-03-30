# 测试文档

## 主要负责人

- 黄安娜 `单元测试`
- 晁佳欢 `单元测试`
- 马致远 `功能测试`


##测试内容

- [配置模块](#配置模块)
- [server/Json解析模块](#server/Json解析模块)
- [server/登陆验证模块](#server/登陆验证模块)
- [server/管道管理模块](#server/管道管理模块)
- [server/rateLimiter模块](#server/rateLimiter模块)
- [server/日志模块](#server/日志模块)
- [server/数据库访问模块](#server/数据库访问模块)
- [client/Json解析模块](#client/Json解析模块)
- [client/日志模块](#client/日志模块)
- [登录功能](#登录功能)
- [发送消息功能](#发送消息功能)
- [日志功能](#日志功能)

# 单元测试

### 配置模块

#### 测试用例
`ConfigReaderTest.java`


| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 读取配置文件 | "config/conf.json" |host=="localhost",port==8080,maxMsgNumber==100,maxMsgNumberPerSec==5 |host=="localhost",port==8080,maxMsgNumber==100,maxMsgNumberPerSec==5|通过|2016.3.27|无|

### server/Json解析模块
#### 测试用例

`JsonToObjectHandlerTest.java`

| 编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | Json解析为类型为chatting的message对象 | 类型为chatting的message序列化的Json字符串 | 类型为chatting的message对象 | 类型为chatting的message对象 | 通过 | 2016.3.27 | 无 |
| 1 | Json解析为类型为authority的message对象 | 类型为authority的message序列化的Json字符串 | 类型为authority的message对象 | 类型为authority的message对象 | 通过 | 2016.3.27 | 无 |

### server/登陆验证模块
#### 测试用例

`AuthorityHandlerTest.java`

| 编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 正确的登陆消息验证 |正确的登陆message（{LoginContent("100","123456"),MessageStatus.NEEDHANDLED,MessageType.AUTHORITY}） | message的Status为NEEDHANDLED （{LoginContent("100","123456"),MessageStatus.NEEDHANDLED,MessageType.AUTHORITY}） | Message的Status为NEEDHANDLED （{LoginContent("100","123456"),MessageStatus.NEEDHANDLED,MessageType.AUTHORITY}） | 通过| 2016.3.27 | 无|
| 2 | 错误的登陆消息验证 | 错误的登陆message （{LoginContent("123","123456"),MessageStatus.NEEDHANDLED,MessageType.AUTHORITY}）| message的Status为LOGINFAIL（{LoginContent("123","123456"),MessageStatus.LOGINFAIL,MessageType.AUTHORITY}） | message的Status为LOGINFAIL（{LoginContent("123","123456"),MessageStatus.LOGINFAIL,MessageType.AUTHORITY}） |通过 | 2016.3.27 | 无 |
| 3 | 聊天消息验证 | 聊天message（{ChatContent("hello"),MessageStatus.NEEDHANDLED,MessageType.CHATTING}） | 未发生改变的message   （{ChatContent("hello"),MessageStatus.NEEDHANDLED,MessageType.CHATTING}） | 未发生改变的message （{ChatContent("hello"),MessageStatus.NEEDHANDLED,MessageType.CHATTING}）| 通过 | 2016.3.27 |无|


### server/管道管理模块
#### 测试用例

`ChannelManagerHandlerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 登陆成功则添加channel |验证成功的message （{LoginContent("100","123456"),MessageStatus.NEEDHANDLED,MessageType.AUTHORITY}）| Manager.channels的长度+1 | Manager.channels的长度+1  | 通过| 2016.3.27 |无
| 2 | 登陆失败channel个数不变 | 验证失败的message （{LoginContent("123","123456"),MessageStatus.LOGINFAIL,MessageType.AUTHORITY}）| Manager.channels的长度不变 | Manager.channels的长度不变 |通过 | 2016.3.27|无
| 3 | 聊天消息 | 聊天message（{ChatContent("hello"),MessageStatus.NEEDHANDLED,MessageType.CHATTING}） | Manager.channels的长度不变 | Manager.channels的长度不变 | 通过 | 2016.3.27 |无


### rateLimiter模块
#### 测试用例

`LimiterHandlerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 2 | 正常发送 | 不超速的发送message| 信息状态变为NEEDHANDLED（不变） | 信息状态变为NEEDHANDLED（不变） |通过 | 2016.3.28|无
| 1 | 发送过频繁 |超过指定速率发送message| 信息状态变为TOOFREQUENT | 信息状态变为TOOFREQUENT  | 通过| 2016.3.28 |无
| 2 | 发送过多 | 超过指定额度发送message| 信息状态变为OVERRANGE | 信息状态变为OVERRANGE |通过 | 2016.3.28|无

### server/日志模块
#### 测试用例

`LoggerHandlerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 登录失败 |验证失败的message | LoggerHandler的invalidLoginNumber+1，其余不变 | LoggerHandler的invalidLoginNumber+1，其余不变  | 通过| 2016.3.28 |无
| 2 | 登陆成功| 验证成功的message | LoggerHandler的validLoginNumber+1，其余不变 | LoggerHandler的validLoginNumber+1，其余不变 |通过 | 2016.3.28|无
| 3 | 聊天消息（正常） | 聊天message | LoggerHandler的receivedMessageNumber+1，forwardMessageNumber+1，其余不变 | LoggerHandler的receivedMessageNumber+1，forwardMessageNumber+1，其余不变 | 通过 | 2016.3.28 |无
| 4 | 聊天消息（发送过多） | 聊天message | LoggerHandler的receivedMessageNumber+1，ignoredMessageNumber+1，其余不变 | LoggerHandler的receivedMessageNumber+1，ignoredMessageNumber+1，其余不变 | 通过 | 2016.3.28 |无
| 5 | 聊天消息（发送过频繁） | 聊天message | LoggerHandler的receivedMessageNumber+1，ignoredMessageNumber+1，其余不变 | LoggerHandler的receivedMessageNumber+1，ignoredMessageNumber+1，其余不变 | 通过 | 2016.3.28 |无





### server/数据库访问模块
#### 测试用例

`LoginServerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 成功登录 |正确的用户名和密码| 返回true | 返回true  | 通过| 2016.3.27 |无
| 2 | 登陆失败 | 带有字符'的用户名| 返回false | 返回false |通过 | 2016.3.28|无
| 3 | 登陆失败 | 空用户名 | 返回false | 返回false | 通过 | 2016.3.27 |无
| 3 | 登陆失败 | 用户名过长，密码为空 | 返回false | 返回false | 通过 | 2016.3.27 |无


### client/Json解析模块
#### 测试用例

`JsonHandlerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
|1|Json解析为类型为SENDSUCCESS的ACK|类型为SENDSUCCESS的ACK序列化的Json字符串|类型为SENDSUCCESS的ACK|类型为SENDSUCCESS的ACK|通过|2016.3.29|无|
|2|Json解析为类型为OTHERSMESSAGE的ACK|类型为OTHERSMESSAGE的ACK序列化的Json字符串|类型为OTHERSMESSAGE的ACK|类型为OTHERSMESSAGE的ACK|通过|2016.3.29|无|
|3|Json解析为类型为TOOFREQUENT的ACK|类型为TOOFREQUENT的ACK序列化的Json字符串|类型为TOOFREQUENT的ACK|类型为TOOFREQUENT的ACK|通过|2016.3.29|无|
|4|Json解析为类型为REDOLOGIN的ACK|类型为REDOLOGIN的ACK序列化的Json字符串|类型为REDOLOGIN的ACK|类型为REDOLOGIN的ACK|通过|2016.3.29|无|
|5|Json解析为类型为LOGINFAIL的ACK|类型为LOGINFAIL的ACK序列化的Json字符串|类型为LOGINFAIL的ACK|类型为LOGINFAIL的ACK|通过|2016.3.29|无|


### client/日志模块
#### 测试用例

ClientLoggerHandlerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
|1|登录失败|类型为LOGINFAIL的ACK|loginFail+1，其余不变|loginFail+1，其余不变|通过|2016.3.29|无|
|2|登陆成功|类型为LOGINSUCCESS的ACK|loginSuccess+1，其余不变|loginSuccess+1，其余不变|通过|2016.3.29|无|
|3|接收消息成功|类型为OTHERSMESSAGE的ACK|receiveMsgNumber+1，其余不变|receiveMsgNumber+1，其余不变|通过|2016.3.29|无|
|4|成功发送消息|类型为SENDSUCCESS的ACK|无改变|无改变|通过|2016.3.29|无|
|5|发送消息过频繁|类型为TOOFRENQUENT的ACK|无改变|无改变|通过|2016.3.29|无|
|6|发送消息过多|类型为REDOLOGIN的ACK|无改变|无改变|通过|2016.3.29|无|

# 功能测试
### 登录功能

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
|1|只输入用户名|用户名|登录失败|弹窗提示登录失败|通过|3.29|无
|2|只输入密码|密码|登录失败|弹窗提示登录失败|通过|3.29|无
|3|不输入任何|无|登录失败|弹窗提示登录失败|通过|3.29|无
|4|输入错误的用户名/密码组合|用户名，密码|登录失败|弹窗提示登录失败|通过|3.29|无
|5|输入正确的用户名/密码组合|用户名，密码|登录成功|登录成功并跳转到聊天界面|通过|3.29|无

### 发送消息功能
	为方便测试，将客户端发送消息上限改为5条，频率上限改为2条/s

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
|1|多个客户端之间相互发送消息|每个客户端各自发送一条消息|剩余客户端收到其他客户端发送的消息|同预期结果|通过|3.29|无
|2|客户端发送消息数目超过上限|客户端发送五条消息|在发送第五条消息时提示发送超过上限并跳转至登录界面,其他客户端无法收到第五条消息|同预期结果|通过|3.29|无
|3|客户端发送消息超过频率上限|以很快的速率发送消息|弹窗提示发送消息过快,其他客户端无法收到提示过快的消息|同预期结果|通过|3.29|无

### 日志功能
	为方便测试，将客户端发送消息上限改为5条，频率上限改为2条/s


| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
|1|登录失败，查看客户端服务端日志|错误的账号密码|客户端日志的Loginfailnumber+1且服务端日志的invalidLoginNumber +1|与预期一致|通过|3.30|通过
|2|登录成功，查看客户端服务端日志|正确的账号密码|客户端日志的Login successful number+1且服务端validLoginNumber+1|与预期一致|通过|3.30|无
|3|客户端发送一条消息，查看日志|消息|客户端的sent message number+1且服务端的forwardMessageNumber+1|同预期|通过|3.30|无
|4|客户端发送五条消息，查看日志|五条消息|客户端sent message number+5且服务端的receivedMessageNumber+5，forwardMessageNumber +4，ignoredMessageNumber不变。|同预期|通过|3.30|无
|5|客户端快速发送消息|消息|服务端ignoredMessageNumber+一定数目|同预期|通过|3.30|无
|6|客户端收到消息|其他客户端发送一条消息|客户端receviedMessageNumber+1|同预期结果|通过|3.30|无