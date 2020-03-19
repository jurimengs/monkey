package com.monkey.server.sync;

import java.util.concurrent.LinkedBlockingQueue;

import org.monkey.db.core.event.Event;
import org.monkey.db.core.listerers.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jurimengs
 *
 * @param <T>
 */
@Slf4j
@Component
@ConditionalOnBean(value = ClusterProperties.class)
public class EventSynchronizer {
    @Autowired
    private ClusterProperties clusterProperties;

    /**
     *  
     * @Title: doSynchronize
     * @param eventCopy
     * @author zhouman
     * @date 2020-03-10 11:42:46
     */
    public void doSynchronize(LinkedBlockingQueue<Event> eventCopy) {
        log.info("ClusterExecutor.doSynchronize size: {}", eventCopy.size());
        // TODO 
        
    }
}
