package com.monkey.server;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.monkey.netty.server.BusinessExecutor;
import com.monkey.netty.server.NettyServer;

/**
 * 启动时初始化 NettyServer， 
 * @author zhouman
 *
 */
@SpringBootApplication(
        scanBasePackages = {"com.monkey.server", "org.monkey.db"}
        )
public class MonkeyServerApplication implements CommandLineRunner {
    @Value("${netty.port}")
    private int port;
    @Autowired
    private BeanFactory beanFactory;

	public static void main(String[] args) {
		SpringApplication.run(MonkeyServerApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        NettyServer nettyServer = new NettyServer();
        nettyServer.setPort(port);
        
        BusinessExecutor businessExecutor = new BusinessExecutor();
        businessExecutor.setBeanFactory(beanFactory);
        
        nettyServer.setBusinessExecutor(businessExecutor);
        nettyServer.bind();
    }

}
