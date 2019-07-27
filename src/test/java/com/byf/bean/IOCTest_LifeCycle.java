package com.byf.bean;

import com.byf.config.MyConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_LifeCycle {
    @Test
    public void test(){
        // 1、创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfLifeCycle.class);
        System.out.println("容器创建完成...");
/*        Object o = applicationContext.getBean("car");
        Object o2 = applicationContext.getBean("car");*/
        // 2、关闭IOC容器
        applicationContext.close();
    }
}
