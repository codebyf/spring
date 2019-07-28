package com.byf.config;

import com.byf.bean.Red;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Profile:
 *      Spring提供的可以根据当前环境，动态的激活和切换一系列组件的功能
 *
 * 开发环境、测试环境、生产环境
 * 数据源（/A）（/B）（/C）
 *
 * @Profile
 *      指定组件在哪个环境下，才能被注册到容器中，不指定，任何环境下都能注册组件
 * 1）加载了环境标识的bean，只有这个环境被激活的时候才能注册到容器中，默认是default环境
 * 2）写在配置类上，只有指定了环境的时候，整个配置类里面的所有配置生效
 * 3）没有标注环境标识的bean，任何环境都是加载的
 */
@Profile("test")
@PropertySource("classpath:/conf/dbconfig.properties")
@Configuration
public class MyConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    private StringValueResolver valueResolver;

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/user_test");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword("1234");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/user");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword("1234");
        String driverClass = valueResolver.resolveStringValue("${db.driverClass}");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/user_prod");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    // 不单独指定Profile，类上有标注，默认加载
    @Bean
    public Red red(){
        return new Red();
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.valueResolver = resolver;
    }
}
