package org.monkey.db.core.store;

import org.apache.commons.lang3.StringUtils;
import org.monkey.db.core.EventType;
import org.monkey.db.core.Executor;
import org.monkey.db.core.Operate;
import org.monkey.db.core.OperateExecutor;
import org.monkey.db.core.Searcher;
import org.monkey.db.core.listerers.EventListener;
import org.monkey.db.core.listerers.NodeEventListener;

import com.alibaba.fastjson.JSONArray;

public class HashStore<K> implements Searcher<K> {
    private final static int PERSIST_FIRST_TIMEAFTER = 4;
    // 持久化触发阈值 
    private final static int PERSIST_FREQUENCY = 5; 
    // 
    private final static int linkSize = 16;
    private EventListener eventListener;
    
    @SuppressWarnings("unchecked")
    private Link<K>[] nodeLinks = new Link[linkSize];
    
    public HashStore() {
        this(new OperateExecutor(), PERSIST_FIRST_TIMEAFTER, PERSIST_FREQUENCY);
    }
    
    public HashStore(Executor executor) {
        this(executor, PERSIST_FIRST_TIMEAFTER, PERSIST_FREQUENCY);
    }
    
    public HashStore(int persistFirstTimeAfter, int persistFrequency) {
        this(new OperateExecutor(), persistFirstTimeAfter, persistFrequency);
    }
    
    public HashStore(Executor executor, int persistFirstTimeAfter, int persistFrequency) {
        for (int i = 0; i < nodeLinks.length; i++) {
            nodeLinks[i] = new Link<>(i);
        }
        eventListener = new NodeEventListener(persistFirstTimeAfter, persistFrequency, executor);
    }
    
    @Override
    public void put(K key, Object object) {
        // get the link from links array
        Link<K> link = getLink(key);
        
        StoreNode<K> node = link.getNodeByKey(key);
        
        if(node != null) {
            // 直接替换
            node.setKey(key);
            node.setObjectValue(object);
            addEvent(object, EventType.UPDATE);
        } else {
            // 如果没有的话， 则添加进去
            link.add(key, object);
            addEvent(object, EventType.ADD);
        }
    }

    @Override
    public Object get(K key) {
        // get the link from links array
        Link<K> link = getLink(key);
        StoreNode<K> node = link.getNodeByKey(key);
        
        if(node == null) {
            return null;
        }
        return node.getObjectValue();
    }

    @Override
    public void remove(K key) {
        Link<K> link = getLink(key);
        link.remove(key);
    }
    
    /**
     * 
     */
    public static final int hash(int hashCode) {
        int h = hashCode;
        int tmp = h ^ h >> linkSize; //散列
        return tmp % linkSize;
    }

    /**
     * 
     * @return
     */
    public int size() {
        int total = 0;
        for (int i = 0; i < nodeLinks.length; i++) {
            total += nodeLinks[i].size();
        }
        return total;
    }
    
    /**
     * @return fastjson
     */
    @Override
    public String toString() {
        JSONArray thisArr = new JSONArray();
        for (int i = 0; i < nodeLinks.length; i++) {
            if(StringUtils.isNotBlank(nodeLinks[i].toString())) {
                thisArr.addAll(nodeLinks[i].toJSONArray());
            }
        }
        return thisArr.toJSONString();
    }
    
    // 添加事件
    private void addEvent(Object object, EventType eventType) {
        Operate operate = new Operate();
        operate.setEventType(eventType);
        operate.setObject(object);
        eventListener.addEvent(operate);
    }

    private Link<K> getLink(K key) {
        // get hashCode first
        int hashCode = key.hashCode();
        // hash it for index of array
        int hash = hash(hashCode);
        // get the link from links array
        return nodeLinks[hash];
    }
    
}
