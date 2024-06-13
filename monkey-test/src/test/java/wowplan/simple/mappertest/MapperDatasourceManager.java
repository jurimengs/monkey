package wowplan.simple.mappertest;

import com.memory.entity.BusinessAsyncTask;
import com.memory.mapper.BusinessAsyncTaskMapper;
import org.junit.Before;
import org.junit.Test;
import wowplan.simple.mappertest.context.BeanContext;
import wowplan.simple.mappertest.datasource.DatasourceManager;

import java.util.Arrays;
import java.util.List;

public class MapperDatasourceManager extends DatasourceManager {
//    protected String mapperPrefixPath = "/Users/manzhou/Desktop/code/gitee/plusham-data/pr-estate/src/main/resources/mapper";

    protected String mapperPrefixPath = "/Users/manzhou/Desktop/code/gitee/wowplan/ruoyi/src/main/resources/mybatis";

    private BeanContext beanContext;
    Long operatorId = 1L;

    @Before
    public void init() throws Exception {
        List<String> mapperXmlPath = Arrays.asList(
                mapperPrefixPath + "/business/asynctask/BusinessAsyncTaskMapper.xml"
        );
        beanContext = new BeanContext()
                .sqlSession(mapperXmlPath)
                .initMappers(BusinessAsyncTaskMapper.class)
//                .initServices(new BusinessAsyncTaskServiceImpl())
        ;

    }

    @Test
    public void test_insert() {
        BusinessAsyncTaskMapper mapper = beanContext.getBean(BusinessAsyncTaskMapper.class);
        BusinessAsyncTask businessAsyncTask = new BusinessAsyncTask();
        mapper.insert(businessAsyncTask);
    }

//    @Test
//    public void test_selectBusinessAsyncTaskById() {
//        IBusinessAsyncTaskService businessAsyncTaskService = beanContext.getBean(IBusinessAsyncTaskService.class);
//        BusinessAsyncTask businessAsyncTask = businessAsyncTaskService.selectBusinessAsyncTaskById(1L);
//        System.out.println("" + JSON.toJSONString(businessAsyncTask));
//    }

//    @Test
//    public void test_insert() {
//        IBusinessAsyncTaskService businessAsyncTaskService = beanContext.getBean(IBusinessAsyncTaskService.class);
//        AddBusinessAsyncTaskRequest entity = new AddBusinessAsyncTaskRequest();
//        entity.setStatus(1);
//        entity.setTaskName("111");
//        entity.setRemark("remark");
//        businessAsyncTaskService.insertBusinessAsyncTask(entity, 1L, 1L);
//    }

}
