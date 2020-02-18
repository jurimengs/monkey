package org.monkey.db.core;

import org.junit.Test;
import org.monkey.db.face.connection.Connection;
import org.springframework.beans.factory.annotation.Autowired;

public class MonkeyClientTest {
    @Autowired
    private Connection con; 
    
    @Test
    public void TestAdd() {
        Object object = null;
        con.save(object );
    }
}
