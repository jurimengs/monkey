package com.monkey.server.enums;

import lombok.Getter;

@Getter
public enum UserRespCodeEnum implements CommonEnum{
    LOGIN_ERROR(21001, "登录异常"), 
    PWD_ERROR(21002, "密码错误"), 
    USER_NOT_EXIST_ERROR(21003, "用户不存在"), 
    USER_ID_NOT_NULL_ERROR(21004, "用户id不能为空"), 
    REGIST_EXIST_ERROR(21005, "该用户名已存在"), 
    REGIST_LOGIN_NAME_NULL_ERROR(21006, "用户名不能为空"), 
    REGIST_USER_NAME_NULL_ERROR(21007, "用户昵称不能为空"), 
    REGIST_PWD_NULL_ERROR(21008, "密码不能为空"),
    UPDATE_ORIGIN_PWD_ERROR(21009, "原密码错误"),
    USER_NOT_LOGIN_ERROR(21010, "未登录"),
    USER_ROLE_ERROR(21011, "用户无角色")
    ;
    UserRespCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
    
    private int code;
    private String message;
}
