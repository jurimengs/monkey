package org.com.framework.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BusinessException extends RuntimeException {
    private int code;
    private String message;
    /**
     */
    private static final long serialVersionUID = -5631834713040791029L;

    public BusinessException() {
        super();
    }
    
    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    
}
