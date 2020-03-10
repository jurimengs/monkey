package org.monkey.db.core.listerers;

import org.monkey.db.core.Event;

public interface EventListener {
    void addEvent(Event operate);
    
    void run();
    
}
