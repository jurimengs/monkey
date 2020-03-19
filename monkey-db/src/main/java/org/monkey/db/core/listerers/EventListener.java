package org.monkey.db.core.listerers;

import org.monkey.db.core.event.Event;

public interface EventListener {
    void addEvent(Event operate);
    
    void run();
    
}
