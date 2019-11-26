package com.reyco.aop.core.processor;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.reyco.aop.core.handler.ProxyBeanHandler;
import com.reyco.aop.core.utils.ConfigurationUtil;

/**
 * 代理对象
 * @author reyco
 *
 */
public class CustomizedProxyInterceptor implements MethodInterceptor  {

	private List<ProxyBeanHandler> proxyBeanHandlerList;

	public CustomizedProxyInterceptor(List<ProxyBeanHandler> proxyBeanHandlerList) {
		this.proxyBeanHandlerList = proxyBeanHandlerList;
	}
	/**
	 * @param o          拦截类
	 * @param method     拦截方法
	 * @param objects 
	 */
	public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //处理前置及环绕前置通知
        for (ProxyBeanHandler proxyBeanHandler: proxyBeanHandlerList) {
            String annotationName = proxyBeanHandler.getAnnotationName();
            if (annotationName.equals(ConfigurationUtil.BEFORE)||annotationName.equals(ConfigurationUtil.AROUND)) {
                this.doProxy(proxyBeanHandler);
            }
        }
        Object result = null;
        try{
            result = methodProxy.invokeSuper(o,args);
        }catch (Exception e){
            System.out.println("get ex:"+e.getMessage());
            throw e;
        }
        //处理后置及环绕前置通知
        for (ProxyBeanHandler proxyBeanHandler: proxyBeanHandlerList) {
            String annotationName = proxyBeanHandler.getAnnotationName();
            if (annotationName.equals(ConfigurationUtil.AFTER)||annotationName.equals(ConfigurationUtil.AROUND))
                this.doProxy(proxyBeanHandler);
        }
        return result;
	}
	
	 /**
     * 处理代理操作
     * @param proxyBeanHolder
     */
    private void doProxy(ProxyBeanHandler proxyBeanHandler){
        String className = proxyBeanHandler.getClassName();
        String methodName = proxyBeanHandler.getMethodName();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
            Method[] methods = clazz.getMethods();
            for (Method poxyMethod:methods)
                if (poxyMethod.getName().equals(methodName)) {
                    poxyMethod.invoke(clazz.newInstance());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
