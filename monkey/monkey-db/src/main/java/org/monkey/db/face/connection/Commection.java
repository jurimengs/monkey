package org.monkey.db.face.connection;

import java.util.List;

/**
 * This interface is doing something as java.sql.Connection
 * crazy start at naming
 * @author jurimengs
 *
 */
public interface Commection<T> {
    void update(T object);
    
    void save(T object);
    
    void delete(T object);
    
    Object select(T object);
    
    List<T> selectList(T object);
    
}
