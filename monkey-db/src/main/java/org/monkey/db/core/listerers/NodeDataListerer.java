package org.monkey.db.core.listerers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.monkey.db.core.Event;
import org.monkey.db.core.executor.Executor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * when some object need to be add、update、delete, Node will put an event to the queue of ${NodeEventListener}, 
 * NodeEventListener will run on schedule per ${persistFrequency} seconds , 
 * NodeEventListener hope 
 * @author jurimengs
 *
 * @param 
 */
@Slf4j
@Setter
public class NodeDataListerer implements EventListener {
    private Lock addEventLock = new ReentrantLock(false);
    
    /**
     * @param persistFirstTimeAfter timer will execute after ${persistFirstTimeAfter} seconds
     * @param persistFrequency timer execute per ${persistFrequency} times 
     */
    public NodeDataListerer() {
        this.run();
    }
    
    /**
     * @param persistFirstTimeAfter timer will execute after ${persistFirstTimeAfter} seconds
     * @param persistFrequency timer execute per ${persistFrequency} times 
     */
    public NodeDataListerer(int persistFirstTimeAfter, int persistFrequency, Executor executor) {
        this.persistFirstTimeAfter = persistFirstTimeAfter;
        this.persistFrequency = persistFrequency;
        this.executor = executor;
        this.run();
    }

    @Override
    public void addEvent(Event operate) {
        String tableName = operate.getStore().getTableName();
        if(!eventCache.containsKey(tableName)) {
            try {
                boolean locked = addEventLock.tryLock();
                if(locked) {
                    List<Event> list = new LinkedList<>();
                    list.add(operate);
                    eventCache.put(tableName, list);
                }
            } finally {
                addEventLock.unlock();
            }
            
        } else {
            List<Event> list = eventCache.get(tableName);
            if(!list.contains(operate)) {
                boolean locked = addEventLock.tryLock();
                if(locked) {
                    list.add(operate);
                }
            }
        }
    }
    

    @Override
    public void run() {
        // 第一次执行的时间为 ${persistFirstTimeAfter} 秒，然后每隔 ${persistFrequency} 秒执行一次
        taskExecutor.scheduleAtFixedRate(new PersistTask(), persistFirstTimeAfter, persistFrequency, TimeUnit.SECONDS);
    }
    
    private Map<String, List<Event>> getCacheCopy() {
        Map<String, List<Event>> cache = null;
        synchronized (lock) {
            cache = eventCache;
            eventCache = new HashMap<>();
        }
        return cache;
    }

    private class PersistTask implements Runnable {
        @Override
        public void run() {
            log.debug("listening ... ");
            Map<String, List<Event>> cacheCopy = getCacheCopy();
            log.info("task count to deal {}", cacheCopy.size());
            
            // <tableName, Event>
            for (Iterator<Entry<String, List<Event>>> iterator = cacheCopy.entrySet().iterator(); iterator.hasNext();) {
                Entry<String, List<Event>> next = iterator.next();
                List<Event> events = next.getValue();
                for (Event tmp : events) {
                    executor.execute(tmp);
                }
                
            }
        }
        
    }
    
    private byte[] lock = new byte[1];
    // 第一次执行在 4 秒后
    private int persistFirstTimeAfter = 4;
    // 持久化触发阈值， 这个值要测试
    private int persistFrequency = 5; // 单位 秒 
    //
    private Executor executor;
    // 
    private Map<String, List<Event>> eventCache = new HashMap<>();
    // 单线程执行
    private ScheduledExecutorService taskExecutor = Executors.newSingleThreadScheduledExecutor();
}
