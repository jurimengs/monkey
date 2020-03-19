package org.monkey.db.core.event.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("deleteHandler")
public class DeleteHandler implements EventTypeHandler {
    private final static String MODULE = "delete from #{TABLE} where (#{CONDITIONS})";

    @Override
    public void execute(Map<String, Object> fieldValues, String tableName, String keyFieldName, String keyFieldValue) {
        // TODO Auto-generated method stub
        log.info("sql: {}", MODULE);
        
    }

}
