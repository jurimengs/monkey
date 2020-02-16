package org.monkey.db.core.store;

public class StoreFactory {
    private static final StoreFactory sf = new StoreFactory();
    
    public static StoreFactory getInstance() {
        return sf;
    }
    
    public <K, T> HashStore<K, T> createStore() {
        HashStore<K, T> store = new HashStore<>();
        return store;
    }
}
