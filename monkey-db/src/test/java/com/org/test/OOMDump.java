package com.org.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OOMDump {
    // -Xmx10M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d://
    static class OOMIntsmaze {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(ArrayList<OOMIntsmaze> list, int num) throws Exception {

        for (int i = 0; i < num; i++) {
            list.add(new OOMIntsmaze());
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws Exception {
        ArrayList<OOMIntsmaze> list = new ArrayList<OOMIntsmaze>();
        fillHeap(list,131);

        Map<String,OOMIntsmaze> map=new HashMap<String,OOMIntsmaze>();
        map.put("LIUYANG",new OOMIntsmaze());
        map.put("intsmaze",new OOMIntsmaze());
//        Thread.sleep(20000000);
    }
}
