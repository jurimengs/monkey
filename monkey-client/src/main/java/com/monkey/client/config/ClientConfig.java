package com.monkey.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.monkey.client.MonkeyServerAdressProperties;
import com.monkey.netty.client.NettyClient;

@Configuration
public class ClientConfig {

    @Bean
    @ConditionalOnBean(MonkeyServerAdressProperties.class)
    public NettyClient nettyClient(MonkeyServerAdressProperties monkeyServerAdressProperties) {
        String host = monkeyServerAdressProperties.getIp();
        int port = monkeyServerAdressProperties.getPort();
        int timeOut = monkeyServerAdressProperties.getTimeOut();
        NettyClient nettyClient = new NettyClient(host , port, timeOut);
        return nettyClient;
    }
}
