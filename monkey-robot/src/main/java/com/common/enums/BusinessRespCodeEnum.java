package com.common.enums;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

/**
 * 所有业务相关的错误码枚举类
 * @author zhouman
 *
 */

@Getter
public enum BusinessRespCodeEnum implements CommonEnum {
    OK(0, "请求成功"), 
    ADD_FLAG_PARAM_CHECK_FAILED(20001, "添加标签参数校验失败"), 
    REMOTE_REQUEST_EXCEPTION(20002, "远程请求异常"), 
    FAIL_SAVE_TO_ES(20003, "保存到ES失败(DB回滚)"),
    ADD_FLAG_PARAM_NOT_NULL(20004, "添加标签接口所有参数必填，请检查参数"),
    AUTH_FAILED(20005, "身份认证失败"),
    ES_FIND_LIST_RETURN_ERROR(20006, "调用ES接口查询返回失败"),
    ES_ADD_TAG_RETURN_ERROR(20007, "调用ES接口添加标签失败"),
    FILE_CENTER_RETURN_ERROR(20008, "调用文件服务接口上传失败"),
    USER_NOT_FOUND(20009, "未获取到该用户信息"),
    REMOTE_REQUEST_WAITING_ERROR(20010, "远程请求等待异常"),
    REMOTE_REQUEST_TIMEOUT(20011, "远程请求超时未返回数据"),
    REMOTE_REQUEST_ERROR(20012, "远程请求未知异常"),
    NETTY_CHANNEL_BUSY(20013, "netty 连接用完了") 
    ;
    
    BusinessRespCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
    
    private int code;
    private String message;
    
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", message);
        return json.toString();
    }
}
