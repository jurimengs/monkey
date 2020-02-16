package org.monkey.db.core.listerers;

import org.monkey.db.core.Operate;

public interface EventListener {
    void addEvent(Operate operate);
    
    void run();
    
    public static final String CLUSTER_SYNCHRONIZER = "clusterSynchronizer";
}
