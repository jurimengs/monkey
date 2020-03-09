package com.org.test;

import java.util.ServiceLoader;

import com.mysql.jdbc.Connection;

public class ServiceLoaderTest {
    
    
    public static void main(String[] args) {
        ServiceLoader<Connection> shouts = ServiceLoader.load(Connection.class);
        for (Connection s : shouts) {
        }
        
    }
}
