package cn.com.pzg.sharding.jdbc.demo.config;

import cn.com.pzg.sharding.jdbc.demo.aspect.DynamicDataSourceAspect;
import org.apache.shardingsphere.driver.jdbc.adapter.AbstractDataSourceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2022 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 * 动态数据源配置：实例化 DynamicDataSourceRegister 和 DynamicDataSource
 *
 * @author pzg
 * @since 2022/11/28
 */
@Configuration
public class DynamicDataSourceConfig {
    @Lazy
    @Autowired
    private DataSource dataSource;

    @Bean
    public DynamicDataSourceRegister dynamicDataSourceRegister(){
        return new DynamicDataSourceRegister();
    }

    @Bean
    public DynamicDataSource dynamicDataSource(DynamicDataSourceRegister register){

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("sharding",dataSource);
        return new DynamicDataSource(register.getDefaultDataSource(),targetDataSources);
    }

    @Bean
    public DynamicDataSourceAspect dataSourceAspect(){
        return new DynamicDataSourceAspect();
    }

}
