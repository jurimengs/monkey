package org.monkey.db.core.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.monkey.db.core.executor.Executor;

public class Tabels {
    private static Tabels SINGLE = new Tabels();
    private byte[] bytelock = new byte[0];
    
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
}
