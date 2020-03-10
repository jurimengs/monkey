package com.org.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapTest {
    @Test
    public void map() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", "value");
        
        HashMap<String, Object> tmp = new HashMap<String, Object>();
        tmp.putAll(map);
        
        tmp.put("abc", "abc");
        
        map.put("ddd", "ddd");
        
        System.out.println(tmp.size());
        System.out.println(map.size());
    }
}
