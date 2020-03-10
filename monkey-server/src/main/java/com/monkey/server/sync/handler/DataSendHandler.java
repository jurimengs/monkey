package com.monkey.server.sync.handler;

import java.util.Map.Entry;

import org.monkey.db.core.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.monkey.server.sync.ClusterProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * TODO netty 实现数据的传输
 * @author jurimengs
 *
 * @param <T>
 */
@Slf4j
@Component
@ConditionalOnBean(value = ClusterProperties.class)
public class DataSendHandler {
    @Autowired
    private ClusterProperties clusterProperties;

    /**
     * 存量同步
     * @Title: stockSynchronize
     * @author zhouman
     * @date 2020-03-10 11:42:46
     */
    public void synchronize(Entry<String, Store> entry) {
        // TODO
        log.info("");
        
    }
}
