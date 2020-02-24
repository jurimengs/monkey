package org.monkey.db.core.store;

import org.monkey.db.core.executor.Executor;

public class StoreFactory {
    private static final StoreFactory sf = new StoreFactory();
    
    public static StoreFactory getInstance() {
        return sf;
    }
    
    public  HashStore createStore(Executor executor, String tableName) {
        HashStore store = new HashStore(executor, tableName);
        return store;
    }
}
