package org.monkey.db.core;

import org.monkey.db.face.connection.EventListener;
import org.monkey.db.face.connection.Executor;
import org.monkey.db.face.connection.Store;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfigration {
    @Bean
    public <K, T> Store<K, T> store() {
        return new HashStore<>();
    }
    
    @Bean
    public <T> EventListener eventListener() {
        EventListener eventListener = new NodeEventListener();
        return eventListener;
    }
    
    @Bean
    public <T> MonkeyMapper<T> monkeyMapper() {
        return new MonkeyMapper<>();
    }
}
