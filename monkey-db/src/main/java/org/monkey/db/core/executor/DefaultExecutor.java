package org.monkey.db.core.executor;

import org.monkey.db.core.Event;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * this is an Executor for default Implement as an exmaple  
 * @author jurimengs
 *
 * @param 
 */
@Slf4j
public class DefaultExecutor implements Executor {
    @Override
    public void execute(Event poll) {
//        Test t = (Test) poll.getObject();
//        log.info("id: {}", t.getId());
        log.info("poll: {}", JSON.toJSONString(poll));
    }
}
