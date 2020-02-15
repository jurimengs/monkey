package org.monkey.db.core.listerers;

import java.util.concurrent.LinkedBlockingQueue;

import org.monkey.db.core.ClusterProperties;
import org.monkey.db.core.Operate;
import org.monkey.db.face.connection.EventListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * this is a special executor, it is not implements the interface Executor
 * if this bean be initialized, it must be executed before the other executors 
 * @author jurimengs
 *
 * @param <T>
 */
@Slf4j
@Component(EventListener.CLUSTER_SYNCHRONIZER)
@ConditionalOnBean(value = ClusterProperties.class)
public class ClusterSynchronizer {

    public void doSynchronize(LinkedBlockingQueue<Operate> queueCopy) {
        log.info("ClusterExecutor.doSynchronize size: {}", queueCopy.size());
        // TODO 
    }
}
