#功能扩展

##讨论基于组件的开发

##Server/Client保存所有收到的消息到文件
- 文件格式不限
- 文件路径可配置

解决：    

- 在原有的PM构件基础上，增加`writeFile(String filename,String content)`接口，实现向文件中写入收到的消息功能
-  Server中，日志全都由`LoggerHandler`这个handler来处理，所以只要在收到聊天消息的条件下，将该消息写入文件即可，将server端收到的消息报存在./messageRecords/server.txt中，格式如下：
`[yyyy-MM-dd HH:mm:ss] 用户帐号 消息内容`
```java
if( messageType == MessageType.CHATTING){
	receivedMessageNumber.record()
	//记录开始
	Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
    String snow = dateFormat.format(now);
    Log.writeFile("./messageRecords/server.txt","["+ snow+ "]" + " "+message.getChatContent().getAccount()+": "+message.getChatContent().toString());
    //记录结束
    if (messageStatus == MessageStatus.NEEDHANDLED) forwardMessageNumber.record();
            else ignoredMessageNumber.record();
        }

```
- Client中，日志全都由`ClientLoggerHandler`这个handler来处理，所以只要在确认消息类型为收到其他用户消息的条件下，将该消息记录下来即可。记录在messageRecords目录下以用户账户号为文件名的`.txt`文件中，格式如下:
`[yyyy-MM-dd HH:mm:ss] 用户帐号 消息内容`

```java
if(type == ACKType.OTHERSMESSAGE){
            receiveMsgNumber.record();
            //记录开始
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
            String snow = dateFormat.format(now);
            Log.writeFile("./messageRecords/"+account+".txt","["+ snow+ "]" + " " + ack.getChatContent().getAccount()+": " + ack.getChatContent().toString());
            //记录结束
        }
```
