package com.monkey.exceptions;

public class ModelAnnotationException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -8643130371363986983L;
    public ModelAnnotationException(){
        super();
    }
    public ModelAnnotationException(String msg){
        super(msg);
    }
    public ModelAnnotationException(Class<?> clazz){
        super("数据对象未使用 com.org.common.annotation.Table 注解, 类: " + clazz.getName());
    }
}
