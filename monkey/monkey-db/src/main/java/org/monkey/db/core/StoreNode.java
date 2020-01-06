package org.monkey.db.core;

import com.alibaba.fastjson.JSONObject;

public class StoreNode<K, V> {
    private int hashCode; // this hashCode is the code of "value"
    private V value;
    private K key;
    private StoreNode<K, V> next;
    private StoreNode<K, V> prev;

    StoreNode(K key, V value, StoreNode<K, V> next) {
        this.key = key;
        this.next = next;
        setValue(value);
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
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

    public StoreNode<K, V> getNext() {
        return next;
    }

    public void setNext(StoreNode<K, V> next) {
        this.next = next;
    }

    public StoreNode<K, V> getPrev() {
        return prev;
    }

    public void setPrev(StoreNode<K, V> prev) {
        this.prev = prev;
    }

}
