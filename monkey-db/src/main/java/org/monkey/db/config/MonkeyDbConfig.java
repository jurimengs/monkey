package org.monkey.db.config;

import org.monkey.db.core.executor.DefaultExecutor;
import org.monkey.db.core.executor.Executor;
import org.monkey.db.core.executor.MysqlExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonkeyDbConfig {
    @Bean
    @ConditionalOnProperty(name="monkey.executor", havingValue="mysql", matchIfMissing=false)
    public Executor mysqlExecutor() {
        return new MysqlExecutor();
    }
    
    @Bean
    @ConditionalOnMissingBean(Executor.class)
    public Executor defaultExecutor() {
        return new DefaultExecutor();
    }
}
