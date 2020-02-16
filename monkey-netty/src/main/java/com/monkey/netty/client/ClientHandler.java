package com.monkey.netty.client;

import java.util.concurrent.TimeUnit;

import com.monkey.base.JsonResult;
import com.monkey.netty.enums.BusinessRespCodeEnum;
import com.monkey.netty.server.RpcResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse>{
    private int timeOut; // TODO 这玩意弄成配置的
    private RpcResponse response;
    private ChannelPromise channelPromise; // 用于做异步转同步，通知线程用的
    
    public ClientHandler(int timeOut) {
        this.timeOut = timeOut;
    }

    //处理服务端返回的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        log.debug("接受到server响应数据: {}", response.toString());
        this.response = response;
        channelPromise.setSuccess(); // 唤醒所有等待当前任务的线程
        ctx.close(); // 关闭连接 TODO 考虑做一个池
    }
 
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        channelPromise = ctx.newPromise();
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
 
    public RpcResponse getRpcResponse() {
        try {
            // 由于 channelRead0 与业务请求主线程是异步的， 因此这里利用 channelPromise 做一个等待，并设置等待超时时间
            log.debug("尝试等待数据接收完成....");
            channelPromise.await(timeOut, TimeUnit.SECONDS);
            // 等待到期了，还没收到数据
            if(response == null) {
                log.error("请求超时");
                response = new RpcResponse();
                response.setStatus(BusinessRespCodeEnum.REMOTE_REQUEST_TIMEOUT.getCode());
            }
            return response;
        } catch (InterruptedException e) {
            log.error("尝试等待异常", e);
            JsonResult<String> result = new JsonResult<>();
            result.setCode(BusinessRespCodeEnum.REMOTE_REQUEST_WAITING_ERROR.getCode());
            result.setMsg(BusinessRespCodeEnum.REMOTE_REQUEST_WAITING_ERROR.getMessage());
            
            response = new RpcResponse();
            response.setStatus(BusinessRespCodeEnum.REMOTE_REQUEST_WAITING_ERROR.getCode());
            return response;
        } catch (Exception e) {
            log.error("尝试等待异常：不知名异常", e);
            response = new RpcResponse();
            response.setStatus(BusinessRespCodeEnum.REMOTE_REQUEST_ERROR.getCode());
            return response;
        }
        
    }
    
    public ChannelPromise getChannelPromise() {
        return channelPromise;
    }
 
}