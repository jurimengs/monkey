package com.common.utils;

import java.net.URL;
import java.util.Objects;

public class UrlResource {
    private final ClassLoader classLoader;
    private final URL url;

    public UrlResource(final ClassLoader classLoader, final URL url) {
        this.classLoader = classLoader;
        this.url = url;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UrlResource that = (UrlResource) o;

        if (classLoader != null ? !classLoader.equals(that.classLoader) : that.classLoader != null) {
            return false;
        }
        if (url != null ? !url.equals(that.url) : that.url != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(classLoader) + Objects.hashCode(url);
    }
}