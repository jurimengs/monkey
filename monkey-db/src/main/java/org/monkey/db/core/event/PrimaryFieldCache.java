package org.monkey.db.core.event;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PrimaryFieldCache {
    private final static PrimaryFieldCache pfc = new PrimaryFieldCache();
    
    private Map<Class<?>, Field> map = new HashMap<>();
    
    public Field getField(Class<?> clazz) {
        return map.get(clazz);
    }
    
    public void setField(Class<?> clazz, Field f) {
        map.put(clazz, f);
    }

    public static PrimaryFieldCache getInstance() {
        return pfc;
    }
}
