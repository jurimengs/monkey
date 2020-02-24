package org.monkey.db.core;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.monkey.client.MonkeyClientConnection;

public class MonkeyClientTest {
    @Autowired
    private MonkeyClientConnection con; 
    
    @Test
    public void TestAdd() {
        // TODO 
        Object object = null;
        con.save(object );
    }
}
