package com.byf.config;

import com.byf.bean.Boss;
import com.byf.bean.Car;
import com.byf.bean.Color;
import com.byf.bean.Red;
import com.byf.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 自动装配：
 *        Spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值
 *
 * 1）@Autowired：自动注入
 *          1）默认优先按照类型取容器中找对应的组件：
 *                      applicationContext.getBean(BookService.class);
 *          2）如果找到多个相同类型的组件，再将属性的名称作为组件的id取容器中查找
 *                      applicationContext.getBean(BookService.class);
 *          3）@Qualifier("bookDao")：使用@Qualifier指定需要装配的组件id，而不是使用属性名
 *          4）自动装配默认一定要将属性赋值好，没有就会报错；NoSuchBeanDefinition
 *                      可以使用@Autowired(required=false);
 *          5）@Primary：让Spring进行自动装配的时候默认使用首选的bean
 *                      也可以使用@Qualifier指定要装配的名字
 *
 * 2）Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[java规范的注解]
 *          @Resource：
 *                  可以和@Autowired一样实现自动装配；默认是按照组件名称进行装配；
 *                  没有能支持@Primary功能，没有支持@Autowired(required=false);
 *          @Inject：
 *                  需要导入javax.inject的依赖，和Autowired的功能一样，没有required=false的功能；
 *          @Autowired：
 *                  Spring定义的：@Resource、@Inject都是java规范
 *
 * AutoWiredAnnotationBeanPostProcessor：解析完成自动装配功能
 *
 * 3）@Autowired：构造器，参数，方法，属性，都是从容器中取参数组件的值
 *          1）【标注在方法位置】：@Bean+方法参数：参数从容器中获取参数组件的值
 *          2）【标注在构造器上】：如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件自动从容器中获取
 *          3）【放在参数位置】
 *
 * 4）自定义组件要使用Spring容器底层的一些组件（ApplicationContext，BeanFactory，xxx）
 *          自定义实现组件xxxAware：在创建对象的时候，会调用接口规定的方法注入相关组件：Aware：
 *          把Spring底层一些组件注入到自定义的Bean中：
 *              xxxAware：功能使用xxxProcessor：
 *                  ApplicationContextAware ===> ApplicationContextAwareProcessor
 */
@Configuration
@ComponentScan(value = {"com.byf.dao","com.byf.service","com.byf.controller"})
public class MyConfigOfAutowried {


    @Bean("bookDao2")
    @Primary
    public BookDao bookDao(){
        BookDao bookDao2 = new BookDao();
        bookDao2.setLable("2");
        return bookDao2;
    }

    @Bean
    public Boss boss(@Autowired  Car car){
        return new Boss(car);
    }

    @Bean
    public Car car(){
        return new Car();
    }

    /**
     * @Bean 标注的方法创建对象的时候，方法参数的值从容器中获取
     * @param car
     * @return
     */
    @Bean
    public Color color(Car car){
        Color color = new Color();
        color.setCar(car);
        return color;
    }

    @Bean
    public Red red(){
        return new Red();
    }
}
