package org.monkey.db.face.connection;

import org.monkey.db.core.Operate;

public interface EventListener<T> {
    void addEvent(Operate<T> operate);
    
    void run();
}
