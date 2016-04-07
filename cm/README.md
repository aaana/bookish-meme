# CM

## Introduction

一个适用于json配置的配置管理构件

- 支持动态加载配置文件
- 可变的配置文件位置
- 可直接配置成Java对象
- 可以根据键读值

## Installation
`maven`

```xml
<dependency>
    <groupId>sse.tongji.bookish-meme</groupId>
    <artifactId>cm</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage
- First, new a Config object,
```java
Config config = new Config();
```
- Second, load json configuration file,
```java
config.readFile("config/conf.json");
```
- Third, invoke relevant API.
```java
//get nested configuration
Config getConf(String key);

//get String value of key
String getString(String key);

//get String value of key
int getInt(String key);

//directly convert to a Java bean
<T> T toObj(Class<T> t);
```

## API Example
#### config/conf.json
```json
{
  "server":{
    "host":"localhost",
    "port":8080,
    "MaxMsgNumber":100,
    "MaxMsgNumberPerSec":5
  }
}
```
#### Configuration class
```java
public class Configuration {
    private String host;
    private int port;
    private int maxMsgNumber;
    private int maxMsgNumberPerSec;
    //setters and getters
    ...
```
#### Kick off!!!
```java
import sse.tongji.bookish-meme.cm

Config config = new Config();
config.readFile("config/conf.json");
//convert to Confihuration class
Configuration configuration = config.getConf("server").toObj(Configuration.class);
//get String value-"host"
String host = config.getConf("server").getString("host");
...其他API调用类似
```