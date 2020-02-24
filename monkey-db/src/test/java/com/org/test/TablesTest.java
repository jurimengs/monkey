package com.org.test;

import java.util.ArrayList;
import java.util.List;

import org.monkey.db.core.executor.Executor;
import org.monkey.db.core.executor.MysqlExecutor;
import org.monkey.db.core.store.Store;
import org.monkey.db.core.store.Tabels;

import com.monkey.base.KeyValue;
import com.monkey.exceptions.DataException;

public class TablesTest {
    public static void main(String[] args) {
        String tableName = "test";
        new Thread() {
            @Override
            public void run() {
                Executor executor = new MysqlExecutor();
                Store store = Tabels.getInstance().getStore(tableName, executor);
                String keyFieldName = "id";
                try {
                    List<KeyValue> datas = new ArrayList<>();
                    KeyValue keyValue = new KeyValue();
                    keyValue.setKey(keyFieldName);
                    keyValue.setValue("333aaa");
                    
                    datas.add(keyValue);
                    store.add(keyFieldName, datas );
                } catch (DataException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        
        new Thread() {
            @Override
            public void run() {
                Executor executor = new MysqlExecutor();
                Store store = Tabels.getInstance().getStore(tableName, executor);
                String keyFieldName = "id";
                try {
                    List<KeyValue> datas = new ArrayList<>();
                    KeyValue keyValue = new KeyValue();
                    keyValue.setKey(keyFieldName);
                    keyValue.setValue("abcdedf");
                    
                    datas.add(keyValue);
                    store.add(keyFieldName, datas );
                } catch (DataException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        
    }
}
