package com.monkey.server.enums;

import lombok.Getter;

@Getter
public enum FileRespCodeEnum implements CommonEnum{
    UPLOAD_FAILED(60001, "上传失败"), 
    FILE_IS_NOT_IMG(60002, "必需上传图片类型"), 
    NO_FILE_TO_UPLOAD(60003, "没有读取到要上传的文件"), 
    FILE_ALREADY_EXIST(60004, "文件已经存在了"), 
    UPLOAD_SUCCESS_SAVE_TO_ES_FAILED(60005, "文件上传成功 ， 保存到es失败"), 
    FILE_TYPE_CHECK_ERROR(60006, "文件类型错误"),
    FILE_COUNT_TOO_LARGE(60007, "上传文件数量过多"),
    FILE_GOT_ERROR_FILE(60008, "上传文件存在异常文件"), 
    FILE_UPLOAD_ERROR(60009, "上传文件错误"), 
    FILE_UPLOAD_RETURN_ERROR(60010, "上传文件返回null")
    ;
    FileRespCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
    
    private int code;
    private String message;
        
}
