# 程序文档

## 主要内容

- [项目描述](#项目描述)
- [项目架构](#项目架构)
	- [基础框架](#基础框架)
	- [模块](#模块)
	- [架构图](#架构图)
- [协议定义](#协议定义)
- [数据流图](#数据流图)
- [配置管理](#配置管理)


## 项目描述

该项目是客户端与服务端的一些通信过程, `客户端`能够与`服务端`进行连接.

1. 客户端能够申请登陆服务器, 并且登陆成功之后能够发送信息.
2. 服务端能够客户端发送的信息做一定的处理, 并且能够对登陆成功的客户端的信息转发给所有的登陆的客户端
3. 客户端和服务端都能记录活动信息到文件中

## 项目架构

### 基础框架

项目直接使用了[Netty](https://github.com/netty/netty)这个基于socket的库来作为基础框架, 使用`事件驱动`以及`异步`和`事件机制`来构建整个项目

详见[Netty工作流](#workflow)


### 模块

#### json解析模块

在客户端和服务端都有负责将json字符串转换为对象的模块，主要使用了[Gson](https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html)来进行转换。

#### 流量控制模块

根据需求，对客户端发送消息的频率和总数都有限制。对于发送总数的限制，在每个客户端的这一模块中会有一个变量保存已收到的消息数目，在每次收到消息时都会判断是否超过限制。对于每秒发送消息数的限制，这里用到了Google [RateLimiter](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/util/concurrent/RateLimiter.html)，服务端每次收到某个客户端消息时都判断是否超过了频率限制。

#### 日志模块

主要使用了Log4J 进行日志的记录, 其中客户端和服务器使用了不同的配置文件.

- 客户端 `config/log4j-client.property`
- 服务端 `config/log4j-server.property`

定时任务直接使用了 `java Timer` 来完成

#### 数据库模块

数据库部分使用比较轻便的**Sqlite**来进行了数据的持久化存储

#### 配置模块

使用了简单易懂的json来作为配置文件, 记录了服务端的IP和Port，以及每次登陆所允许发送的消息最大次数maxMsgNumber和每秒允许发送的消息最大次数maxMsgNumberPerSec

#### 流水线模块

基于[Netty工作流](#workflow), 我们需要构建一系列的管道来对接收到的数据来进行的一个流水线式的操作.


#### 架构图

![architecture](https://raw.githubusercontent.com/tztztztztz/bookish-meme/master/doc/architecture.png)


<h4 id="workflow"> Netty工作流 </h4>

![netty-workflow](https://raw.githubusercontent.com/tztztztztz/bookish-meme/master/doc/img/netty-workflow.png)


## 协议定义

我们对客户端向服务端发送的消息和服务端回复客户端的消息预先进行了一些定义，具体如下：


客户端发送的消息有两种类型：
+ AUTHORITY（登录消息）
+ CHATTING（聊天消息）


服务端回复给客户端的消息有六种类型：
+ LOGINFAIL(登录失败)
+ LOGINSUCCESS(登录成功)
+ RELOGIN(需要重新登录)
+ TOOFREQUENT（消息发送过于频繁）
+ SENDSUCCESS（发送成功）
+ OTHERMESSAGE（收到其他客户端发送的消息）

这样客户端和服务端在收到消息时就可以根据消息类型做相应的处理。

## 数据流图
![dataflow1](https://raw.githubusercontent.com/tztztztztz/bookish-meme/master/doc/img/dataflow-1.png)
![dataflow2](https://raw.githubusercontent.com/tztztztztz/bookish-meme/master/doc/img/dataflow-2.png)
## 配置管理

使用 github repository 进行版本控制，各成员在各自的分支上编写代码，在完成某一功能模块后合并至主分支。







