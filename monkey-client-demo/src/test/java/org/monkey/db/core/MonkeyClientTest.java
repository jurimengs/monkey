package org.monkey.db.core;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.monkey.client.Connection;

public class MonkeyClientTest {
    @Autowired
    private Connection con; 
    
    @Test
    public void TestAdd() {
        Object object = null;
        con.save(object );
    }
}
