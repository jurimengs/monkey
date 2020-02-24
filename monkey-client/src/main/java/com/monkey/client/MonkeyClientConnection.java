package com.monkey.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;

import org.monkey.db.face.annotation.PrimaryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monkey.base.DataModelIn;
import com.monkey.base.KeyValue;
import com.monkey.constants.CommonConstants;
import com.monkey.exceptions.MetadataReaderException;
import com.monkey.exceptions.ModelAnnotationException;
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
    private static final String NAME = "name";
    private static final String VALUE = "value";

    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
    
    @Autowired
    private NettyClient nettyClient;
    
    public void update(Object object) {
        DataModelIn parseObject = parseObject(object);
        
        execute(parseObject, "update", new Class<?>[] {
            DataModelIn.class
        } );
    }

    
    public void save(Object object) {
        DataModelIn parseObject = parseObject(object);
        execute(parseObject, "save", new Class<?>[] {
            DataModelIn.class
        } );
    }

    
    public void delete(Object object) {
        DataModelIn parseObject = parseObject(object);
        execute(parseObject, "delete", new Class<?>[] {
            DataModelIn.class
        } );
    }

    
    public Object select(Object object) {
        DataModelIn parseObject = parseObject(object);
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
    
    private DataModelIn parseObject(Object object) {
        Class<?> clazz = object.getClass();
        MetadataReader metadataReader = getMetadataReader(clazz.getName());
        
        DataModelIn dataModelIn = null;
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        
        if(!annotationMetadata.hasAnnotation(PrimaryKey.class.getName())) {
            throw new ModelAnnotationException("monkey: class " + clazz.getName() + 
                    " need annotation org.monkey.db.face.annotation.PrimaryKey") ;
        }
        if(!annotationMetadata.hasAnnotation(Entity.class.getName())) {
            throw new ModelAnnotationException("object need annotation : javax.persistence.Entity");
        }
        Map<String, Object> annotationPrimaryKey = 
                annotationMetadata.getAnnotationAttributes(PrimaryKey.class.getName());
        String primaryKey = (String) annotationPrimaryKey.get(VALUE);

        Map<String, Object> annotationEntity = 
                annotationMetadata.getAnnotationAttributes(Entity.class.getName());
        
        Object tableNameObject = annotationEntity.get(NAME);
        if (tableNameObject == null) {
            log.error("annotation javax.persistence.Entity need a \"name\": {}", annotationMetadata.getClassName());
            throw new ModelAnnotationException("annotation javax.persistence.Entity need a \"name\"");
        }
        dataModelIn = new DataModelIn();
        List<KeyValue> datas = parseData(object);
        dataModelIn.setPrimaryKey(primaryKey);
        dataModelIn.setDatas(datas);
        dataModelIn.setTableName(tableNameObject.toString());
        dataModelIn.setOperateTime(new Date());
    
        return dataModelIn;
    }

    private MetadataReader getMetadataReader(String clazzName) {
        MetadataReader metadataReader;
        try {
            metadataReader = metadataReaderFactory.getMetadataReader(clazzName);
        } catch (IOException e) {
            log.error("clazzName: {} got IOException ", clazzName, e);
            throw new MetadataReaderException(e.getMessage());
        }
        return metadataReader;
    }

    private List<KeyValue> parseData(Object object) {
        List<KeyValue> datas = new ArrayList<>();
        JSONObject json = JSON.parseObject(toJSONString(object));
        Set<Entry<String, Object>> entrySet = json.entrySet();
        for (Iterator<Entry<String, Object>> iterator = entrySet.iterator(); iterator.hasNext();) {
            KeyValue kv = entryToKeyValue(iterator.next());
            datas.add(kv);
        }
        return datas;
    }
    
    private KeyValue entryToKeyValue(Entry<String, Object> next) {
        KeyValue kv = new KeyValue();
        kv.setKey(next.getKey());
        kv.setValue(next.getValue());
        return kv;
    }

    private String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }
}
