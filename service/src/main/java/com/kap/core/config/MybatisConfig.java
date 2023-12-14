package com.kap.core.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <pre>
 * 마이바티스 Config 설정
 * </pre>
 *
 * @ClassName		    : MybatisConfig.java
 * @Description		: 마이바티스 Config 설정
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.01.10	  박주석	             최초 생성
 * </pre>
 */
@Configuration
@Slf4j
//JAVA 파일 SCAN 위치
@MapperScan(basePackages= {"com.kap.service.dao"}, sqlSessionFactoryRef = "SqlSessionFactory", sqlSessionTemplateRef = "SessionTemplate")
//트랜젝션 사용
@EnableTransactionManagement
public class MybatisConfig {

    @Value("${mybatis-config.config-location}")
    private String mybatisLocation;

    @Value("${mybatis-config.mapper-location}")
    private String mapperLocation;

    @Value("${spring.db.datasource.jndi-name}")
    private String jndiName;

    //DATABASE
    @Bean(name = "dataSource")
    @Profile("local")
    @ConfigurationProperties(prefix = "spring.db.datasource")
    public DataSource DataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * dataSource 생성
     * @return
     */
    //DATABASE
    @Bean(name = "dataSource")
    @Profile("!local")
    public DataSource jndiDataSource() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource(jndiName);
        return dataSource;
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dataSource") DataSource DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(DataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResources(mybatisLocation)[0]);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperLocation));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.kap.core.dto");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "SessionTemplate")
    public SqlSessionTemplate SqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory firstSqlSessionFactory) {
        return new SqlSessionTemplate(firstSqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource b2bDataSource) {
        return new DataSourceTransactionManager(b2bDataSource);
    }
}