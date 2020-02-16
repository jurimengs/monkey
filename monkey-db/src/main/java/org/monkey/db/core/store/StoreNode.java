package org.monkey.db.core.store;

import com.alibaba.fastjson.JSONObject;

public class StoreNode<K> {
    private int hashCode; // this hashCode is the code of "value"
    private Object value;
    private K key;
    private StoreNode<K> next;
    private StoreNode<K> prev;

    public StoreNode(K key, Object value, StoreNode<K> next) {
        this.key = key;
        this.next = next;
        setObjectValue(value);
    }

    public Object getObjectValue() {
        return value;
    }

    public void setObjectValue(Object value) {
        if(value != null) {
            this.hashCode = value.hashCode();
        }
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

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public StoreNode<K> getNext() {
        return next;
    }

    public void setNext(StoreNode<K> next) {
        this.next = next;
    }

    public StoreNode<K> getPrev() {
        return prev;
    }

    public void setPrev(StoreNode<K> prev) {
        this.prev = prev;
    }

}
