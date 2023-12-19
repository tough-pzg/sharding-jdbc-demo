package cn.com.pzg.sharding.jdbc.demo.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * Copyright 2022 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 * 动态数据源 ： 重写determineCurrentLookupKey方法，决定当前使用的数据源是哪一个
 *
 * @author pzg
 * @since 2022/11/29
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(Object defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(defaultTargetDataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
       return DynamicDataSourceContextHolder.getDataSource();
    }


}