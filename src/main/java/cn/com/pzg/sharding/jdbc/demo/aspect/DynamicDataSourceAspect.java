package cn.com.pzg.sharding.jdbc.demo.aspect;

import cn.com.pzg.sharding.jdbc.demo.annotation.UseDataSource;
import cn.com.pzg.sharding.jdbc.demo.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * Copyright 2022 Shanghai Yejia Digital Technology Co.,Ltd. All rights reserved.
 * 拦截 被UseDataSource注释的类或方法，并且依据就近原则生效
 *
 * @author pzg
 * @since 2022/12/01
 */
@Order(1)
@Aspect
public class DynamicDataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAspect.class);


    @Pointcut("@within(cn.com.pzg.sharding.jdbc.demo.annotation.UseDataSource)")
    public void classPointCut() {

    }

    @Pointcut("@annotation(cn.com.pzg.sharding.jdbc.demo.annotation.UseDataSource)")
    public void methodPointCut() {
    }


    @Around("classPointCut()")
    public Object classAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.isAnnotationPresent(UseDataSource.class) && !method.isAnnotationPresent(UseDataSource.class)) {
            UseDataSource useDataSource = declaringClass.getAnnotation(UseDataSource.class);
            DynamicDataSourceContextHolder.setDataSource(useDataSource.value());
        }

        return proceed(point);
    }

    @Around("methodPointCut()")
    public Object methodAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        if (method.isAnnotationPresent(UseDataSource.class)) {
            UseDataSource useDataSource = method.getAnnotation(UseDataSource.class);
            DynamicDataSourceContextHolder.setDataSource(useDataSource.value());
        }

        return proceed(point);
    }

    private Object proceed(ProceedingJoinPoint point) throws Throwable{
        try {
            return point.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSource();
        }
    }

}
