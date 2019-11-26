package com.reyco.aop.core.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.reyco.aop.core.processor.RealizedAopBeanPostProcessor;
import com.reyco.aop.core.processor.RegisterBeanFactoryPostProcessor;

/**
 * 描述:
 * 自定义aop实现，提交给spring处理的类
 *
 * @author reyco
 */
public class CustomizedAopImportSelector implements ImportSelector {

    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{RealizedAopBeanPostProcessor.class.getName(),RegisterBeanFactoryPostProcessor.class.getName()};
    }
}
