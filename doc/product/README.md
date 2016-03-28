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

详见[Netty工作流](#Netty工作流)


### 模块

#### 日志模块

主要使用了Log4J 进行日志的记录, 其中客户端和服务器使用了不同的配置文件.

- 客户端 `config/log4j-client.property`
- 服务端 `config/log4j-server.property`

定时任务直接使用了 `java Timer` 来完成

#### 数据库模块

数据库部分使用比较轻便的**Sqlite**来进行了数据的持久化存储

#### 配置模块

使用了简单易懂的json来作为配置文件, 记录了服务端的IP和Port

#### 流水线模块

基于[Netty工作流](#Netty工作流), 我们需要构建一系列的管道来对接收到的数据来进行的一个流水线式的操作.


#### 架构图

![architecture](https://raw.githubusercontent.com/tztztztztz/bookish-meme/master/doc/architecture.png)


#### Netty工作流

![netty-workflow](https://raw.githubusercontent.com/tztztztztz/bookish-meme/master/doc/img/netty-workflow.png)


## 协议定义

## 数据流图

## 配置管理









