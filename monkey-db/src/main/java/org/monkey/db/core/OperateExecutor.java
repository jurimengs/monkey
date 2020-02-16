package org.monkey.db.core;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * this is an Executor for default Implement, you may treated it as an exmaple  
 * @author jurimengs
 *
 * @param 
 */
@Slf4j
public class OperateExecutor implements Executor {
    @Override
    public void execute(Operate poll) {
//        Test t = (Test) poll.getObject();
//        log.info("id: {}", t.getId());
        log.info("poll: {}", JSON.toJSONString(poll));
    }
}
