package com.monkey.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "monkey.server.address")
@ConditionalOnProperty(name="monkey.on", havingValue="true", matchIfMissing=false)
public class MonkeyServerAdressProperties {
    private String ip;
    private int port;
    private int timeOut=1000;
}
