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