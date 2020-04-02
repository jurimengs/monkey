package org.monkey.db.core.event.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.monkey.constants.CommonConstants.Symbol;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("addHandler")
public class AddHandler implements EventTypeHandler {
    private final static String MODULE = "insert into #{TABLE} (#{FIELDS}) values (#{VALUES})";

    /**
     * 
     * @Title: execute
     * @Description: TODO(描述)
     * @param fieldValues
     * @param tableName
     * @param keyFieldName useless in this method
     * @param keyFieldValue useless in this method
     * @see org.monkey.db.core.event.handler.EventTypeHandler#execute(java.util.Map, java.lang.String, java.lang.String, java.lang.String) 
     * @author zhouman
     * @date 2020-04-02 06:45:44
     */
    @Override
    public void execute(Map<String, Object> fieldValues, String tableName, String keyFieldName, String keyFieldValue) {
        if(MapUtils.isEmpty(fieldValues)) {
            log.error("no fields to insert : {}", tableName);
            return;
        }
        Set<Entry<String, Object>> entrySet = fieldValues.entrySet();
        List<String> fields = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (Iterator<Entry<String, Object>> iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            
            fields.add(key);
            values.add(value);
        }
        String fieldsStr = StringUtils.join(fields, Symbol.COMMA);
        String valueStr = StringUtils.join(values, Symbol.COMMA);
        
        String sql = MODULE.replace(MODULE_TABLE, tableName)
                .replace(MODULE_FIELDS, fieldsStr)
                .replace(MODULE_VALUES, valueStr);
        
        log.info("sql: {}", sql);
    }

}
