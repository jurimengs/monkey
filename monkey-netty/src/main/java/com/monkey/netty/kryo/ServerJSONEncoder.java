package com.monkey.netty.kryo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerJSONEncoder extends MessageToByteEncoder<Object> {
    
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf out) throws Exception {
        log.debug("服务端序列化 ...");
        try {
            byte[] data = JSONObject.toJSONString(msg, SerializerFeature.WriteClassName).getBytes() ;
            out.writeBytes(data) ;
        } catch (Exception e) {
            ReferenceCountUtil.release(msg);
            log.error("服务端编码返回异常失败", e);
        }
    }
    

}
