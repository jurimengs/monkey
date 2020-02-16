package com.org.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monkey.client.Connection;
import com.org.test.TestEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("/test")
public class TestController {
    @Autowired
    private Connection conn;
    
    @RequestMapping("/testSave")
    public void testSave() {
        TestEntity ent = new TestEntity();
        ent.setId("111");
        ent.setName("name");
        conn.save(ent);
        log.info("xxxxxxx");
    }
}
