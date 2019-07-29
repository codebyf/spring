package com.byf.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务：
 *
 * 环境搭建：
 *      1）导入相关依赖
 *          数据源、数据库驱动、Spring-jdbc模块
 *      2）配置数据源、JdbcTemplate（Spring提供的简化数据库操作的工具）操作数据源
 *      3）给方法上标注@Transactional 标识当前方法时一个事务方法；
 *      4）@EnableTransactionManagement 开启基于注解的事务管理功能
 *          @Enablexxx
 *      5）配置事务管理器来控制事务；
 *          @Bean
 *          public PlatformTransactionManager transactionManager()
 *
 * 原理：
 *      1）@EnableTransactionManagement
 *          利用TransactionManagementConfigurationSelector给容器中导入组件
 *          给容器中导入两个组件：
 *              AutoProxyRegistrar、ProxyTransactionManagementConfiguration
 *      2）AutoProxyRegistrar：给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件；
 *           InfrastructureAdvisorAutoProxyCreator：
 *              利用后置处理器机制在对象创建后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用
 *      3）ProxyTransactionManagementConfiguration：
 *           1）给容器中注册事务增器：
 *              1）事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource 解析事务
 *              2）事务拦截器：
 *                  TransactionInterceptor：保存了事务属性信息，事务管理器
 *                  他是一个 MethodInterceptor：
 *                      在目标方法执行的时候：
 *                          执行拦截方法；
 *                          事务拦截器：
 *                              1）先获取事务相关属性
 *                              2）再获取 PlatformTransactionManager ，如果事务没有添加指定的tr则使用平台默认的事务管理器
 *                                  最终会从容器中按照类型 PlatformTransactionManager
 *                              3）执行目标方法：
 *                                  如果异常，获取事务管理器，利用事务管理回滚操作：
 *                                      txInfo.getTransactionManager().rollback
 *                                  如果正常，利用事务管理的提交操作：
 *                                      txInfo.getTransactionManager().commit
 *
 *
 *
 *
 *
 *
 *
 */
@EnableTransactionManagement
@ComponentScan(value = "com.byf.tx")
@Configuration
public class TxConfig {
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("1234");
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        // Spring对@Configuration类会特殊处理：给容器中加组件的方法，多次调用都只是从容器中找组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    // 注册事务管理器到容器中
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }
}
