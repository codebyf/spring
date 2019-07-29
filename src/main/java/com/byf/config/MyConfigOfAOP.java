package com.byf.config;

import com.byf.aop.LogAspects;
import com.byf.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP：【动态代理】
 *      只在程序运行期间动态的将某段代码注入到指定方法指定位置进行运行的编程
 *
 * 1、导入aop模块依赖：Spring AOP（spring-aspects）
 * 2、定义一个业务逻辑类（MathCalculator）：在业务逻辑运行的时候
 * 3、定义一个日志切面（LogAspects）：切面里的方法需要动态感知MathCalculator.div运行到
 *       通知方法：
 *              前置通知(@Before)：logStart：在目标方法(div)运行之前运行
 *              后置通知(@After)：logEnd：在目标方法(div)运行结束之后运行
 *              返回通知(@AfterReturning)：logReturn：在目标方法(div)正常返回之后运行
 *              异常通知(@AfterThrowing)：logException：在目标方法(div)出现异常以后运行
 *              环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.proceed())
 * 4、给切面类的目标方法标注何时何地运行（通知注解）
 * 5、将切面类和业务逻辑类（目标方法所在类）加入到容器
 * 6、必须告诉Spring切面类是谁，@Aspect
 * 7、给配置类加@EnableAspectJAutoProxy【开启基于注解的aop模式】
 *        在Spring中很多的@EnableXXX
 *
 *  三部：
 *      1）将业务逻辑组件和切面类都加入到容器中：告诉Spring哪个是切面类（@Aspect）
 *      2）在切面类的每一个通知方法上标注通知注解，告诉Spring何时何地执行（切入点表达式）
 *      3）开启基于注解的aop模式：
 *              @EnableAspectJAutoProxy
 *
 *  AOP原理：【给容器注册了哪些组件，组件是么时候工作，组件的功能是什么】
 *          @EnableAspectJAutoProxy
 *  1、@EnableAspectJAutoProxy是什么？
 *          @Import（AspectJAutoProxyRegistrar.class)：给容器导入AspectJAutoProxyRegistrar
 *              利用AspectJAutoProxyRegistrar自定义给容器中注册bean
 *              internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator
 *           给容器中注册一个容器代理创建器：AnnotationAwareAspectJAutoProxyCreator（AspectJ自动代理创建器）
 *
 *  2、AnnotationAwareAspectJAutoProxyCreator
 *      ->AspectJAwareAdvisorAutoProxyCreator
 *          ->AbstractAdvisorAutoProxyCreator
 *              ->AbstractAutoProxyCreator
 *                  implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                  关注后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory
 *
 *  AbstractAutoProxyCreator.setBeanFactory
 *  AbstractAutoProxyCreator.[后置处理器]postProcessBeforeInitialization、postProcessAfterInitialization
 *
 *  AbstractAdvisorAutoProxyCreator.setBeanFactory -》 initBeanFactory()
 *
 *  流程：
 *      1）传入配置类：创建IOC容器
 *      2）注册配置类：调用refresh()刷新容器
 *      3）invokeBeanFactoryPostProcessors(beanFactory);注册bean的后置处理器来方便连接bean的创建
 *          1）先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
 *          2）给容器中添加别的BeanPostProcessor
 *          3）优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *          4）再给容器注册实现了Ordered接口的BeanPostProcessor
 *          5）注册没实现优先级接口的BeanPostProcessor
 *          6）注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中
 *              创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
 *              1）创建bean实例
 *              2）populateBean：给bean的各种属性赋值
 *              3）initializeBean：初始化bean；
 *                  1）invokeAwareMethods()：处理Aware接口的方法回调
 *                  2）applyBeanPostProcessorsBeforeInitialization()：应用后置处理器的processor.postProcessBeforeInitialization
 *                  3）invokeInitMethods()：执行自定义初始化方法
 *                  4）applyBeanPostProcessorsAfterInitialization()：应用后置处理器的processor.postProcessAfterInitialization
 *              4）BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)：创建成功：aspectJAdvisorFactory、aspectJAdvisorsBuilder
 *          7）把beanPostProcessor注册到beanFactory中：
 *              beanFactory.addBeanPostProcessor(beanPostProcessor)
 * -----------------以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程--------------
 *      4）finishBeanFactoryInitialization(beanFactory)：完成beanFactory初始化工作，创建剩下的单实例bean
 *          1）遍历获取容器中所有的bean，依次创建对象getBean(beanName);
 *              getBean->doGetBean->getSingleton(beanName)
 *          2）创建bean：
 *              【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会拦截，InstantiationAwareBeanPostProcessor，
 *              会调用AbstractAutoProxyCreator的postProcessBeforeInstantiation()】
 *              1）先从缓存中获取当前bean，如果能获取到，说明bean是之前被创建的bean，直接调用，否则创建
 *                  只要创建好的Bean会被缓存起来
 *              2）createBean()：创建bean：AnnotationAwareAspectJAutoProxyCreator在任何创建Bean之前返回bean的实例
 *                  【BeanPostProcessor：在Bean对象创建完成，初始化前后调用】
 *                  【InstantiationAwareBeanPostProcessor：在创建Bean实例之前先尝试调用后置处理器返回对象的】
 *                  resolveBeforeInstantiation(beanName, mbdToUse);解析BeforeInstantiation
 *                  希望后置处理器能再次返回一个代理对象，如果能返回代理对象就返回，如果不能继续
 *                  1）后置处理器先尝试返回对象
 *                      bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *                      拿到所有后置处理器：如果是InstantiationAwareBeanPostProcessor；
 *                      就执行postProcessBeforeInstantiation
 *                      if(bean != null) {
 *                          bean = applyBeanPostProcessorsBeforeInstantiation(bean, beanName);
 *                      }
 *                  2）createBean(beanName, mbd, args);真正的去创建一个bean
 *                  3）
 *
 * AnnotationAwareAspectJAutoProxyCreator
 *      1）每一个bean创建之前，调用postProcessBeforeInstantiation：
 *          关心MathCalculator和LogAspects的场景
 *          1）判断当前bean是否在advisedBeans中（保存了所有需要增强bean）
 *          2）判断当前bean是否是基础类型Advice/Pointcut/Advisor/AopInfrastructureBean
 *              或者是否为切面（@Aspect）
 *          3）是否需要跳过
 *                  1）获取候选的增强（切面里面的通知方法）【List<Advisor> candidateAdvisors】
 *                      每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor类型
 *                      判断每一个增强器是否是AspectJPointcutAdvisor类型：返回true
 *                  2）永远返回false
 *
 *       2）创建对象
 *       postProcessAfterInitialization
 *              return wrapIfNecessary(bean, beanName, cacheKey);
 *              1）获取当前bean的所有增强器（通知方法）
 *                  1）找到能在当前bean使用的增强器（找那些通知方法时需要切入当前bean方法的）
 *                  2）获取能够在bean使用的增强器
 *                  3）给增强器排序
 *              2）保存当前bean在advisedBeans中
 *              3）如果当前bean需要增强，创建当前bean的代理对象
 *                  1）获取所有增强器（通知方法）
 *                  2）保存到proxyFactory
 *                  3）创建代理对象：Spring自动决定
 *                      if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
 * 				          return new JdkDynamicAopProxy(config); // jdk动态代理
 *                       }
 * 			            return new ObjenesisCglibAopProxy(config); // cglib的动态代理
 * 			        4）给容器中返回当前组件使用cglib增强了的代理对象
 * 			        5）以后容器中获取到的这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程
 * 		  3）目标方法执行：
 * 		        容器中保存了组件的代理对象（cglib增强后的对象），这个对象里面保存了详细信息（比如增强器，目标对象：xxx）
 * 		        1）CglibAopProxy.intercept();拦截目标方法的执行
 * 		        2）根据ProxyFactory对象获取将要执行的目标方法拦截器
 * 		            List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 * 		            1）List<Object> interceptorList保存所有拦截器 5
 * 		                一个默认的ExposeInvocationInterceptor 和 4个增强器
 * 		            2）遍历所有的增强器，将其转换为Interceptor：
 * 		                registry.getInterceptors(advisor)；
 * 		            3）将增强器转换为List<MethodInterceptor>;
 * 		                如果是MethodInterceptor：直接加入到集合中；
 * 		                如果不是，使用AdvisorAdapter将增强器转换为MethodInterceptor
 * 		                转换完成，返回MethodInterceptor数组
 * 		        3）如果没有拦截器链，直接执行目标方法：
 *                  拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）
 * 		        4）如果有拦截器链：把需要执行的目标对象，目标方法，拦截器链等信息传入一个CglibMethodInvocation对象
 * 		            并调用Object retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
 * 		        5）拦截器链的除法过程：
 * 		            1）如果没有拦截器执行目标方法，或者拦截器的所有和拦截器数组-1大小一样（指定了最后一个拦截器）执行目标方法；
 * 		            2）链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行：
 * 		                拦截器机制：保证通知方法与目标方法的执行顺序
 *
 *  总结：
 *      1）@EnableAspectJAutoProxy 开启AOP功能
 *      2）@EnableAspectJAutoProxy 给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreater
 *      3）AnnotationAwareAspectJAutoProxyCreater是一个后置处理器；
 *      4）容器的创建流程：
 *              1）registerBeanPostProcessors（）注册后置处理器：创建 AnnotationAwareAspectJAutoProxyCreator 对象
 *              2）finishBeanFactoryInitialization(beanFactory); 初始化剩下的单实例bean
 *                  1）创建业务逻辑组件和切面组件
 *                  2）AnnotationAwareAspectJAutoProxyCreator 拦截组件的创建过程
 *                  3）组件创建完成之后，判断组件是否需要增强；
 *                         是：切面的通知方法，包装成增强器（Advisor）；给业务逻辑创建一个代理对象（CGlib）；
 *       5）执行目标方法：
 *               1）代理对象执行目标方法
 *               2）CglibA拦截器opProxy.intercept()
 *                   1）得到目标方法的拦截器（增强器包装成MethodInterceptor）
 *                   2）利用拦截器的链式机制，一次进入每一个拦截器进行执行
 *                   3）效果：
 *                      正常执行：前置通知-》目标方法-》后置通知-》返回通知
 *                      异常执行：前置通知-》目标方法-》后置通知-》异常通知
 *
 */
@EnableAspectJAutoProxy
@Configuration
public class MyConfigOfAOP {
    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
