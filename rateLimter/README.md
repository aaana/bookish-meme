# RTLimiter

## Introduction 

一个方便对消息进行流量控制的组件

## Installation
`mavem`

```xml
<dependency>
    <groupId>sse.tongji.bookish-meme</groupId>
    <artifactId>RtLimter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

这里有两种类型的RtLimiter
- PerSecondCountRtLimiter
- SumCountRtLimiter


### SumCountRtLimiter

#### Initialization
```java
RtLimiter rtLimiter = new SumCountRtLimiter(5);
rtLimiter.tryAcquire()
```

#### reset
```java
rtLimiter.reset();
```



## 类图

![class-diagram](https://raw.githubusercontent.com/tztztztztz/bookish-meme/master/rateLimter/doc/class-diagram.png)
