# TZLicense

## Introduction 

一个方便对消息进行流量控制的组件

## Installation
`maven`

```xml
<dependency>
    <groupId>sse.tongji.bookish-meme</groupId>
    <artifactId>tzlicense</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Usage

这里有两种类型的TZLicense
- PerSecondCountLicense
- SumCountLicense


### PerSecondCountLicense

#### Initialization
```java
TZLicense tzLicense = new PerSecondCountLicense(100);
tzLicense.tryAcquire()
```

#### reset
```java
tzLicense.reset();
```

### PerSecondCountRtLimiter

```java
TZLicense tzLicense = new PerSecondCountLicense(5);
tzLicense.tryAcquire()
```

#### reset
```java
tzLicense.reset();
```

## 类图

![class-diagram](https://raw.githubusercontent.com/tztztztztz/bookish-meme/master/rateLimter/doc/class-diagram.png)
