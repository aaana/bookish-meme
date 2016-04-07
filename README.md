# bookish-meme


## Table of Contents
- [Introduction](#introduction)
- [Requirements](#requirements)
- [Framework](#framework)
- [Get Started](#get-started)
- [Dependences Installation](#dependences-installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [程序文档](https://github.com/tztztztztz/bookish-meme/tree/master/doc/product)
- [项目管理文档](https://github.com/tztztztztz/bookish-meme/tree/master/doc/management)
- [项目测试文档](https://github.com/tztztztztz/bookish-meme/tree/master/doc/test)
- [复用文档](https://github.com/tztztztztz/bookish-meme/tree/master/doc/reuse)
- [可复用组件](#可复用组件)
  - [RTLimiter](https://github.com/tztztztztz/bookish-meme/tree/master/rateLimter)
  - [LimitHandler](https://github.com/tztztztztz/bookish-meme/tree/master/LimitHandler)
  - [cm](https://github.com/tztztztztz/bookish-meme/tree/master/cm)

## Introduction

### 小组成员

| 学号 | 姓名 | Github |
| -----|:----:| ----:|
| 1352892    | 谭靖儒    |     |
| 1352875    | 黄安娜   | [aaana](https://github.com/aaana)    |
| 1352965    | 晁佳欢    | wlmxjm1    |
|     | 马致远    |     |
|     | 林昌盛    |     |


## Requirements

- IDE IntellJ IDEA
- **Jdk >= 1.8.0_45**
- maven

## Framework

  - [Netty](https://github.com/netty/netty)
    
## Get Started


## Dependences Installation
### maven
`pom.xml`
```xml
<dependencies>
    <dependency>
        <groupId>io.netty</groupId>
        <artifactId>netty-all</artifactId>
        <version>4.0.0.CR3</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.10</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>javassist</groupId>
        <artifactId>javassist</artifactId>
        <version>3.12.1.GA</version>
    </dependency>
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.2</version>
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.2.2</version>
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.2.2</version>
    </dependency>
    <dependency>
        <groupId>com.google.guava</groupId>
        <artifactId>guava</artifactId>
        <version>19.0</version>
    </dependency>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.7.2</version>
    </dependency>
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
</dependencies>
```

## Configuration
`config/conf.json`
```json
{
  "server":{
    "HOST":"localhost",
    "PORT":8080,
    "MaxMsgNumber":100,
    "MaxMsgNumberPerSec":5
  }

}
```

## Usage

1. 直接运行Server
2. 运行ClientGUI
3. Login
4. enjoy it!




