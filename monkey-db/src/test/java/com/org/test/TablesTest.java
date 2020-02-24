package com.org.test;

import org.monkey.db.core.store.Store;
import org.monkey.db.core.store.Tabels;

public class TablesTest {
    public static void main(String[] args) {
        String tableName = "test";
        new Thread() {
            @Override
            public void run() {
                Tabels.getInstance();
            }
        }.start();
        
        new Thread() {
            @Override
            public void run() {
                Tabels.getInstance();
            }
        }.start();
        
    }
}
