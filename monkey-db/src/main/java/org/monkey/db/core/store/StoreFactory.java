package org.monkey.db.core.store;

public class StoreFactory {
    private static final StoreFactory sf = new StoreFactory();
    
    public static StoreFactory getInstance() {
        return sf;
    }
    
    public <K> HashStore<K> createStore() {
        HashStore<K> store = new HashStore<>();
        return store;
    }
}
