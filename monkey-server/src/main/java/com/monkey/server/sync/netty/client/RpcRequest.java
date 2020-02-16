package com.monkey.server.sync.netty.client;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RpcRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4994724510168064878L;
    private String id;
    private Class<?> beanClass; // 目标class， 一般为 Service
    private String beanName;
    private String method; // 目标 class 的执行方法名称
    private Class<?>[] parameterTypes; // 目标 class 的执行方法，其参数类型
    private Object[] args; // 执行参数
    
    public String toString() {
        return "RpcRequest{" +JSON.toJSONString(this)+ "}";
    }


}