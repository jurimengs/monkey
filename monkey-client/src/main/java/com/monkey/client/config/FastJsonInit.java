package com.monkey.client.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.parser.ParserConfig;

@Configuration
public class FastJsonInit {
    @PostConstruct
    public void init() {
        // 开启fastjson 序列化支持
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
}
