package org.monkey.db.core.event.handler;

import java.util.Map;

public interface EventTypeHandler {
    void execute(Map<String, Object> fieldValues, String tableName, String keyFieldName, String keyFieldValue);
}
