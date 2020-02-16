package com.monkey.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import lombok.Data;

@Data
@ConditionalOnProperty(prefix = "monkey.server.address", matchIfMissing = false)
public class MonkeyServerAdressProperties {
    private String ip;
    private int port;
    private int timeOut;
}
