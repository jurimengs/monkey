package org.monkey.db.core.store;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class StoreNode {
    private int hashCode; // this hashCode is the code of "key"
    private Map<String, Object> value;
    private String key;
    private StoreNode next;
    private StoreNode prev;

    public StoreNode(String key, Map<String, Object> value, StoreNode next) {
        this.key = key;
        this.next = next;
        setObjectValue(value);
    }

    public Map<String, Object> getObjectValue() {
        return value;
    }

    public void setObjectValue(Map<String, Object> value) {
        this.value = value; 
    }
    
    public JSONObject toJSON() {
        JSONObject thisObj = new JSONObject();
        thisObj.put("key", this.key);
        thisObj.put("value", this.value);
        return thisObj;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.hashCode = key.hashCode();
        this.key = key;
    }

    public StoreNode getNext() {
        return next;
    }

    public void setNext(StoreNode next) {
        this.next = next;
    }

    public StoreNode getPrev() {
        return prev;
    }

    public void setPrev(StoreNode prev) {
        this.prev = prev;
    }

}
