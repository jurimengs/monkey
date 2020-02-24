package org.monkey.db.core;

import org.apache.commons.lang3.StringUtils;
import org.monkey.db.core.store.HashStore;

public class Event {
    private HashStore store;
    private String keyFieldName; // key field name
    private String keyFieldValue; // value of key
    private EventType eventType;
    
    public HashStore getStore() {
        return store;
    }
    public void setStore(HashStore store) {
        this.store = store;
    }
    public String getKeyFieldName() {
        return keyFieldName;
    }
    public void setKeyFieldName(String keyFieldName) {
        this.keyFieldName = keyFieldName;
    }
    public String getKeyFieldValue() {
        return keyFieldValue;
    }
    public void setKeyFieldValue(String keyFieldValue) {
        this.keyFieldValue = keyFieldValue;
    }
    public EventType getEventType() {
        return eventType;
    }
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        
        Event other = (Event) obj;
        if (!StringUtils.equals(other.getKeyFieldValue(), this.getKeyFieldValue())) {
            return false;
        }
        return true;
    }
}
