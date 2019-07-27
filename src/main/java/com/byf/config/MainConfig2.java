package com.byf.config;

import com.byf.bean.Color;
import com.byf.bean.ColorFactoryBean;
import com.byf.bean.Person;
import com.byf.bean.Shape;
import com.byf.condition.LinuxCondition;
import com.byf.condition.MyImportBeanDefinitionRegistry;
import com.byf.condition.MyImportSelector;
import com.byf.condition.WindowsCondition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
@Conditional(WindowsCondition.class)
@Import({Color.class, Shape.class, MyImportSelector.class, MyImportBeanDefinitionRegistry.class})
public class MainConfig2 {
    // 默认是单实例的
    /**
     * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON
     * prototype：多实例
     * singleton：单实例（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。
     *                          以后每次获取就是直接从容器（map.get()）中拿
     * request：同一个请求创建一个实例
     * session：同一个session创建一个实例
     *
     * 懒加载：
     *         单实例bean：默认在容器启动的时候创建
     *         懒加载：容器启动的时候不创建对象，第一次使用（获取）Bean创建对象，并初始化
     */
    // @Scope(value = "prototype")
    @Bean
    @Lazy
    public Person person(){
        System.out.println("给容器添加Person...");
        return new Person("张三", 21);
    }
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    @Lazy
    public Person person01(){
        System.out.println("给容器添加Person...");
        return new Person("bill", 21);
    }
    @Conditional(LinuxCondition.class)
    @Bean("linus")
    @Lazy
    public Person person02(){
        System.out.println("给容器添加Person...");
        return new Person("linus", 21);
    }

    /**
     * 给容器中注册组件：
     *         1）包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）[自己写的]
     *         2）@Bean[导入的第三方包的组件]
     *         3）@Import：导入没有注解标注的Bean
     *              1.@Import(要导入到容器中的组件)：容器中就会自动注册这个组件
     *              2.ImportSelector：返回需要注册的全类名
     *              3.ImportBeanDefinitionRegistry：手动注册bean到bean容器
     *         4）使用Spring提供的FactoryBean（工厂Bean）
     */
    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
