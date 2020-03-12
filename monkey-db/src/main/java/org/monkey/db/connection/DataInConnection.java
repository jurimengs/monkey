package org.monkey.db.connection;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.monkey.db.core.executor.Executor;
import org.monkey.db.core.store.Store;
import org.monkey.db.core.store.Tabels;
import org.monkey.db.face.connection.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monkey.base.DataModelIn;
import com.monkey.base.KeyValue;
import com.monkey.constants.CommonConstants;
import com.monkey.exceptions.DataException;
import com.monkey.exceptions.ModelAnnotationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(CommonConstants.BEAN_NAME_SERVER_CONNECTION)
public class DataInConnection implements Connection {
    
    @Autowired
    private Executor executor;

    @Override
    public void save(DataModelIn object) throws DataException {
        if(object == null) {
            log.error("monkey: null can not be updated");
            throw new RuntimeException("monkey: null can not be updated");
        }
        String tableName = object.getTableName();
        
        String keyFieldName = object.getPrimaryKey();
        if(StringUtils.isEmpty(keyFieldName)) {
            throw new ModelAnnotationException("monkey: object need an PromaryKey");
        }
        
        Store store = Tabels.getInstance().getStore(tableName, executor);
        List<KeyValue> datas = object.getDatas();
        store.add(keyFieldName, datas);
    }

    @Override
    public void update(DataModelIn object) {
        if(object == null) {
            throw new RuntimeException("monkey: null can not be updated");
        }
        String tableName = object.getTableName();
        
        String keyFieldName = object.getPrimaryKey();
        if(StringUtils.isEmpty(keyFieldName)) {
            throw new ModelAnnotationException("monkey: object need an PromaryKey");
        }
        
        Store store = Tabels.getInstance().getStore(tableName, executor);
        List<KeyValue> datas = object.getDatas();
        store.update(keyFieldName, datas);
        
    }
    
    @Override
    public void delete(DataModelIn object) {
        
    }

    @Override
    public Object select(DataModelIn object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Object> selectList(DataModelIn object) {
        // TODO Auto-generated method stub
        return null;
    }

//    private Field getPrimaryField(Class<?> clazz) {
//        Field fieldRes = cache.getField(clazz);
//        if(fieldRes == null) {
//            Field[] fields = clazz.getDeclaredFields();
//            for (Field field : fields) {
//                if(field.getAnnotation(TableId.class) != null || 
//                        field.getAnnotation(PrimaryKey.class) != null) {
//                    field.setAccessible(Boolean.TRUE);
//                    cache.setField(clazz, field);
//                    return field;
//                }
//            }
//        }
//        throw new BeanException("monkey:buddy, show me one field with annotation org.service.monkey.face.annotation.Id");
//    }
//    private PrimaryFieldCache cache = PrimaryFieldCache.getInstance();
}
