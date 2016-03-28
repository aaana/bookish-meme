# 测试文档

## 主要负责人

- 黄安娜 `单元测试`
- 晁佳欢 `单元测试`
- 马致远 `集成测试`


##测试内容

- [配置模块](#配置模块)
- [登陆验证模块](#登陆验证模块)

### 配置模块

#### 测试用例
`ConfigReaderTest.java`

>- 用例描述:	读取配置文件	
>- 输入数据:	"config/conf.json"
>- 预期结果:   	host=="localhost",port==8080,maxMsgNumber==100,maxMsgNumberPerSec==5
>- 实际结果:	host=="localhost",port==8080,maxMsgNumber==100,maxMsgNumberPerSec==5
>- 测试结果:	通过
>- 测试时间:	2016.3.27
>- bug描述	无   


### 登陆验证模块
#### 测试用例

`AuthorityHandlerTest.java`

| 编号 | 用例描述 | 输入数据 | 预期结果 | 实际结果 |  测试结果 | 测试时间 |
|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
| 1 | 正确的登陆消息验证 |正确的登陆message | message的Status为NEEDHANDLED | Message的Status为NEEDHANDLED  | 通过| 2016.3.27 |
| 2 | 错误的登陆消息验证 | 错误的登陆message | message的Status为LOGINFAIL | message的Status为LOGINFAIL |通过 | 2016.3.27 |
| 3 | 聊天消息验证 | 聊天message | 未发生改变的message | 未发生改变的message | 通过 | 2016.3.27 |



