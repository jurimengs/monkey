package org.monkey.db.core.executor;

import java.util.Map;

import org.monkey.db.core.event.Event;
import org.monkey.db.core.event.EventType;
import org.monkey.db.core.event.handler.EventTypeHandler;
import org.monkey.db.core.store.HashStore;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * this is an Executor for default Implement as an exmaple  
 * @author jurimengs
 *
 * @param 
 */
@Slf4j
public class MysqlExecutor implements Executor, ApplicationContextAware {
    
    @Override
    public void execute(Event event) {
        HashStore store = event.getStore();
        String tableName = store.getTableName(); // 表名
        EventType eventType = event.getEventType(); // 事件
        String keyFieldName = event.getKeyFieldName(); // 主键名称
        String keyFieldValue = event.getKeyFieldValue(); // 主键值 
        
        // poll: {"eventType":"ADD","keyFieldName":"id","keyFieldValue":"111","store":{"allLink":[{"hash":0},{"first":{"key":"111","objectValue":{"name":"name","id":"111"}},"hash":1,"last":{}],"tableName":"t_test"}}
        
        Map<String, Object> map = store.get(keyFieldValue); // 主键对应的对象所有非空字段 值 
        log.info("poll: tableName : {}, eventType : {}, keyFieldName : {}, keyFieldValue : {}, value map : {}", 
                tableName, eventType, keyFieldName, keyFieldValue, JSON.toJSONString(map));
        
        String name = eventType.toString().toLowerCase() + "Handler";
        EventTypeHandler eventHandler = (EventTypeHandler) applicationContext.getBean(name);
        eventHandler.execute(map, tableName, keyFieldName, keyFieldValue);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    private ApplicationContext applicationContext;
}
