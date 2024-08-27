package com.monkey.spider;

import java.util.concurrent.*;

public class ThreadPool {
    private static int corePoolSize = 4;
    private static int maximumPoolSize = 8;
    private static long keepAliveTime = 1000;
    private static TimeUnit unit = TimeUnit.MILLISECONDS;
    private static BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(30);
    private static ThreadPoolExecutor executor = 
            new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, 
                    unit, workQueue, Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
    
    public static void execute(Runnable command) {
        executor.execute(command);
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
