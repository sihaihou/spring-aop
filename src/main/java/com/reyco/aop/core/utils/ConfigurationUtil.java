package com.reyco.aop.core.utils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.reyco.aop.core.handler.ProxyBeanHandler;

/**
 * 描述工具类
 * @author aop的描述
 *
 */
public class ConfigurationUtil {
	/**
     * aop标识注解类
     */
    public static final String AOP_POINTCUT_ANNOTATION = "com.reyco.aop.core.annotation.MyAspect";
    /**
     * 前置通知注解类
     */
    public static final String BEFORE = "com.reyco.aop.core.annotation.MyBefore";
    /**
     * 后置通知注解类
     */
    public static final String AFTER = "com.reyco.aop.core.annotation.MyAfter";
    /**
     * 环绕通知注解类
     */
    public static final String AROUND = "com.reyco.aop.core.annotation.MyAround";
    /**
     * 存放需代理的全部目标对象类
     */
    public static volatile ConcurrentMap<String,List<ProxyBeanHandler>> classzzProxyBeanHandler = new ConcurrentHashMap<String, List<ProxyBeanHandler>>();
}
