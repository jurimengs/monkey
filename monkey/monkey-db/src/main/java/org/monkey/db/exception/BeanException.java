package org.monkey.db.exception;

/**
 * kinds of exceptions for bean, like 
 * @author jurimengs
 *
 */
public class BeanException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -4625221572702735803L;

    public BeanException() {
        super();
    }
    
    public BeanException(String message) {
        super(message);
    }

}
