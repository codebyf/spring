package com.byf.ext;

import com.byf.bean.Car;
import com.byf.bean.Red;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扩展原理：
 *      BeanPostProcessor： bean后置处理器，bean创建对象初始化前后进行拦截工作的
 *      BeanFactoryPostProcessor：beanFactory 的后置处理器
 *          在 beanFactory 标准初始化之后调用：所有的bean定义已经保存加载到 beanFactory
 * 1）ioc容器创建对象
 * 2）invokeBeanFactoryPostProcessors(beanFactory); 执行BeanFactoryPostProcessors
 *      如何找到所有BeanFactoryPostProcessors并执行他们的方法：
 *          1）直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
 *          2）在初始化创建其他组件前面执行
 */
@ComponentScan(value = "com.byf.ext")
@Configuration
public class ExtConfig {
    @Bean
    public Car car(){
        return new Car();
    }
}
