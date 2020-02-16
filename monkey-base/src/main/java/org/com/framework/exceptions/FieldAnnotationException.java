package org.com.framework.exceptions;

import java.lang.reflect.Field;

public class FieldAnnotationException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -8643130371363986983L;
    public FieldAnnotationException(){
        super();
    }
    public FieldAnnotationException(String msg){
        super(msg);
    }
    public FieldAnnotationException(Field field){
        super("域未使用 com.org.common.annotation.Column 注解, Field: " + field.getName());
    }
}
