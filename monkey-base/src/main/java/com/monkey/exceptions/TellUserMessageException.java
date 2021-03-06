package com.monkey.exceptions;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description 拦截异常后，将信息返回前端的异常类
 * 
 * @author zhouman 2019年1月20日
 */
public class TellUserMessageException extends RuntimeException {
    private static final long serialVersionUID = -2483604307238761050L;
    private String code;
    private String message;

    public TellUserMessageException() {
        super();
    }

    public TellUserMessageException(String message) {
        this.code = StringUtils.EMPTY;
        this.message = message;
    }

    public TellUserMessageException(int code, String message) {
        this.code = StringUtils.EMPTY;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
