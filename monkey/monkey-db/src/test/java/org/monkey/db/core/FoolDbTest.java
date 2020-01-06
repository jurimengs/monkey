package org.monkey.db.core;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FoolDbTest {
    public static void main(String[] args) {
        HashStore<String, Test> hashStore = new HashStore<>(2, 10);
        
        pressTest(hashStore);
    }

    private static void pressTest(HashStore<String, Test> hashStore) {
        log.info("pressTest start ");
        for (int i = 0; i < 200; i++) {
            Thread t = new Thread(new PressTask("thread-" + i, 50, hashStore));
            t.start();
        }
    }
    
}
