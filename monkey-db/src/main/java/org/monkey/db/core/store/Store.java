package org.monkey.db.core.store;

import java.util.List;
import java.util.Map;

import com.monkey.base.KeyValue;
import com.monkey.exceptions.DataException;

/**
 * for search bean
 * @author jurimengs
 *
 */
public interface Store {
    /**
     * 如果没有则添加
     * @param key
     * @param object
     */
    void add(String key, List<KeyValue> datas) throws DataException;
    /**
     * @param keyFieldName 主键的字段名
     * @param object
     */
    void update(String keyFieldName, List<KeyValue> datas);
    
    Map<String, Object> get(String key);

    void remove(String key);
    
    Link[] getAllLink();
}
