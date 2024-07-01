package com.common.server;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RpcResponse implements Serializable {
 
    /**
     * 
     */
    private static final long serialVersionUID = 5295615864204126713L;
    
    private String id;
    private String msg;
    private Object data;
    // 0=success -1=fail
    private int status;
    
    public boolean isSuccess() {
        return status == 0;
    }
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public Object getData() {
        return data;
    }
 
    public void setData(Object data) {
        this.data = data;
    }
 
    public int getStatus() {
        return status;
    }
 
    public void setStatus(int status) {
        this.status = status;
    }
 
    @Override
    public String toString() {
        return "RpcResponse{" + JSON.toJSONString(this) + '}';
    }
}