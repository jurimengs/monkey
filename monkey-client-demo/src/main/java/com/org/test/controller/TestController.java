package com.org.test.controller;

import org.monkey.db.face.connection.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("/test")
public class TestController {
    @Autowired
    private Connection conn;  // TODO 要 lazy ？ conotroller 加载在 configuationg 之后？
    
    @RequestMapping(value="/testSave", method=RequestMethod.POST)
    public void testSave() {
        TestEntity ent = new TestEntity();
        ent.setId("111");
        ent.setName("name");
        conn.save(ent);
        log.info("xxxxxxx");
    }
}
