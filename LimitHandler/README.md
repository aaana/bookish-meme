# LimitHandler

## Introduction 

一个适用Netty的，用于限制流量的handler

## Installation
`maven`

```xml
<dependency>
    <groupId>sse.tongji.bookish-meme</groupId>
    <artifactId>limit-handler</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

定义规则，即决定哪些是消息是需要这个handler去响应的，哪些是不需要这个handler响应的

```java 
        MessageFilter<AnyTypeObject> messageFilter = new MessageFilter<AnyTypeObject>() {
            //定义这个对象师傅应该响应
            @Override
            public Boolean shouldFilter(AnyTypeObject obj) {
                if(obj should be filte) return true;
                else return false;
            }
        };
```

定义需要使用到的limiter类型

```java
int maxCount = 5;
RtLimiter rateLimiter = new SumCountRtLimiter(maxCount);
```

构造limitHandler

```java
        RateLimitHandler<AnyTypeObject> rateLimitHandler = new RateLimitHandler<AnyTypeObject>(messageFilter, rateLimiter) {
            //当该类型的消息被响应且被许可时
            @Override
            public void messageAgree(AnyTypeObject msg) {
                //做你的业务逻辑
            }
			  //当该类型的消息被响应且不被许可时
            @Override
            public void messageDisagree(AnyTypeObject msg) {
                //做你的业务逻辑
            }
			  //当该类型的消息不被响应
            @Override
            public void messageIgnore(AnyTypeObject msg) {
                //做你的业务逻辑
            }
        };
```

加入到Netty的pipeline中

```java
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringEncoder())
                .addLast("limit",new rateLimitHandler());
    }

}
```





