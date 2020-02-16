package com.monkey.server.sync.netty.works;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.com.framework.exceptions.TellUserMessageException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考 org.mybatis.spring.mapper.ClassPathMapperScanner
 * 
 * @author A37
 *
 */
@Slf4j
@Setter
public class NettyBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private BeanFactory beanFactory;
    
    private Class<? extends Annotation> annotationClass;

    private Class<?> markerInterface;

    private Class<? extends ServcieInterfaceProxy> nettyFactoryBeanClass = ServcieInterfaceProxy.class;

    public NettyBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilters() {

        boolean acceptAllInterfaces = true;

        // if specified, use the given annotation and / or marker interface
        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = false;
        }

        // override AssignableTypeFilter to ignore matches on the actual marker
        // interface
        if (this.markerInterface != null) {
            addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }

        // 扫描所有类
        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter((metadataReader, metadataReaderFactory) -> true);

            // 等于下面这个
            // addIncludeFilter(new TypeFilter() {
            // @Override
            // public boolean match(MetadataReader metadataReader, MetadataReaderFactory
            // metadataReaderFactory)
            // throws IOException {
            // return true;
            // }
            // });

        }
    }
    
    public void scanAndRegistByName(BeanDefinitionRegistry registry, String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if(CollectionUtils.isEmpty(beanDefinitionHolders)) {
            throw new TellUserMessageException("NettyScann注解配置的扫描目录下，未获取到需要自定义注入的类");
        }
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            ScannedGenericBeanDefinition definition = (ScannedGenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            
            Class<?> interfaceClass;
            try {
                interfaceClass = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                log.error("定义 netty 扫描类实例化异常", e);
                throw new TellUserMessageException(e.getMessage());
            }
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
            definition.setBeanClass(this.nettyFactoryBeanClass);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            
            definition.getPropertyValues().add("interfaceClass", interfaceClass);
            definition.getPropertyValues().add("beanFactory", beanFactory); // 把 beanFactory 注册给代理
            
            registry.registerBeanDefinition(beanClassName, definition);
        }
        
    }

    // 调用父类执行扫描
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        // addFilter();
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if (beanDefinitionHolders.isEmpty()) {
            System.err.println("No Interface Found!");
        } else {
            // 创建代理对象
            processBeanDefinitions(beanDefinitionHolders);
        }
        return beanDefinitionHolders;
    }

    // 只扫描顶级接口
    @Override
    public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return metadata.isInterface() && metadata.isIndependent();
    }

    /**
     * 为扫描到的接口创建代理对象
     * 
     * @param beanDefinitionHolders
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
            definition.setBeanClass(this.nettyFactoryBeanClass);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }

    }

    public void setMapperFactoryBeanClass(Class<? extends ServcieInterfaceProxy> nettyFactoryBeanClass) {
        this.nettyFactoryBeanClass = nettyFactoryBeanClass == null ? ServcieInterfaceProxy.class : nettyFactoryBeanClass;
    }
}