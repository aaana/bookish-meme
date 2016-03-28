# 测试文档

## 主要负责人

- 黄安娜 `单元测试`
- 晁佳欢 `单元测试`
- 马致远 `功能测试`


##测试内容

- [配置模块](#配置模块)
- [登陆验证模块](#登陆验证模块)
- [管道管理模块](#管道管理模块)
- [rateLimiter模块]
- [日志模块]
- [数据库访问模块]

### 配置模块

#### 测试用例
`ConfigReaderTest.java`

>- 用例描述:	读取配置文件	
>- 输入数据:	"config/conf.json"
>- 预期结果:   	host=="localhost",port==8080,maxMsgNumber==100,maxMsgNumberPerSec==5
>- 实际结果:	host=="localhost",port==8080,maxMsgNumber==100,maxMsgNumberPerSec==5
>- 测试结果:	通过
>- 测试时间:	2016.3.27
>- bug描述	无   


### 登陆验证模块
#### 测试用例

`AuthorityHandlerTest.java`

| 编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 |
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 正确的登陆消息验证 |正确的登陆message | message的Status为NEEDHANDLED | Message的Status为NEEDHANDLED  | 通过| 2016.3.27 |
| 2 | 错误的登陆消息验证 | 错误的登陆message | message的Status为LOGINFAIL | message的Status为LOGINFAIL |通过 | 2016.3.27 |
| 3 | 聊天消息验证 | 聊天message | 未发生改变的message | 未发生改变的message | 通过 | 2016.3.27 |


### 管道管理模块
#### 测试用例

`ChannelManagerHandlerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 登陆成功则添加channel |验证成功的message | channels的长度+1 | channels的长度+1  | 通过| 2016.3.27 |无
| 2 | 登陆失败channel个数不变 | 验证失败的message | channels的长度不变 | channels的长度不变 |通过 | 2016.3.27|无
| 3 | 聊天消息 | 聊天message | channels的长度不变 | channels的长度不变 | 通过 | 2016.3.27 |无


### 日志模块
#### 测试用例

`LoggerHandlerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 登录失败 |验证失败的message | LoggerHandler的invalidLoginNumber+1，其余不变 | LoggerHandler的invalidLoginNumber+1，其余不变  | 通过| 2016.3.28 |无
| 2 | 登陆成功| 验证成功的message | LoggerHandler的validLoginNumber+1，其余不变 | LoggerHandler的validLoginNumber+1，其余不变 |通过 | 2016.3.28|无
| 3 | 聊天消息（正常） | 聊天message | LoggerHandler的receivedMessageNumber+1，forwardMessageNumber+1，其余不变 | LoggerHandler的receivedMessageNumber+1，forwardMessageNumber+1，其余不变 | 通过 | 2016.3.28 |无
| 4 | 聊天消息（发送过多） | 聊天message | LoggerHandler的receivedMessageNumber+1，ignoredMessageNumber+1，其余不变 | LoggerHandler的receivedMessageNumber+1，ignoredMessageNumber+1，其余不变 | 通过 | 2016.3.28 |无
| 5 | 聊天消息（发送过频繁） | 聊天message | LoggerHandler的receivedMessageNumber+1，ignoredMessageNumber+1，其余不变 | LoggerHandler的receivedMessageNumber+1，ignoredMessageNumber+1，其余不变 | 通过 | 2016.3.28 |无


### rateLimiter模块
#### 测试用例

`LimiterHandlerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 2 | 正常发送 | 不超速的发送message| 信息状态变为NEEDHANDLED（不变） | 信息状态变为NEEDHANDLED（不变） |通过 | 2016.3.28|无
| 1 | 发送过频繁 |超过指定速率发送message| 信息状态变为TOOFREQUENT | 信息状态变为TOOFREQUENT  | 通过| 2016.3.28 |无
| 2 | 发送过多 | 超过指定额度发送message| 信息状态变为OVERRANGE | 信息状态变为OVERRANGE |通过 | 2016.3.28|无


### 数据库访问模块
#### 测试用例

`LoginServerTest.java`

| 用例编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 | bug描述
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 成功登录 |正确的用户名和密码| 返回true | 返回true  | 通过| 2016.3.27 |无
| 2 | 登陆失败 | 带有字符'的用户名| 返回false | 返回false |通过 | 2016.3.28|无
| 3 | 登陆失败 | 空用户名 | 返回false | 返回false | 通过 | 2016.3.27 |无
| 3 | 登陆失败 | 用户名过长，密码为空 | 返回false | 返回false | 通过 | 2016.3.27 |无
