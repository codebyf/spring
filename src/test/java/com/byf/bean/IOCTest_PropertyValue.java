package com.byf.bean;

import com.byf.config.MyConfigOfLifeCycle;
import com.byf.config.MyConfigOfPropertieyValue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_PropertyValue {
    // 1、创建IOC容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfPropertieyValue.class);

    @Test
    public void test(){
        System.out.println("容器创建完成...");
        printBeans();
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);
        applicationContext.close();
    }

    public void printBeans(){
        String[]  definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames)
        {
            System.out.println(name);
        }
    }
}
