package com.byf.bean;

import com.byf.aop.MathCalculator;
import com.byf.config.MyConfigOfAOP;
import com.byf.config.MyConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_AOP {

    // 1、使用命令行动态设置环境变量：在虚拟器参数位置加载 -Dspring.profiles.active=dev
    // 2、创建IOC容器时设置环境变量
    @Test
    public void test(){
        // 1、创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAOP.class);
        System.out.println("容器创建完成...");
        final MathCalculator calculator = applicationContext.getBean(MathCalculator.class);
        System.out.println(calculator);
        try{
            calculator.div(5,0);
        } catch (Exception e){
            System.out.println("java.lang.ArithmeticException: / by zero");
        }
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
