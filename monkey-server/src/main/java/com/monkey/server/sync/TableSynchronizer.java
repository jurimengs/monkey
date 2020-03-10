package com.monkey.server.sync;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.monkey.db.core.store.Store;
import org.monkey.db.core.store.Tabels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.monkey.server.sync.handler.DataSendHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jurimengs
 *
 * @param <T>
 */
@Slf4j
@Component
@ConditionalOnBean(value = DataSendHandler.class)
public class TableSynchronizer {
    @Autowired
    private DataSendHandler tableSynchronizerHandler;

    /**
     * 存量同步
     * 存量同步的时候就要标记，  要开始增量同步了
     * @Title: stockSynchronize
     * @author zhouman
     * @date 2020-03-10 11:42:46
     */
    public void stockSynchronize(String ip) {
        Map<String, Store> storeCache = Tabels.getInstance().getStoreCacheClone(ip);
        Set<Entry<String, Store>> entrySet = storeCache.entrySet();
        for (Iterator<Entry<String, Store>> iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry<String, Store> entry = iterator.next();
            tableSynchronizerHandler.synchronize(entry);
        }
    }
    
    /**
     * 增量同步
     * @Title: incrSynchronize
     * @Description: TODO(描述)
     * @author zhouman
     * @date 2020-03-10 03:06:45
     */
    public void incrSynchronize(Object obj) {
        // 通过 netty 发
    }
}
