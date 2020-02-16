package org.monkey.db.core;

public class Operate<T> {
    private T object;
    private EventType eventType;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    
}
