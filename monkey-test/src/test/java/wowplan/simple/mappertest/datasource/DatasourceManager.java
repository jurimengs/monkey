package wowplan.simple.mappertest.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.FileUrlResource;

public class DatasourceManager {
    protected String database = "database";
    protected String dbuser = "root";
    protected String dbpwd = "zhouman123";
    protected String dburl = "jdbc:mysql://localhost:3306/"+ database +"?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";

    protected SqlSessionFactory getSqlSessionFactory(FileUrlResource[] mapperXmlResources) throws Exception {
        DruidDataSource dataSource = getDruidDataSource();

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.ruoyi.project"); // typeAliasesPackage: com.ruoyi.**.domain
//        sqlSessionFactoryBean.setMapperLocations(new ClassPathResource("classpath*:mapper/**/*Mapper.xml"));
        sqlSessionFactoryBean.setMapperLocations(
                mapperXmlResources
        );

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }




    protected DruidDataSource getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dburl);
        dataSource.setUsername(dbuser);
        dataSource.setPassword(dbpwd);
//        dataSource.setMaxActive(1);
//        dataSource.setMaxWait(1);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setValidationQuery("SELECT 1");
//        dataSource.setTestOnBorrow(true);
//        dataSource.setTestWhileIdle(true);
        return dataSource;
    }
}
