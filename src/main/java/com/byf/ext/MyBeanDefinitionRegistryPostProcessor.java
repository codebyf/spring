package com.byf.ext;

import com.byf.bean.Red;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry...bean的数量：" + registry.getBeanDefinitionCount());
        // RootBeanDefinition beanDefinition = new RootBeanDefinition(Red.class);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Red.class).getBeanDefinition();
        registry.registerBeanDefinition("hello",beanDefinition);
    }
    // BeanDefinitionRegistry Bean定义信息的保存中心 ，
    // 以后BeanFactory 就是按照BeanDefinitionRegistry里面保存的每一个bean的定义信息创建Bean
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor...bean的数量："+beanFactory.getBeanDefinitionCount());
    }
}
