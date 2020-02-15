package org.monkey.metrics;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadCpuMetrics implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ThreadCpuMetrics.class);
    
    public ThreadCpuMetrics() {
        processorCount = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
        threadBean = ManagementFactory.getThreadMXBean();
        this.cpuModelList = initCpuModels(threadBean, processorCount);
    }
    
    public ThreadCpuMetrics(int processorCount, ThreadMXBean threadBean) {
        this.processorCount = processorCount;
        this.threadBean = threadBean;
    }

    @Override
    public void run() {
        double cputotal = 0D;
        for(ThreadCpuModel cpuModel : cpuModelList) {
            double cpuTime = cpuModel.getCpuTime();
            long collectTime = cpuModel.getCollectTime();
            
            if (cpuTime == 0) {
                cpuTime = threadBean.getThreadCpuTime(cpuModel.getThreadId());
                collectTime = System.currentTimeMillis();
                cpuModel.setCpuTime(cpuTime);
                cpuModel.setCollectTime(collectTime);
            } else {
                double newCpuTime = threadBean.getThreadCpuTime(cpuModel.getThreadId());
                long newCollectTime = System.currentTimeMillis();
                double cpu = (newCpuTime - cpuTime) / (newCollectTime - collectTime) / 1000_000 / processorCount;
                cpuTime = newCpuTime;
                collectTime = newCollectTime;
                double singleThreadCpu = cpu * 100;
                if(singleThreadCpu > 4) {
                    logger.info("线程 {} cpu利用率 {}", cpuModel.getThreadName(), String.format("%.2f %%", singleThreadCpu));
//                System.out.println(cpuModel.getThreadName() + " ----> " + String.format("Process cpu is: %.2f %%", cpu * 100));
                }
                
                cputotal += cpu;
            }
        }
        double totalCpu = cputotal * 100;
        if(totalCpu > 50) {
            logger.info("总cpu利用率 {}", String.format("%.2f %%", totalCpu));
//        System.out.println("total ==============" + String.format("Process cpu is: %.2f %%", cputotal * 100));
        }
    }
    
    private List<ThreadCpuModel> initCpuModels(ThreadMXBean threadBean, int processorCount) {
        List<ThreadCpuModel> cpuModelList = new ArrayList<>();
        
        long[] allThreadIds = threadBean.getAllThreadIds();
        for (long id : allThreadIds) {
            ThreadCpuModel model = new ThreadCpuModel();
            ThreadInfo threadInfo = threadBean.getThreadInfo(id);
            model.setThreadId(id);
            model.setThreadName(threadInfo.getThreadName());
            cpuModelList.add(model);
        }
        return cpuModelList;
    }
    
    private List<ThreadCpuModel> cpuModelList;
    private int processorCount;
    private ThreadMXBean threadBean;
    
}