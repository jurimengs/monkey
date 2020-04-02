# monkey

#### 介绍

对于一些数据写入频繁的系统 ， 如果能解决其数据存储的压力， 应当考虑将数据频繁的运算操作，放在内存中，并定时将计算结果持久化到数据库中
即以降低数据库写入压力为目标
monkey，基于 springboot2.x 开发的内存数据库，意在减少对数据库频繁的操作
可以定制持久化实现， 默认提供 mysql 的持久化方式，当前持久策略只有持久化频率，默认是1秒触发一次持久化
client-server通信技术桥梁是 netty
当前正在规划高可用的支持， 以确保内存数据的相对安全
使用内存数据库的好处在于，绕开数据库的瓶颈，过滤掉频繁的写操作，




#### 软件架构
软件架构说明


#### 安装教程

1.  配置好 server 的数据库连接，当前支持 mysql
2.  xxxx
3.  xxxx

#### 使用说明

1.  client 需要依赖到项目中
2.  server 可以直接以 springboot 的方式启动
3.  client-demo 是模拟的示例项目，意在演示数据流如何从客户端进入 monkey

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


####
https://gitee.com/jurimengs/monkey
https://github.com/jurimengs/monkey
