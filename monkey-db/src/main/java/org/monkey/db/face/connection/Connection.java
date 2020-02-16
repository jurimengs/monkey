package org.monkey.db.face.connection;

import java.util.List;

/**
 * @author jurimengs
 *
 */
public interface Connection<T> {
    void update(T object);
    
    void save(T object);
    
    void delete(T object);
    
    Object select(T object);
    
    List<T> selectList(T object);
    
}
