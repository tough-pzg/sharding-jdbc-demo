package cn.com.pzg.sharding.jdbc.demo.config;

import cn.com.pzg.sharding.jdbc.demo.bean.HikariCpConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Copyright 2022 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 * 动态数据源注册器
 *
 * @author pzg
 * @since 2022/11/28
 */
public class DynamicDataSourceRegister implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    /**
     * 默认数据源（主数据源）
     */
    private DataSource defaultDataSource;
    /**
     * 自定义数据源
     */
    private final Map<Object, Object> customDataSources = new HashMap<>();

    /**
     * HikariCpConfig 配置合并器
     */
    private static final ConfigMergeCreator<HikariCpConfig, HikariConfig> MERGE_CREATOR = new ConfigMergeCreator<>("HikariCp", HikariCpConfig.class, HikariConfig.class);

    /**
     * 默认的Hikari连接池全局配置
     */
    private static final HikariCpConfig GLOBAL_HIKARI_CP_CONFIG = new HikariCpConfig();

    public static final String COMMA = ",";

    static {
        GLOBAL_HIKARI_CP_CONFIG.setMaximumPoolSize(10);
        GLOBAL_HIKARI_CP_CONFIG.setIdleTimeout(600000L);
        GLOBAL_HIKARI_CP_CONFIG.setAutoCommit(true);
        GLOBAL_HIKARI_CP_CONFIG.setMaxLifetime(1800000L);
        GLOBAL_HIKARI_CP_CONFIG.setConnectionTimeout(30000L);
    }


    /**
     * 凡注册到Spring容器内的bean，实现了EnvironmentAware接口重写setEnvironment方法后，在工程启动时可以获得application.properties的配置文件配置的属性值
     *
     * @param environment 环境
     */
    @Override
    public void setEnvironment(Environment environment) {
        initDefaultDataSource(environment);
    }

    public DataSource getDefaultDataSource() {
        return defaultDataSource;
    }

    public Map<Object, Object> getCustomDataSources() {
        return customDataSources;
    }

    /**
     * 初始化默认数据源
     *
     * @param env
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源配置
        Map<String, Object> dataSourceMap = new HashMap<>(8);

        dataSourceMap.put("driver-class-name", env.getProperty("spring.datasource.driver-class-name"));
        dataSourceMap.put("url", env.getProperty("spring.datasource.url"));
        dataSourceMap.put("username", env.getProperty("spring.datasource.username"));
        dataSourceMap.put("password", env.getProperty("spring.datasource.password"));

        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(env);
        //hikari 连接池 配置信息
        Binder binder = new Binder(sources);
        BindResult<HikariCpConfig> bindResult = binder.bind("spring.datasource.hikari", HikariCpConfig.class);
        HikariCpConfig hikariCpConfig;
        try {
            hikariCpConfig = bindResult.get();
        } catch (Exception e) {
            hikariCpConfig = new HikariCpConfig();
        }
        defaultDataSource = buildDataSource("default", dataSourceMap, hikariCpConfig);
    }


    /**
     * 创建数据源
     *
     * @param dataSourceName 数据源名称
     * @param dsMap          数据源属性map
     * @return
     */
    private DataSource buildDataSource(String dataSourceName, Map<String, Object> dsMap, HikariCpConfig hikariCpConfig) {
        try {
            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            //限制，只使用 Hikari 数据源连接池
            HikariDataSource dataSource = new HikariDataSource();
            //合并连接池配置
            HikariConfig hikariConfig = MERGE_CREATOR.create(GLOBAL_HIKARI_CP_CONFIG, hikariCpConfig);
            //设置数据源属性
            hikariConfig.setDriverClassName(driverClassName);
            hikariConfig.setJdbcUrl(url);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);

            //连接池属性 复制到 数据源实例上
            hikariConfig.copyStateTo(dataSource);
            log.info("创建[{}]数据源,url:{}", dataSourceName, url);
            return dataSource;
        } catch (Exception e) {
            log.error("使用配置文件，创建数据源时出现异常", e);
        }
        return null;
    }

}
