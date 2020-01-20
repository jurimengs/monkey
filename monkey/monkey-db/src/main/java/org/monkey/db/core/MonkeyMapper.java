package org.monkey.db.core;

import java.lang.reflect.Field;
import java.util.List;

import org.monkey.db.exception.BeanException;
import org.monkey.db.face.annotation.Id;
import org.monkey.db.face.connection.Mapper;
import org.monkey.db.face.connection.Store;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unchecked")
public class MonkeyMapper<T> implements Mapper<T> {
    private final Store<String, T> store = StoreFactory.getInstance().createStore();
    private PrimaryFieldCache cache = PrimaryFieldCache.getInstance();
    
    @Override
    public void update(T object) {
        if(object == null) {
            throw new RuntimeException("fooldb: null can not be updated");
        }
        // 首先要找到主键 Field
        Class<T> clazz = (Class<T>) object.getClass();
        Field primaryKey = getPrimaryField(clazz);
        try {
            Object primaryKeyValue = primaryKey.get(object);
            if(primaryKeyValue == null) {
                throw new BeanException("fooldb: primaryKeyValue can not be null");
            }
            // 
            String key = primaryKeyValue.toString();
            store.put(key, object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("fooldb: can not get primaryKeyValue", e);
            throw new BeanException("fooldb: can not get primaryKeyValue");
        }
    }

    @Override
    public void save(T object) {
        if(object == null) {
            throw new RuntimeException("fooldb: null can not be saved");
        }
        // 首先要找到主键 Field
        Class<T> clazz = (Class<T>) object.getClass();
        Field primaryKey = getPrimaryField(clazz);
        try {
            Object primaryKeyValue = primaryKey.get(object);
            if(primaryKeyValue == null) {
                throw new BeanException("fooldb: primaryKeyValue can not be null");
            }
            // 
            String key = primaryKeyValue.toString();
            store.put(key, object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("fooldb: can not get primaryKeyValue", e);
            throw new BeanException("fooldb: can not get primaryKeyValue");
        }
    
        
    }

    @Override
    public void delete(T object) {
        if(object == null) {
            return;
        }
        // 首先要找到主键 Field
        Class<T> clazz = (Class<T>) object.getClass();
        Field primaryKey = getPrimaryField(clazz);
        try {
            Object primaryKeyValue = primaryKey.get(object);
            if(primaryKeyValue == null) {
                throw new BeanException("fooldb: primaryKeyValue can not be null");
            }
            // 
            String key = primaryKeyValue.toString();
            store.remove(key);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("fooldb: can not get primaryKeyValue", e);
            throw new BeanException("fooldb: can not get primaryKeyValue");
        }
    }

    @Override
    public T select(T object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<T> selectList(T object) {
        // TODO Auto-generated method stub
        return null;
    }

    private Field getPrimaryField(Class<T> clazz) {
        Field fieldRes = cache.getField(clazz);
        if(fieldRes == null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if(field.getAnnotation(TableId.class) != null || 
                        field.getAnnotation(Id.class) != null) {
                    field.setAccessible(Boolean.TRUE);
                    cache.setField(clazz, field);
                    return field;
                }
            }
        }
        throw new BeanException("fooldb:buddy, show me one field with annotation org.service.fooldb.face.annotation.Id");
    }
}
