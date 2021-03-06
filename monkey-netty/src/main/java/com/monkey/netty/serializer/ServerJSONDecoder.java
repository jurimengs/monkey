package com.monkey.netty.serializer;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.monkey.constants.CommonConstants;
import com.monkey.netty.client.RpcRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerJSONDecoder extends MessageToMessageDecoder<ByteBuf> {
    
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, List<Object> list) throws Exception {
        RpcRequest parseObject = null;
        int len = msg.readableBytes(); // 可以用的数据长度
        byte data[] = new byte[len]; // 数据接受容器
        int readerIndex = msg.readerIndex();
        log.info("服务端反序列化 ...从buf的  {} 开始读，读取长度为 {}", readerIndex, len);
        try {
            msg.getBytes(readerIndex, data, 0, len);
            
            String text = new String(data, CommonConstants.CharSet.UTF8);
//            log.info("text: {}", text);
            
            parseObject = JSON.parseObject(text, RpcRequest.class);
        } catch (Exception e) {
            log.error("服务端反序列化异常, readerIndex:{}, len:{}", readerIndex, len, e);
            ReferenceCountUtil.release(msg);
            parseObject = new RpcRequest();
        }
        list.add(parseObject) ;
    }
}
