package org.monkey.db.core.store;

/**
 * for search bean
 * @author jurimengs
 *
 */
public interface Store<K, T> {
    /**
     * 如果有则更新，如果没有则添加
     * @param key
     * @param object
     */
    void put(K key, T object);
    
    T get(K key);

    void remove(K key);
}
