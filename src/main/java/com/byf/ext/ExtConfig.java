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
 * 1、BeanFactoryPostProcessor 原理
 *  1）ioc容器创建对象
 *  2）invokeBeanFactoryPostProcessors(beanFactory); 执行BeanFactoryPostProcessors
 *      如何找到所有BeanFactoryPostProcessors并执行他们的方法：
 *          1）直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
 *          2）在初始化创建其他组件前面执行
 *
 * 2、BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *      postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry);
 *      在所有bean定义信息将要加载，bean实例还未创建
 *
 *      优先于BeanFactoryPostProcessor执行，
 *      利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些bean
 *
 *  原理：
 *      1）ioc创建容器
 *      2）refresh() -> invokeBeanFactoryPostProcessors(beanFactory)
 *      3）从容器中获取到所有 BeanDefinitionRegistryPostProcessor 组件
 *          1、依次触发所有的 postProcessBeanDefinitionRegistry() 方法
 *          2、再来触发 postProcessBeanFactory() 方法 BeanFactoryPostProcessor
 *      4）再来从容器找到BeanFactoryPostProcessor组件，依次触发触发postProcessBeanFactory()方法
 *
 * 3、ApplicationListener：建ring容器中发布的时间。事件驱动模型开发
 *      public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 *      监听ApplicationEvent 及其下面的子事件；
 *
 *   步骤：
 *      1）写一个事件监听器(实现ApplicationListener<ApplicationEvent>接口)来监听某个事件（ApplicationEvent及其子类）
 *          #@EventListener；
 *          原理：使用EventListenerMethodProcessor处理器解析方法上的@EventListener；
 *
 *
 *      2）把监听器加入容器
 *      3）只要容器中有相关事件的发布，我们就能监听到这个事件：
 *              ContextRefreshedEvent：容器刷新完成（所有bean都完全创建）会发布这个事件；
 *              ContextClosedEvent：关闭容器会发布这个事件；
 *      4）发布一个事件
 *
 *   原理：
 *      ContextRefreshedEvent、com.byf.bean.IOCTest_Ext$1、ContextClosedEvent
 *      1）ContextRefreshedEvent事件：
 *          1）容器创建对象：refresh()
 *          2）finishRefresh();容器刷新完成就会发布ContextRefreshedEvent事件
 *      2）自己发布事件
 *      3）容器关闭会发布ContextClosedEvent事件：
 *
 *    【事件发布流程】：
 *          3）publishEvent(new ContextRefreshedEvent(this));
 *                  1）获取事件多播器派发事件（派发器）：getApplicationEventMulticaster()
 *                  2）multicastEvent派发事件
 *                  3）获取到所有ApplicationListener：getApplicationListeners();
 *                     for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
 *                      1）如果有Executor：可以使用Executor异步进行派发；
 *                          Executor executor = getTaskExecutor();
 *                      2）否则同步的方式直接执行listener方法：invokeListener(listener, event);
 *                      拿到listener回调 listener.onApplicationEvent(event);
 *     【事件多播器(派发器)】
 *          1）容器创建对象：refresh();
 *          2）initApplicationEventMulticaster();初始化多播器ApplicationEventMulticaster
 *              1）先去容器中找有没有id="applicationEventMulticaster"的组件
 *                  if (beanFactory.containsLocalBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME))
 *              2）如果没有：this.applicationEventMulticaster =
 * 					beanFactory.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
 * 				   并加入到容器中：我们就可以在其他组件要派发事件，自动注入这个applicationEventMulticaster
 *      【容器中有哪些监听器】
 *          1）容器创建对象：refresh();
 *          2）registerListener();
 *          for (ApplicationListener<?> listener : getApplicationListeners()) {
 * 			    getApplicationEventMulticaster().addApplicationListener(listener);
 *          }
 *
 *     SmartInitializingSingleton 原理：
 *          1）ioc容器创建对象并refresh();
 *          2）finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean
 *              1）先创建所有的单实例bean：getBean();
 *              2）获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型：
 *                  if (singletonInstance instanceof SmartInitializingSingleton) {
 *                      如果是就调用：smartSingleton.afterSingletonsInstantiated();
 *                  }
 *
 *
 */
@ComponentScan(value = "com.byf.ext")
@Configuration
public class ExtConfig {
    @Bean
    public Car car(){
        return new Car();
    }
}
