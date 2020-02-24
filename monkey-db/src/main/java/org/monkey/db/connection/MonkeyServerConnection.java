//package org.monkey.db.connection;
//
//import java.lang.reflect.Field;
//import java.util.List;
//
//import org.monkey.db.core.PrimaryFieldCache;
//import org.monkey.db.core.store.Store;
//import org.monkey.db.core.store.StoreFactory;
//import org.monkey.db.exception.BeanException;
//import org.monkey.db.face.annotation.Id;
//import org.monkey.db.face.connection.Connection;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.stereotype.Component;
//
//import com.baomidou.mybatisplus.annotation.TableId;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component("connection")
//@ConditionalOnMissingBean(DataInConnection.class)
//public class MonkeyServerConnection {
//    private final Store<String> store = StoreFactory.getInstance().createStore();
//    private PrimaryFieldCache cache = PrimaryFieldCache.getInstance();
//    
//    
//    public void update(Object object) {
//        if(object == null) {
//            throw new RuntimeException("fooldb: null can not be updated");
//        }
//        // 首先要找到主键 Field
//        Class<?> clazz = object.getClass();
//        Field primaryKey = getPrimaryField(clazz);
//        try {
//            Object primaryKeyValue = primaryKey.get(object);
//            if(primaryKeyValue == null) {
//                throw new BeanException("fooldb: primaryKeyValue can not be null");
//            }
//            // 
//            String key = primaryKeyValue.toString();
//            store.put(key, object);
//        } catch (IllegalArgumentException | IllegalAccessException e) {
//            log.error("fooldb: can not get primaryKeyValue", e);
//            throw new BeanException("fooldb: can not get primaryKeyValue");
//        }
//    }
//
//    
//    public void save(Object object) {
//        if(object == null) {
//            throw new RuntimeException("fooldb: null can not be saved");
//        }
//        // 首先要找到主键 Field
//        Class<?> clazz = object.getClass();
//        Field primaryKey = getPrimaryField(clazz);
//        try {
//            Object primaryKeyValue = primaryKey.get(object);
//            if(primaryKeyValue == null) {
//                throw new BeanException("fooldb: primaryKeyValue can not be null");
//            }
//            // 
//            String key = primaryKeyValue.toString();
//            store.put(key, object);
//        } catch (IllegalArgumentException | IllegalAccessException e) {
//            log.error("fooldb: can not get primaryKeyValue", e);
//            throw new BeanException("fooldb: can not get primaryKeyValue");
//        }
//    
//        
//    }
//
//    
//    public void delete(Object object) {
//        if(object == null) {
//            return;
//        }
//        // 首先要找到主键 Field
//        Class<?> clazz = object.getClass();
//        Field primaryKey = getPrimaryField(clazz);
//        try {
//            Object primaryKeyValue = primaryKey.get(object);
//            if(primaryKeyValue == null) {
//                throw new BeanException("fooldb: primaryKeyValue can not be null");
//            }
//            // 
//            String key = primaryKeyValue.toString();
//            store.remove(key);
//        } catch (IllegalArgumentException | IllegalAccessException e) {
//            log.error("fooldb: can not get primaryKeyValue", e);
//            throw new BeanException("fooldb: can not get primaryKeyValue");
//        }
//    }
//
//    
//    public Object select(Object object) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    
//    public List<Object> selectList(Object object) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    private Field getPrimaryField(Class<?> clazz) {
//        Field fieldRes = cache.getField(clazz);
//        if(fieldRes == null) {
//            Field[] fields = clazz.getDeclaredFields();
//            for (Field field : fields) {
//                if(field.getAnnotation(TableId.class) != null || 
//                        field.getAnnotation(Id.class) != null) {
//                    field.setAccessible(Boolean.TRUE);
//                    cache.setField(clazz, field);
//                    return field;
//                }
//            }
//        }
//        throw new BeanException("fooldb:buddy, show me one field with annotation org.service.fooldb.face.annotation.Id");
//    }
//}
