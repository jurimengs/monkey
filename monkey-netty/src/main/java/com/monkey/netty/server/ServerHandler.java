package com.monkey.netty.server;

import java.util.UUID;

import com.monkey.exceptions.BusinessInvokeException;
import com.monkey.netty.client.BusinessRespCodeEnum;
import com.monkey.netty.client.RpcRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private BusinessExecutor businessExecutor;
    
    //接受client发送的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        long start = System.currentTimeMillis();
        RpcRequest request = (RpcRequest) msg;
//        log.info("接收到客户端信息:{}", request.toString());
        
        RpcResponse response = new RpcResponse();
        response.setId(UUID.randomUUID().toString());
        try {
            Object object = businessExecutor.execute(request);
            response.setData(object);
            response.setStatus(BusinessRespCodeEnum.OK.getCode());
        } catch (BusinessInvokeException e) {
            response.setStatus(BusinessRespCodeEnum.REMOTE_REQUEST_EXCEPTION.getCode());
            log.error("", e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
        long over = System.currentTimeMillis();
        log.info("本次服务端消耗时间: {}", (over - start));
        //返回的数据结构
        ctx.writeAndFlush(response);
    }
 
    //通知处理器最后的channelRead()是当前批处理中的最后一条消息时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("服务端接收数据完毕..");
    }
 
    //读操作时捕获到异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
 
    //客户端去和服务端连接成功时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush("hello client");
    }
}