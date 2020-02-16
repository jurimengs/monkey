package org.monkey.db.core.listerers;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.monkey.db.core.Executor;
import org.monkey.db.core.Operate;

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
public class NodeEventListener implements EventListener {
    
    /**
     * @param persistFirstTimeAfter timer will execute after ${persistFirstTimeAfter} seconds
     * @param persistFrequency timer execute per ${persistFrequency} times 
     */
    public NodeEventListener() {
        this.run();
    }
    
    /**
     * @param persistFirstTimeAfter timer will execute after ${persistFirstTimeAfter} seconds
     * @param persistFrequency timer execute per ${persistFrequency} times 
     */
    public NodeEventListener(int persistFirstTimeAfter, int persistFrequency, Executor executor) {
        this.persistFirstTimeAfter = persistFirstTimeAfter;
        this.persistFrequency = persistFrequency;
        this.executor = executor;
        this.run();
    }

    @Override
    public void addEvent(Operate operate) {
        synchronized (lock) {
            queue.add(operate);
        }
    }
    

    @Override
    public void run() {
        // 第一次执行的时间为 ${persistFirstTimeAfter} 秒，然后每隔 ${persistFrequency} 秒执行一次
        taskExecutor.scheduleAtFixedRate(new PersistTask(), persistFirstTimeAfter, persistFrequency, TimeUnit.SECONDS);
    }
    
    private LinkedBlockingQueue<Operate> getQueueCopy() {
        LinkedBlockingQueue<Operate> queueCopy = null;
        synchronized (lock) {
            queueCopy = queue;
            queue = new LinkedBlockingQueue<>();
        }
        return queueCopy;
    }

    private class PersistTask implements Runnable {
        @Override
        public void run() {
            log.debug("listening ... ");
            LinkedBlockingQueue<Operate> queueCopy = getQueueCopy();
            log.info("task count to deal {}", queueCopy.size());
            
            Operate poll = null;
            while ((poll = queueCopy.poll()) != null) {
                executor.execute(poll);
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
    private LinkedBlockingQueue<Operate> queue = new LinkedBlockingQueue<>();
    // 单线程执行
    private ScheduledExecutorService taskExecutor = Executors.newSingleThreadScheduledExecutor();
}
