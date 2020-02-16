package org.monkey.db.core.listerers;

import org.monkey.db.core.Operate;

public interface EventListener<T> {
    void addEvent(Operate<T> operate);
    
    void run();
    
    public static final String CLUSTER_SYNCHRONIZER = "clusterSynchronizer";
}
