package com.monkey.netty.server;

import org.springframework.beans.factory.InitializingBean;

import com.monkey.netty.kryo.ServerJSONDecoder;
import com.monkey.netty.kryo.ServerJSONEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class NettyServer implements InitializingBean {
    private int port;
    private BusinessExecutor businessExecutor;

    public void bind() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // bossGroup就是parentGroup，是负责处理TCP/IP连接的
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // workerGroup就是childGroup,是负责处理Channel(通道)的I/O事件

        ServerBootstrap sb = new ServerBootstrap();
        sb.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 128) // 初始化服务端可连接队列,指定了队列的大小128
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持长连接
                .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作
                    @Override
                    protected void initChannel(SocketChannel sh) throws Exception {
                        sh.pipeline()
                        .addLast("decoder", new ServerJSONDecoder()) //解码response
                        .addLast("encoder", new ServerJSONEncoder()) //编码request
                        .addLast(new ServerHandler(businessExecutor)); // 使用ServerHandler类来处理接收到的消息
                    }
                });
        // 绑定监听端口，调用sync同步阻塞方法等待绑定操作完
        ChannelFuture future = sb.bind(port).sync();

        if (future.isSuccess()) {
            log.info("服务端启动成功: port: {}", port);
        } else {
            log.error("服务端启动失败: port: {}", port, future.cause());
            bossGroup.shutdownGracefully(); // 关闭线程组
            workerGroup.shutdownGracefully();
        }

        // 成功绑定到端口之后,给channel增加一个 管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程。
        future.channel().closeFuture().sync();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        bind();
    }
}