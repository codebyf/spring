package com.byf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * bean的声明周期
 *          bean创建--初始化--销毁过程
 * 容器管理bean的声明周期
 * 自定义初始化和销毁方法：容器在bean进行到当前声明舟曲的时候使用自定义初始化和销毁方法
 *
 * 构造（对象创建）
 *          单实例：在容器启动的时候创建对象（指定@Lazy时，在容器创建完成后构造）
 *          多实例：在每次获取Bean的时候创建对象
 *
 * 遍历得到容器所有的BeanPostProcessor：挨个执行beforeInitialization，
 * 一旦返回null，跳出for循环，不会执行后面的BeanPostProcessor.postProcessBeforeInitialization方法
 *
 * beanPostProcessor原理
 * populateBean(beanName, mbd, instanceWrapper);// 给bean进行属性赋值
 * initializeBean(beanName, exposedObject, mbd){
 * applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 * invokeInitMethods(beanName, wrappedBean, mbd); 执行自定义初始化
 * applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 * }
 *
 * BeanPostProcessor.postProcessBeforeInitialization
 * 初始化：
 *          对象创建完成，并赋值好，调用初始化方法
 * BeanPostProcessor.postProcessAfterInitialization
 * 销毁：
 *          单实例：容器关闭的时候
 *          多实例：容器不会管理这个Bean，容器不会调用销毁方法
 *
 * 1）指定初始化和销毁方法
 *          指定init-method和destroy-method
 * 2）通过让Bean实现接口：
 *          InitializingBean（定义初始化逻辑）
 *          DisposableBean（定义销毁逻辑）
 *
 * 3）可以使用JSR250：
 *          @PostConstruct ：在bean创建完成并属性赋值完成，来执行初始化方法
 *          @PreDestroy ： 在容器销毁bean之前通知我们进行清理工作
 * 4）BeanPostProcessor【interface】：bean的后置处理器：
 *          在bean的初始化前后进行一些处理工作（例如：包装bean）
 *          postProcessBeforeInitialization：构造之后，在初始化之前
 *          postProcessAfterInitialization：在初始化之后工作
 *
 * Spring底层对 BeanPostProcessor 的使用：
 *          bean赋值，注入其他组件，@Autowired，声明周期注解功能，@Async，xxx 都是通过BeanPostProcessor实现
 *
 */
@ComponentScan(value = "com.byf.bean")
@Configuration
public class MyConfigOfLifeCycle {
    public class Car{
        public Car() {
            System.out.println("对象Car创建...");
        }
        public void init(){
            System.out.println("Car ... init");
        }
        public void destory(){
            System.out.println("Car ... destroy");
        }
    }
    //@Scope(value = "prototype")
    // @Bean(initMethod = "init", destroyMethod = "destory")
    public Car car(){
        return new Car();
    }
}


