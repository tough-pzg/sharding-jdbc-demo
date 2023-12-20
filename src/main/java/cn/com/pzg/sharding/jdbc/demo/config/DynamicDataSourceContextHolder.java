package cn.com.pzg.sharding.jdbc.demo.config;

import java.util.function.Supplier;

/**
 * Copyright 2022 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 * 使用 ThreadLocal 保存当前使用的数据源名称
 *
 * @author pzg
 * @since 2022/11/28
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    public static void setDataSource(String dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
        System.out.println("设置数据源为：" + dataSourceType);
    }

    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }

    public static void execute(String dataSourceType, Runnable runnable) {
        try {
            setDataSource(dataSourceType);
            runnable.run();
        } finally {
            clearDataSource();
        }
    }

    public static <T> T execute(String dataSourceType, Supplier<T> supplier) {
        try {
            setDataSource(dataSourceType);
            return supplier.get();
        } finally {
            clearDataSource();
        }
    }
}
