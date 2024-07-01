package com.core.exception;

/**
 * 应该要给用户提示的消息
 */
public class MessageAlertException extends InterruptedException{
    public MessageAlertException(String msg) {
        super(msg);
    }
}
