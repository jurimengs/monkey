package com.monkey.exceptions;

import org.apache.commons.lang3.StringUtils;

/**
 */
public class MetadataReaderException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -5909481057394521259L;
    private String code;
    private String message;

    public MetadataReaderException() {
        super();
    }

    public MetadataReaderException(String message) {
        this.code = StringUtils.EMPTY;
        this.message = message;
    }

    public MetadataReaderException(int code, String message) {
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
