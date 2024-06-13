package wowplan.simple.mappertest.context;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.FileUrlResource;
import wowplan.simple.mappertest.datasource.DatasourceManager;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanContext extends DatasourceManager {

    private Map<String, Object> beanMap = new HashMap<>();

    private List<UnInitializedBean> unIinitialized = new ArrayList<>();


    private SqlSession sqlSession;


    public BeanContext sqlSession(List<String> mapperXmlPath) throws Exception {
        FileUrlResource[] mapperXmlResources = mapperXmlPath.stream().map(xml -> {
            try {
                FileUrlResource fileUrlResource = new FileUrlResource(xml);
                return fileUrlResource;
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }).toArray(FileUrlResource[]::new);

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mapperXmlResources);
        sqlSession = sqlSessionFactory.openSession();

        return this;
    }

    public BeanContext initMappers(Class<?>... mapperClasses) throws Exception {
        for (Class<?> mapperClass : mapperClasses) {
            this.putBean(sqlSession.getMapper(mapperClass));
        }
        return this;
    }

    public BeanContext initServices(Object... objects) throws NoSuchFieldException, IllegalAccessException {
        for (Object object : objects) {
            // 找属性
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldTypeClassName = field.getType().getName();
                Object fieldObject = this.getBean(fieldTypeClassName);
                if(fieldObject != null) {
                    setField(object, field.getName(), fieldObject);
                } else {
                    // 如果没有的属性先记录下来等会再弄
                    UnInitializedBean unInitialized = new UnInitializedBean();
                    unInitialized.setObject(object);
                    unInitialized.setField(field);
                    unIinitialized.add(unInitialized);
                }
            }

            this.putBean(object);
        }
        this.reAssignField();
        return this;
    }

    public void reAssignField() throws NoSuchFieldException, IllegalAccessException {
        if(unIinitialized != null && unIinitialized.size() > 0) {
            for (UnInitializedBean unInitializedBean : unIinitialized) {
                Object object = unInitializedBean.getObject();
                Field field = unInitializedBean.getField();
                String fieldTypeClassName = field.getType().getName();

                Object fieldObject = this.getBean(fieldTypeClassName);
                if(fieldObject != null) {
                    this.setField(object, field.getName(), fieldObject);
                }
            }
        }
    }



    protected void setField(Object aimObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = aimObject.getClass();
        // 获取私有属性name的Field对象
        Field field = clazz.getDeclaredField(fieldName);
        // 设置Field对象的访问权限为可访问
        field.setAccessible(true);
        field.set(aimObject, value);
    }

    protected void putBean(Object bean) {

        String className = bean.getClass().getGenericInterfaces()[0].getTypeName();
        boolean exist = beanMap.containsKey(className);
        if(exist) {
            throw new RuntimeException("已存在实例: " + className);
        }
        beanMap.put(className, bean);
//        Class<?>[] interfaces = bean.getClass().getInterfaces();
//        if(interfaces != null && interfaces.length > 0) {
//            Arrays.stream(interfaces).forEach(single -> {
//                beanMap.put(single.getClass().getName(), bean);
//            });
//        }
        
    }

    public Object getBean(String className) {
        return beanMap.get(className);
    }

    public <T> T getBean(Class<T> clazz) {
        return (T) beanMap.get(clazz.getName());
    }
}
