package org.monkey.db.core.event.handler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("deleteHandler")
public class DeleteHandler implements EventTypeHandler {
    private final static String MODULE = "delete from #{TABLE} where (#{CONDITIONS})";

    /**
     * 
     * @Title: execute
     * @Description: TODO(描述)
     * @param fieldValues useless in this method
     * @param tableName
     * @param keyFieldName
     * @param keyFieldValue 
     * @see org.monkey.db.core.event.handler.EventTypeHandler#execute(java.util.Map, java.lang.String, java.lang.String, java.lang.String) 
     * @author zhouman
     * @date 2020-04-02 06:50:45
     */
    @Override
    public void execute(Map<String, Object> fieldValues, String tableName, String keyFieldName, String keyFieldValue) {

        if(StringUtils.isEmpty(keyFieldName) || StringUtils.isEmpty(keyFieldValue)) {
            log.error("no primary key to delete : {}", tableName);
            return;
        }
        
        StringBuilder condition = new StringBuilder();
        condition.append(keyFieldName).append(" = ").append(keyFieldValue);
        
        String sql = MODULE.replace(MODULE_TABLE, tableName)
                .replace(MODULE_CONDITIONS, condition);
        
        log.info("sql: {}", sql);
    }

}
