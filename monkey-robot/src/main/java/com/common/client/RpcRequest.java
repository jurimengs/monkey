package com.common.client;

import com.alibaba.fastjson.JSON;
import com.common.abstracts.CustomEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RpcRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4994724510168064878L;
//    private String id;
//    private Class<?> beanClass; // 目标class， 这玩意不太好用， 不建议使用
//    private String beanName;
//    private String method; // 目标 class 的执行方法名称
//    private Class<?>[] parameterTypes; // 目标 class 的执行方法，其参数类型
//    private Object[] args; // 执行参数

    private CustomEvent customEvent;
    
    public String toString() {
        return "RpcRequest{" +JSON.toJSONString(this)+ "}";
    }

    public CustomEvent getCustomEvent() {
        return customEvent;
    }

    public void setCustomEvent(CustomEvent customEvent) {
        this.customEvent = customEvent;
    }
}