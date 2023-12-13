package cn.com.pzg.sharding.jdbc.demo.util;

import org.apache.shardingsphere.sharding.algorithm.keygen.SnowflakeKeyGenerateAlgorithm;

import java.util.Properties;

/**
 * Copyright 2023 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 *
 * @author pzg
 * @description
 * @since 2023/12/13
 */
public class SnowFlakeUtils {

    public static final SnowflakeKeyGenerateAlgorithm KEY_GENERATOR = new SnowflakeKeyGenerateAlgorithm();

    static {
        Properties props = new Properties();
        props.setProperty("workerId","1024");
        props.setProperty("type","SNOWFLAKE");
        KEY_GENERATOR.init(props);
    }

    public static Long generateKey(){
        return KEY_GENERATOR.generateKey();
    }

}
