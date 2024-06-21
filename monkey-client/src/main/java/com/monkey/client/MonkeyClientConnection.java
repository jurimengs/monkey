package com.monkey.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monkey.base.DataModelIn;
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
public class MonkeyClientConnection {

    @Autowired
    private ObjectParser objectParser;
    
    @Autowired
    private NettyClient nettyClient;
    
    public void update(Object object) {
        DataModelIn parseObject = objectParser.parseObject(object);
        
        execute(parseObject, "update", new Class<?>[] {
            DataModelIn.class
        } );
    }

    
    public void save(Object object) {
        DataModelIn parseObject = objectParser.parseObject(object);
        execute(parseObject, "save", new Class<?>[] {
            DataModelIn.class
        } );
    }
    public void saveList(List<?> objects) {
        objects.stream().forEach(object -> {
            save(object);
        });
    }

    
    public void delete(Object object) {
        DataModelIn parseObject = objectParser.parseObject(object);
        execute(parseObject, "delete", new Class<?>[] {
            DataModelIn.class
        } );
    }

    
    public Object select(Object object) {
        DataModelIn parseObject = objectParser.parseObject(object);
        return execute(parseObject, "select", new Class<?>[] {
            DataModelIn.class
        } );
    }

    @SuppressWarnings("unchecked")
    public List<Object> selectList(DataModelIn object) {
        Object execute = execute(object, "select", new Class<?>[] {
            DataModelIn.class
        } );
        if(execute != null) {
            return (List<Object>) execute;
        }
        return new ArrayList<>();
    }

    private Object execute(DataModelIn dataModelIn, String methodName, Class<?>[] parameterTypes) {

        long start = System.currentTimeMillis();
        Channel channel = nettyClient.getChannel();
        // 消息体
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setArgs(new Object[] {dataModelIn});
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
