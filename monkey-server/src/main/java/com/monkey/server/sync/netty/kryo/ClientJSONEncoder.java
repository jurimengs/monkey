package com.monkey.server.sync.netty.kryo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.monkey.server.sync.netty.client.RpcRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientJSONEncoder extends MessageToByteEncoder<RpcRequest> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcRequest msg, ByteBuf out) throws Exception {
        log.debug("客户端序列化 ...");
        try {
            byte data [] = JSONObject.toJSONString(msg, SerializerFeature.WriteClassName).getBytes();
            out.writeBytes(data) ;
        } catch (Exception e) {
            ReferenceCountUtil.release(msg);
            log.error("ClientJSONEncoder error ...", e);
        }
    }
    

}
