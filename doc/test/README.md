# 测试文档

## 主要负责人

- 黄安娜 `单元测试`
- 晁佳欢 `单元测试`
- 马致远 `集成测试`


## 测试内容

————
**以下为模板，请勿直接修改**

- [某某模块/handler1](#某某模块/handler2)
- [某某模块/handler1](#某某模块/handler2)

### 某某模块/handler1
#### 测试用例 
	直接使用文字描述即可，不需要贴出代码

- xxx
- xxx
- xxx

### 某某模块/handler2
#### 测试用例 

- xxx
- xxx
- xxx
- 

##测试内容

- [配置模块/ConfigReader](#配置模块/ConfigReader)
- [登陆验证模块/AuthorityHandler](#登陆验证模块/AuthorityHandler)

### 配置模块/ConfigReader
#### 测试用例
ConfigReaderTest.java
>用例描述:	读取配置文件	
>输入数据:	"config/conf.json"
>预期结果:   	host=="localhost",port==8080,maxMsgNumber==100,maxMsgNumberPerSec==5
>实际结果:	host=="localhost",port==8080,maxMsgNumber==100,maxMsgNumberPerSec==5
>测试结果:	通过
>测试时间:	2016.3.27
>bug描述	无   


###登陆验证模块/AuthorityHandler
####测试用例
AuthorityHandlerTest.java
>编号 		1
>用例描述:	正确的登陆消息验证
>输入数据:	正确的登陆message
>预期结果	message的Status为NEEDHANDLED
>实际结果	Message的Status为NEEDHANDLED
>测试结果	通过
>测试时间       2016.3.27
***
-－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

-1   正确的登陆消息验证	  正确的登陆message 	  message的Status为NEEDHANDLED	  Message的Status为NEEDHANDLED    		2016.3.27
-2   错误的登陆消息验证	  错误的登陆message	  message的Status为LOGINFAIL	  Message的Status为LOGINFAIL	  通过 		2016.3.27
-3   聊天消息验证	  聊天message		  未发生改变的message		  未发生改变的message		  通过		2016.3.27
