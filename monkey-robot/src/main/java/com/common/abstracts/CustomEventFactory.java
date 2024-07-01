package com.common.abstracts;

// 事件工厂能不能抽象出来？
public interface CustomEventFactory<T> {
    CustomEvent apply();
    CustomEvent apply(T config);
    public String getName();
}
