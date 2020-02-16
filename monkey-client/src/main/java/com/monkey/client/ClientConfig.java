package com.monkey.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.monkey.netty.client.NettyClient;

@Configuration
public class ClientConfig {
    @Autowired
    private MonkeyServerAdressProperties monkeyServerAdressProperties;

    @Bean
    @ConditionalOnBean(MonkeyServerAdressProperties.class)
    public NettyClient nettyClient() {
        String host = monkeyServerAdressProperties.getIp();
        int port = monkeyServerAdressProperties.getPort();
        int timeOut = monkeyServerAdressProperties.getTimeOut();
        NettyClient nettyClient = new NettyClient(host , port, timeOut);
        return nettyClient;
    }
}
