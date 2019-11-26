package com.reyco.aop.core.processor;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import com.reyco.aop.core.handler.ProxyBeanHandler;
import com.reyco.aop.core.utils.ConfigurationUtil;

public class RegisterBeanFactoryPostProcessor implements BeanFactoryPostProcessor  {
	/**
     * 存放需要代理的相关信息类
     */
    public static volatile List<ProxyBeanHandler> proxyBeanHandlerList = new Vector<ProxyBeanHandler>();
    
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		 //获取所有的beanDefinitionName
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName:beanDefinitionNames){
            BeanDefinition beanDefinition= beanFactory.getBeanDefinition(beanDefinitionName);
            //判断beanDefinition是否是一个注解AnnotatedBeanDefinition
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                //取得beanDefinition上的所有注解
                AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();
                Set<String> Annotations = metadata.getAnnotationTypes();
                //循环所有注解，找到aop切面注解类
                for (String annotation:Annotations)
                    if (annotation.equals(ConfigurationUtil.AOP_POINTCUT_ANNOTATION)) {
                        doScan((GenericBeanDefinition)beanDefinition);
                    }
            }
        }
	}
	 /**
     * 扫描所有注解方法
     * @param beanDefinition
     */
    private void doScan(GenericBeanDefinition beanDefinition){
        try {
            String className = beanDefinition.getBeanClassName();
            Class<?> beanDefinitionClazz = Class.forName(className);
            Method[] methods = beanDefinitionClazz.getMethods();
            for (Method method :methods){
                Annotation[] annotations = method.getAnnotations();
                  for(Annotation annotation:annotations) {
                    String annotationName = annotation.annotationType().getName();
                    if(annotationName.equals(ConfigurationUtil.BEFORE)||annotationName.equals(ConfigurationUtil.AFTER)||
                            annotationName.equals(ConfigurationUtil.AROUND))
                        doScan(className,method,annotation);
                  }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描出所有被代理的类
     * @param className
     * @param method
     * @param annotation
     */
    private void doScan(String className,Method method,Annotation annotation){
        ProxyBeanHandler proxyBeanHandler = new ProxyBeanHandler();
        proxyBeanHandler.setClassName(className);
        proxyBeanHandler.setMethodName(method.getName());
        proxyBeanHandler.setAnnotationName(annotation.annotationType().getName());
        //获取注解上的所有方法
        Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
        String packagePath = null;
        for (Method annotationMethod:annotationMethods) {
            if (annotationMethod.getName().equals("value")){
                try {
                    packagePath = (String) annotationMethod.invoke(annotation, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!packagePath.isEmpty()){
            String rootPath = this.getClass().getResource("/").getPath();
            String targetPackagePath = rootPath + packagePath.replace(".","/");
            File file = new File(targetPackagePath);
            File[] fileList = file.listFiles();
            List<ProxyBeanHandler> proxyBeanHandlerList = null;
            for (File temp:fileList) {
                if (temp.isFile()) {//判断是否为文件
                    String targetClass = packagePath+"."+temp.getName().replace(".class","");
                    try {
                    	proxyBeanHandlerList = ConfigurationUtil.classzzProxyBeanHandler.get(targetClass);
                    }catch(Exception e){
                    }
                    if (proxyBeanHandlerList==null) {
                    	proxyBeanHandlerList = new Vector<ProxyBeanHandler>();
                    }
                    proxyBeanHandlerList.add(proxyBeanHandler);
                    ConfigurationUtil.classzzProxyBeanHandler.put(targetClass,proxyBeanHandlerList);
                }
            }

        }
    }
}
