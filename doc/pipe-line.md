0, 1 type
content : obj

login
chatting message 

内容
如果是登陆类型
账号密码


如果是消息类型
就是一个消息string


验证模块

判断是不是登陆信息，如果不是，直接向下传递，不做处理
如果是，验证，如果失败， flag置为1， 如果成功，不做处理，往下传递


管道管理者

先看flag 

如果是登陆消息，把channel装入， 直接pass；

limiter

先看flag

logging
先看flag


相应模块
如果是登陆信息且flag == 1 返回登陆失败	
			   flag == 0 返回登陆成功

			   如果是聊天消息且flag == 1 返回发送失败 （发送过多）
			   			   flag  == 2 返回发送失败 （发送频繁）
						   			    flag == 0 返回发送成功 同时转发




									    客户端
									    登陆时候，发送登陆信息，如果收到 登陆失败，提示登陆失败，收到登陆成功，登陆成功，进入聊天界面

									    发送消息的时候，收到 0， 1， 2


