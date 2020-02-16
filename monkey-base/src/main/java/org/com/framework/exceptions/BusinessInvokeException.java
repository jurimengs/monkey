package org.com.framework.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BusinessInvokeException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -929912649659934216L;
    private int code;
    private String message;

    public BusinessInvokeException() {
        super();
    }
    
    public BusinessInvokeException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessInvokeException(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    
}
