package org.monkey.db.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.monkey.db.face.connection.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfigration.class)
public class MonkeyMapperTest {
    @Autowired
    private Mapper<TestObject> mapper;
    
    @Test
    public void Test() {
        TestObject object = new TestObject();
        object.setId("oooid");
        object.setName("oooname");
        mapper.save(object);
    }
}


