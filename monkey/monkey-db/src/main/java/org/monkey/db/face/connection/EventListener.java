package org.monkey.db.face.connection;

import org.monkey.db.connection.Operate;

public interface EventListener<T> {
    void addEvent(Operate<T> operate);
    
    void run();
}
