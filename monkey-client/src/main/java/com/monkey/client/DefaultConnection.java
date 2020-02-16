package com.monkey.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monkey.constants.CommonConstants;
import com.monkey.netty.client.ClientHandler;
import com.monkey.netty.client.NettyClient;
import com.monkey.netty.client.RpcRequest;
import com.monkey.netty.server.RpcResponse;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DefaultConnection implements Connection {
    @Autowired
    private NettyClient nettyClient;

    @Override
    public void update(Object object) {
        execute(object, "update", new Class<?>[] {
            Object.class
        } );
    }

    @Override
    public void save(Object object) {
        execute(object, "save", new Class<?>[] {
            Object.class
        } );
    }

    @Override
    public void delete(Object object) {
        execute(object, "delete", new Class<?>[] {
            Object.class
        } );
    }

    @Override
    public Object select(Object object) {
        return execute(object, "select", new Class<?>[] {
            Object.class
        } );
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> selectList(Object object) {
        Object execute = execute(object, "select", new Class<?>[] {
            Object.class
        } );
        if(execute != null) {
            return (List<Object>) execute;
        }
        return new ArrayList<>();
    }

    private Object execute(Object param, String methodName, Class<?>[] parameterTypes) {

        long start = System.currentTimeMillis();
        Channel channel = nettyClient.getChannel();
        // 消息体
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setArgs(new Object[] {param});
        request.setBeanClass(Connection.class);
        request.setMethod(methodName);
        request.setBeanName(CommonConstants.BEAN_NAME_SERVER_CONNECTION);
        request.setParameterTypes(parameterTypes);

        ChannelFuture future = channel.writeAndFlush(request);
        ClientHandler clientHandler = future.channel().pipeline().get(ClientHandler.class);
        RpcResponse rpcResponse = clientHandler.getRpcResponse();

        future.addListener(thisFuture -> {
            if (!thisFuture.isSuccess()) {
                log.error("unexpected send fail:{}", thisFuture.cause().getMessage());
            }
        });
        log.info("rpcResponse:{}", rpcResponse);

        long over = System.currentTimeMillis();
        log.info("接受请求到返回消耗时间: {}", (over - start));
        return rpcResponse.getData();
    }
}
