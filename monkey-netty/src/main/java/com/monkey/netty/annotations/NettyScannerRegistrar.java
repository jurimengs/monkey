package com.monkey.netty.annotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.monkey.netty.works.NettyBeanDefinitionScanner;
import com.monkey.netty.works.ServcieInterfaceProxy;

/**
 * 通过注解的方式完成Bean扫描，可代替 NettyBeanDefinitionRegistryPostProcessor ？ 
 * 参考 org.mybatis.spring.annotation.MapperScannerRegistrar
 * @author A37
 *
 */
public class NettyScannerRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private BeanFactory beanFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(NettyScan.class.getName()));
        if (mapperScanAttrs != null) {
            registerBeanDefinitions(mapperScanAttrs, registry);
        }
    }

    void registerBeanDefinitions(AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry) {

        NettyBeanDefinitionScanner scanner = new NettyBeanDefinitionScanner(registry);

        // this check is needed in Spring 3.1
        Optional.ofNullable(resourceLoader).ifPresent(scanner::setResourceLoader);

        // 利用 NettyProxy 注解标识要生成代理的类，没有NettyProxy注解的则不生成代理
        scanner.setAnnotationClass(NettyProxy.class);
        
        Class<?> markerInterface = annoAttrs.getClass("markerInterface");
        if (!Class.class.equals(markerInterface)) {
            scanner.setMarkerInterface(markerInterface);
        }

        Class<? extends BeanNameGenerator> generatorClass = annoAttrs.getClass("nameGenerator");
        if (!BeanNameGenerator.class.equals(generatorClass)) {
            scanner.setBeanNameGenerator(BeanUtils.instantiateClass(generatorClass));
        }

        Class<? extends ServcieInterfaceProxy> mapperFactoryBeanClass = annoAttrs.getClass("factoryBean");
        if (!ServcieInterfaceProxy.class.equals(mapperFactoryBeanClass)) {
            // 如果没有，scanner 会默认 NettyFactoryBean
            scanner.setMapperFactoryBeanClass(mapperFactoryBeanClass);
        }

        List<String> basePackages = new ArrayList<>();
        basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("value")).filter(StringUtils::hasText)
                .collect(Collectors.toList()));

        basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText)
                .collect(Collectors.toList()));

        basePackages.addAll(Arrays.stream(annoAttrs.getClassArray("basePackageClasses")).map(ClassUtils::getPackageName)
                .collect(Collectors.toList()));

        scanner.setBeanFactory(beanFactory);
        scanner.setResourceLoader(this.resourceLoader);
        scanner.registerFilters();
        scanner.scanAndRegistByName(registry, StringUtils.toStringArray(basePackages));
        
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    // static class RepeatingRegistrar extends MapperScannerRegistrar {
    // /**
    // * {@inheritDoc}
    // */
    // @Override
    // public void registerBeanDefinitions(AnnotationMetadata
    // importingClassMetadata,
    // BeanDefinitionRegistry registry) {
    // AnnotationAttributes mapperScansAttrs = AnnotationAttributes
    // .fromMap(importingClassMetadata.getAnnotationAttributes(MapperScans.class.getName()));
    // if (mapperScansAttrs != null) {
    // Arrays.stream(mapperScansAttrs.getAnnotationArray("value"))
    // .forEach(mapperScanAttrs -> registerBeanDefinitions(mapperScanAttrs,
    // registry));
    // }
    // }
    // }

}
