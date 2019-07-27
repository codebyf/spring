package com.byf.bean;

import com.byf.config.MainConfig2;
import com.byf.config.MainConfig1;
import org.junit.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.Map;

public class IOCTest {

    @Test
    public void testIOC(){
        ListableBeanFactory applicationContext =  new AnnotationConfigApplicationContext(MainConfig1.class);;
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for(String name : beanNames){
            System.out.println(name);
        }
    }

    @Test
    public void testIOC2(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        System.out.println("ioc容器创建完成....");
        Object p1 = applicationContext.getBean("person");
        Object p2 = applicationContext.getBean("person");
        System.out.println(p1 == p2);
        /*String[] beanNames = applicationContext.getBeanDefinitionNames();
        for(String name : beanNames){
            System.out.println(name);
        }

        Object p1 = applicationContext.getBean("person");
        Object p2 = applicationContext.getBean("person");
        System.out.println(p1 == p2);*/
    }

    /**
     * @Conditional：按照一定的条件进行判断，满足条件给容器中注册bean
     *
     */
    @Test
    public void test03(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
        Environment environment = applicationContext.getEnvironment();
        // 动态获取环境变量的值：Windows 7
        String os = environment.getProperty("os.name");
        System.out.println(os);
        for (String name : namesForType){
            System.out.println(name);
        }
        Map<String, Person> persons= applicationContext.getBeansOfType(Person.class);
        System.out.println(persons.toString());

    }

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @Test
    public void testImport(){
        printBeans();
        // 工厂Bean获取的是调用getObject创建的对象
        Object bean = applicationContext.getBean("colorFactoryBean");
        Object bean2 = applicationContext.getBean("colorFactoryBean");
        System.out.println("bean的类型：" + bean.getClass());
        System.out.println("bean的类型：" + bean2.getClass());
        // 注意运算符+号的优先级：+ 大于 ==，（bean == bean2）要加括号
        System.out.println("bean == bean2 : " + (bean == bean2));
        Object bean3 = applicationContext.getBean("&colorFactoryBean");
        System.out.println("bean的类型：" + bean3.getClass());
    }

    public void printBeans(){
        String[]  definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames)
        {
            System.out.println(name);
        }
    }
}
