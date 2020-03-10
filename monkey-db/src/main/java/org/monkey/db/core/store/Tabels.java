package org.monkey.db.core.store;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.monkey.db.core.executor.Executor;

public class Tabels {
    private static Tabels SINGLE = new Tabels();
    private byte[] bytelock = new byte[0];
    private Map<String, Boolean> incrStartMap = new ConcurrentHashMap<>(); // 用于标记某个 ip ， 是否开始增量同步
    
    private Map<String, Store> storeCache = new ConcurrentHashMap<>();
    
    public static Tabels getInstance() {
        return SINGLE;
    }
    
    public Store getStore(String tableName, Executor executor) {
        // return if contains it
        if(storeCache.containsKey(tableName)) {
            return storeCache.get(tableName);
        }
        
        // or will create one
        synchronized(bytelock) {
            // check exist again, 
            if(storeCache.containsKey(tableName)) {
                return storeCache.get(tableName);
            }
            Store store = StoreFactory.getInstance().createStore(executor, tableName);
            storeCache.put(tableName, store);
            return store;
        }
    }
    
    /**
     * 请求数据同步的存量数据 , 该方法不会被频繁调用
     * @Title: getStoreCacheClone
     * @param ip 请求数据同步的节点 ip
     * @return
     * @author zhouman
     * @date 2020-03-10 03:50:37
     */
    public Map<String, Store> getStoreCacheClone(String ip) {
        // 把 storeCache 锁住，对于数据世界来讲就是 STW
        // 这样就能确保 incrStartMap 与 storeCache 的copy 节点保持一致了吗？
        HashMap<String, Store> tmp = new HashMap<>();
        synchronized (storeCache) {
            // 再锁 incrStartMap， 这样读 incrStartMap 的也在等待。读 incrStartMap 的地方主要是 HashStore.add ， 
            // add 如果读不了，会造成 add 卡顿，直到 这两重锁释放
            // 也就是说， 在添加节点的时候，应该尽量选择空闲的时候
            synchronized(incrStartMap) {
                incrStartMap.put(ip, Boolean.TRUE);
            }
            tmp.putAll(storeCache);
        }
        return tmp;
    }
    
    public Map<String, Boolean> getIncrSychClusterIp() {
        return incrStartMap;
    }
    
}
