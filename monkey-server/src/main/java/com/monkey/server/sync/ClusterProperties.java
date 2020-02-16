package com.monkey.server.sync;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import lombok.Data;

@Data
@ConditionalOnProperty(prefix = "monkey.cluster", matchIfMissing = false)
public class ClusterProperties {
    private List<String> addresses;
    private Integer heartbeat;
    private Integer persistFirstTimeAfter;
    private Integer persistFrequency; // 单位 秒 
}
