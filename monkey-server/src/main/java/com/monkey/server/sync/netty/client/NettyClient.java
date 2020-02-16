package com.monkey.server.sync.netty.client;

import org.springframework.beans.factory.InitializingBean;

import com.monkey.server.sync.netty.kryo.ClientJSONDecoder;
import com.monkey.server.sync.netty.kryo.ClientJSONEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
 
@Slf4j
public class NettyClient implements InitializingBean {
 
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private String host;
    private int port;
    private int timeOut;
 
    //连接服务端的端口号地址和端口号
    public NettyClient(String host, int port) {
        this(host, port, 30); // timeOut 默认3秒
    }
    
    public NettyClient(String host, int port, int timeOut) {
        this.host = host;
        this.port = port;
        this.timeOut = timeOut;
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
    }

    
    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }
    
    public Channel getChannel() {
        try {
            Channel newChannel = createNewChannel();
            return newChannel;
        } catch (InterruptedException e) {
            log.error("创建新连接异常", e);
            throw new RuntimeException("", e);
        }
    }

    private void start() throws Exception {
        if(timeOut <= 0) {
            throw new RuntimeException("timeOut not null");
        }
        bootstrap.group(group).channel(NioSocketChannel.class)  // 使用NioSocketChannel来作为连接用的channel类
            .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                    .addLast("decoder", new ClientJSONDecoder()) //解码response
                    .addLast("encoder", new ClientJSONEncoder()) //编码request
                    .addLast(new ClientHandler(timeOut)); //客户端处理类
                }
            });
    }
    
    private Channel createNewChannel() throws InterruptedException {
        //发起异步连接请求，绑定连接端口和host信息
        ChannelFuture future = bootstrap.connect(host, port).sync();
        future.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        return future.channel();
    }
}