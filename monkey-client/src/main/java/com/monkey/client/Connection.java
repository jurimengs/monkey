package com.monkey.client;

import java.util.List;

public interface Connection {

    void update(Object object);
    
    void save(Object object);
    
    void delete(Object object);
    
    Object select(Object object);
    
    List<Object> selectList(Object object);

}
