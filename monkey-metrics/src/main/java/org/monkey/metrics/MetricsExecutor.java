package org.monkey.metrics;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MetricsExecutor {
    private static final String PRINTER = "printer";
    private static final int period = 3;
    private static final int initialDelay = 0;
    
    public MetricsExecutor() {
        start();
    }
    
    private void start() {
        // another thread to print host cpu
        ScheduledExecutorService printer = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread th = new Thread(r);
                th.setName(PRINTER);
                return th;
            }
        });
        
        printer.scheduleAtFixedRate(new ThreadCpuMetrics(), initialDelay, period, TimeUnit.SECONDS);
    }
    
}
