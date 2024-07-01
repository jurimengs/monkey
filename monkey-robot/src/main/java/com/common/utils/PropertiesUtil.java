package com.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;

/**
 * 参考 org.apache.logging.log4j.util.PropertyFilePropertySource.loadPropertiesFile
 * version=2.12.1
 * groupId=org.apache.logging.log4j
 * artifactId=log4j-api
 */
@Slf4j
public class PropertiesUtil {

    public static Properties loadPropertiesFile(final String fileName) {
        final Properties props = new Properties();
        for (final URL url : findResources(fileName)) {
            InputStream in = null;
            try {
                in = url.openStream();
                props.load(in);
            } catch (final IOException e) {
                log.error("读配置文件失败", e);
            } finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return props;
    }

    public static Collection<URL> findResources(final String resource) {
        final Collection<UrlResource> urlResources = findUrlResources(resource);
        final Collection<URL> resources = new LinkedHashSet<>(urlResources.size());
        for (final UrlResource urlResource : urlResources) {
            resources.add(urlResource.getUrl());
        }
        return resources;
    }

    public static Collection<UrlResource> findUrlResources(final String resource) {
        // @formatter:off
        final ClassLoader[] candidates = {
                PropertiesUtil.class.getClassLoader(),
                ClassLoader.getSystemClassLoader()};
        // @formatter:on
        final Collection<UrlResource> resources = new LinkedHashSet<>();
        for (final ClassLoader cl : candidates) {
            if (cl != null) {
                try {
                    final Enumeration<URL> resourceEnum = cl.getResources(resource);
                    while (resourceEnum.hasMoreElements()) {
                        resources.add(new UrlResource(cl, resourceEnum.nextElement()));
                    }
                } catch (final IOException e) {
                    log.error("搜寻配置文件路径异常", e);
                }
            }
        }
        return resources;
    }

}
