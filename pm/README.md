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
注意 : 请以路径分隔符结束。
```java
log.setPMDir("..\\hello\\how\\");(windows)
log.setPMDir("../hello/how/");(unix)
```
定时完成压缩任务
```java
Log.setCompressPath("message_records")
//每天凌晨压缩一次
Log.startCompressSchedule()
//可以随时结束压缩计划
Log.endCompressSchedule()
```
请务必自己每天设置一次路径，否则无法使用每周归档的功能。
#New Features in 1.0.3
每周对聊天记录和性能日志进行归档

```java
//设置目标压缩文件路径
Log.setReCompressPath("total.zip")
//开始压缩任务
Log.startReCompressSchedule();
//停止压缩任务
Log.stopReCompressSchedule();
```
请务必每周自己设置一次目标文件路径，否则会因为重名而失败。

#New Features in 1.0.4
写文件并对单个文件和总文件的大小进行限制。

首先，在congfig目录下创建config.json文件，对输出目录，单个文件大小和文件夹大小进行配置。
```json
{
  "outputFolder":"log/",
  "singleFileSize":100,//kB
  "totalFileSize":1024 //MB
}
```
然后使用以下方法写文件内容，文件将在你配置的目录下自动生成。当单个文件大小超过限制时将自动生成新文件，当文件夹大小超过限制时，将在控制台提示你清理文件。
```java
Log.writeFile("content")
```
特别提示：本功能依赖于本组的cm构件。