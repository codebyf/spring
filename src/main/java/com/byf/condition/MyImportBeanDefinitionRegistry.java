package com.byf.condition;

import com.byf.bean.Body;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar {
    /**
     * @param importingClassMetadata：当前类的注解信息
     * @param registry：BeanDefinition主策类
     *                 把所需要添加到容器的bean：调用
     *                BeanDefinitionRegistry.registryBeanDefinition手工注册进来
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean definition = registry.containsBeanDefinition("com.byf.bean.Heigh");
        boolean definition2 = registry.containsBeanDefinition("com.byf.bean.Weight");
        if (definition && definition2)
        {
            // 指定Bean定义信息：（Bean类型，Scope...）

            BeanDefinition beanDefinition = new RootBeanDefinition(Body.class);
            registry.registerBeanDefinition("body",beanDefinition);
        }
    }
}
