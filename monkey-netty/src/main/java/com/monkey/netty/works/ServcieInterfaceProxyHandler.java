package com.monkey.netty.works;

import java.lang.reflect.Method;
import java.util.UUID;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.cglib.proxy.InvocationHandler;

import com.monkey.netty.client.ClientHandler;
import com.monkey.netty.client.NettyClient;
import com.monkey.netty.client.RpcRequest;
import com.monkey.netty.server.RpcResponse;
import com.monkey.util.StringUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考
 * 
 * @author A37
 *
 */
@Slf4j
@Setter
public class ServcieInterfaceProxyHandler implements InvocationHandler {
    private BeanFactory beanFactory;
    private Class<?> interfaceClass;

    public ServcieInterfaceProxyHandler() {
    }

    public ServcieInterfaceProxyHandler(BeanFactory beanFactory, Class<?> interfaceClass) {
        this.beanFactory = beanFactory;
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 因为是 NettyTestService 接口方法的实现，所以这里直接做 netty 请求的实现就可以了
        return invokeByNettyClient(method, args);
//        return invokeByNettyPool(method, args);
    }

    private Object invokeByNettyClient(Method method, Object[] args) {
        long start = System.currentTimeMillis();
        NettyClient client = (NettyClient) beanFactory.getBean("nettyClient");

        Channel channel = client.getChannel();
        // 消息体
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setArgs(args);
        request.setBeanClass(interfaceClass);
        request.setMethod(method.getName());
        request.setBeanName(StringUtil.getBeanNameOfClass(interfaceClass.getName()));
        request.setParameterTypes(method.getParameterTypes());

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
