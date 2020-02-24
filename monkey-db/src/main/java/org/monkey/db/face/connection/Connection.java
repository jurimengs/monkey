package org.monkey.db.face.connection;

import java.util.List;

import com.monkey.base.DataModelIn;
import com.monkey.exceptions.DataException;

/**
 * @author jurimengs
 *
 */
public interface Connection {
    void update(DataModelIn object);
    
    void save(DataModelIn object) throws DataException;
    
    void delete(DataModelIn object);
    
    Object select(DataModelIn object);
    
    List<Object> selectList(DataModelIn object);
    
}
