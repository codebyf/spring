package com.byf.bean;

import com.byf.config.MyConfigOfProfile;
import com.byf.config.MyConfigOfPropertieyValue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Profile {

    // 1、使用命令行动态设置环境变量：在虚拟器参数位置加载 -Dspring.profiles.active=dev
    // 2、创建IOC容器时设置环境变量
    @Test
    public void test(){
        // 1、创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 2、设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("test");
        // 3、注册主配置类
        applicationContext.register(MyConfigOfProfile.class);
        // 4、启动刷新容器
        applicationContext.refresh();
        System.out.println("容器创建完成...");

        printBeans(applicationContext);
        applicationContext.close();
    }

    public void printBeans(AnnotationConfigApplicationContext applicationContext){
        String[]  definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames)
        {
            System.out.println(name);
        }
    }
}
