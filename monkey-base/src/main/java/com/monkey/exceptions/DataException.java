package com.monkey.exceptions;

/**
 * kinds of exceptions for bean, like 
 * @author jurimengs
 *
 */
public class DataException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8525732672447685544L;

    public DataException() {
        super();
    }
    
    public DataException(String message) {
        super(message);
    }

}
