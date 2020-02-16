package com.monkey.server.enums;

import lombok.Getter;

@Getter
public enum SystemRespCodeEnum implements CommonEnum{
    SYS_ERROR(10001, "系统异常"),
    SYS_UMS_TOKEN_VALIDATE_ERROR(10002, "UMS token 验证返回失败")
    ;
    SystemRespCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
    
    private int code;
    private String message;
}
