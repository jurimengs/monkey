package org.com.framework.exceptions;

public class SqlProviderException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -6347308766459070364L;
    public SqlProviderException(){
        super();
    }
    public SqlProviderException(String msg){
        super(msg);
    }
}
