package org.monkey.db.core.event.handler;

import java.util.Map;

public interface EventTypeHandler {

    public final static String MODULE_TABLE = "#{TABLE}";
    public final static String MODULE_FIELDS = "#{FIELDS}";
    public final static String MODULE_VALUES = "#{VALUES}";
    public final static String MODULE_CONDITIONS = "#{CONDITIONS}";
    
    void execute(Map<String, Object> fieldValues, String tableName, String keyFieldName, String keyFieldValue);
}
