# PM
## Introduction
性能管理构件，接收性能参数，并自动生成报告。

## Installation
`maven`

```xml
		<dependency>
            <groupId>sse.tongji.bookish-meme</groupId>
            <artifactId>pm</artifactId>
            <version>1.0.0</version>
        </dependency>
```

## Usage

#### Initialization
```java
Log log = new Log();

```

#### Customize
设置多长时间后开始写日志
```java
log.setDelay(60000);
```
设置日志间隔
```java
log.setInterval(60000);
```
####Set Parameter
设置性能参数
```java
log.setParam("key",value)
```
####Run

```java
log.run();
```
####Stop
```java
log.stop();
```
#New Features in 1.0.1
##Static Method
写文件操作
```java
Log.writeFile("path","content")
```
文件压缩
```java
Log.compress("destFilePath")
```
说明：该方法会将所有调用 Log.writeFile（） 方法生成的文件和性能日志进行压缩。
使用Log.resetCompress()方法进行重置。（每次使用Log.compress()都会压缩重置后生成的文件）
#New Features in 1.0.2
设置性能日志文件目录（默认保存在当前目录的 log 文件夹下）
```java
log.setPMDir("..\\hello\\how\\");(windows)
log.setPMDir("../hello/how/");(unix)
```
注意 : 请以路径分隔符结束。
