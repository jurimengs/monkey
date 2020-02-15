package org.monkey.db.core;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PressTask implements Runnable {
    private String threadName;
    private int count;
    private HashStore<String, Test> hashStore;
    
    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            Test t = new Test();
            t.setId("id:" + threadName + ":" + i);
            hashStore.put(t.getId(), t);
        }
    }

}
