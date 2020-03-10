package org.monkey.db.core.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.monkey.db.core.EventType;
import org.monkey.db.core.Event;
import org.monkey.db.core.executor.Executor;
import org.monkey.db.core.listerers.EventListener;
import org.monkey.db.core.listerers.NodeDataListerer;

import com.alibaba.fastjson.JSONArray;
import com.monkey.base.KeyValue;
import com.monkey.exceptions.DataException;

public class HashStore implements Searcher {
    private final static int PERSIST_FIRST_TIMEAFTER = 4;
    // 持久化触发阈值 
    private final static int PERSIST_FREQUENCY = 5; 
    // 
    private final static int linkSize = 16;
    private EventListener eventListener;
    private String tableName;
    
    private Link[] nodeLinks = new Link[linkSize];
    
    public HashStore(Executor executor, String tableName) {
        this(executor, tableName, PERSIST_FIRST_TIMEAFTER, PERSIST_FREQUENCY);
    }
    
    public HashStore(Executor executor, String tableName, int persistFirstTimeAfter, int persistFrequency) {
        for (int i = 0; i < nodeLinks.length; i++) {
            nodeLinks[i] = new Link(i);
        }
        this.tableName = tableName;
        eventListener = new NodeDataListerer(persistFirstTimeAfter, persistFrequency, executor);
    }
    
    @Override
    public void add(String keyFieldName, List<KeyValue> datas) throws DataException {
        Map<String, Object> dataMap = tranfser(datas);
        String key = (String) dataMap.get(keyFieldName);
        // get the link from links array
        Link link = getLink(key);
        if(link.getNodeByKey(key) != null) {
            throw new DataException("data exist");
        }
        
        link.add(key, dataMap);
        
        Event operate = new Event();
        operate.setEventType(EventType.ADD);
        operate.setKeyFieldValue(key);
        operate.setKeyFieldName(keyFieldName);
        operate.setStore(this);
        eventListener.addEvent(operate);
    }
    
    @Override
    public void update(String keyFieldName, List<KeyValue> datas) {
        Map<String, Object> dataMap = tranfser(datas);
        String key = (String) dataMap.get(keyFieldName);
        // get the link from links array
        Link link = getLink(key);
        
        StoreNode node = link.getNodeByKey(key);
        if(node != null) {
            Map<String, Object> objectValue = node.getObjectValue();
            for(KeyValue kv : datas) {
                objectValue.put(kv.getKey(), kv.getValue());
            }

            Event operate = new Event();
            operate.setEventType(EventType.UPDATE);
            operate.setKeyFieldValue(key);
            operate.setKeyFieldName(keyFieldName);
            operate.setStore(this);
            eventListener.addEvent(operate);
        }
    }

    @Override
    public Map<String, Object> get(String key) {
        // get the link from links array
        Link link = getLink(key);
        StoreNode node = link.getNodeByKey(key);
        
        if(node == null) {
            return null;
        }
        return node.getObjectValue();
    }

    @Override
    public void remove(String key) {
        Link link = getLink(key);
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
    
    private Link getLink(String key) {
        // get hashCode first
        int hashCode = key.hashCode();
        // hash it for index of array
        int hash = hash(hashCode);
        // get the link from links array
        return nodeLinks[hash];
    }

    private Map<String, Object> tranfser(List<KeyValue> datas) {
        // why map: map can update one field directly instead of  catching out the whole object 
        Map<String, Object> dataMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(datas)) {
            int size = datas.size();
            for (int i = 0; i < size; i++) {
                KeyValue keyValue = datas.get(i);
                dataMap.put(keyValue.getKey(), keyValue.getValue());
            }
        }
        return dataMap;
    }

    public String getTableName() {
        return tableName;
    }
    
}
