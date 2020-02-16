package com.monkey.server.sync.netty.works;

import java.util.Objects;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Proxy;

import lombok.Setter;

/**
 * 参考
 * 
 * @author A37
 *
 */
@Setter
public class ServcieInterfaceProxy implements FactoryBean<Object> {

    private Class<?> interfaceClass;
    private BeanFactory beanFactory; // NettyBeanDefinitionScanner 中通过  definition.getPropertyValues().add("beanFactory", beanFactory) 注册进来的

    public ServcieInterfaceProxy() {
    }

    public ServcieInterfaceProxy(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
    
    @Override
    public Object getObject() throws Exception {
        // 
        Objects.requireNonNull(interfaceClass);
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), 
                new Class[] {interfaceClass}, new ServcieInterfaceProxyHandler(beanFactory, interfaceClass));
//        return (T) Enhancer.create(interfaceClass,new DymicInvocationHandler());
    }
    
    @Override
    public Class<?> getObjectType() {
      return this.interfaceClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {
      return true;
    }

}
