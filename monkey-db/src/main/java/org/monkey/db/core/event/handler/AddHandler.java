package org.monkey.db.core.event.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("addHandler")
public class AddHandler implements EventTypeHandler {
    private final static String MODULE = "insert into #{TABLE} (#{FIELDS}) values ()";

    @Override
    public void execute(Map<String, Object> fieldValues, String tableName, String keyFieldName, String keyFieldValue) {
        Set<Entry<String, Object>> entrySet = fieldValues.entrySet();
        
        for (Iterator<Entry<String, Object>> iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            
        }
        
        log.info("sql: {}", MODULE);
    }

}
