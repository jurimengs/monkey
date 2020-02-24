package org.monkey.db.core.executor;

import java.util.Map;

import org.apache.ibatis.binding.MapperMethod.SqlCommand;
import org.apache.ibatis.jdbc.SQL;
import org.monkey.db.core.Event;
import org.monkey.db.core.EventType;
import org.monkey.db.core.store.HashStore;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * this is an Executor for default Implement as an exmaple  
 * @author jurimengs
 *
 * @param 
 */
@Slf4j
public class MysqlExecutor implements Executor {
    @Override
    public void execute(Event poll) {
        HashStore store = poll.getStore();
        String tableName = store.getTableName(); // 表名
        EventType eventType = poll.getEventType(); // 事件
        String keyFieldName = poll.getKeyFieldName(); // 主键名称
        String keyFieldValue = poll.getKeyFieldValue(); // 主键值 
        
        Map<String, Object> map = store.get(keyFieldValue); // 主键对应的对象所有非空字段 值 
        
        
        // poll: {"eventType":"ADD","object":{"datas":[{"key":"name","value":"name"},{"key":"id","value":"111"}],"operateTime":1582436570194,"primaryKey":"id","tableName":"t_test","version":0}}
//        SqlCommand sql = new SqlCommand(configuration, mapperInterface, method);
        SQL sql = new SQL();
        log.info("poll: {}", JSON.toJSONString(poll));
    }
}
