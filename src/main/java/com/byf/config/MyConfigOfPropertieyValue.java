package com.byf.config;

import com.byf.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// 使用@PropertiesSource读取外部配置文件
@PropertySource(value = {"classpath:/conf/person.properties"})
@Configuration
public class MyConfigOfPropertieyValue {
    @Bean
    public Person person(){
        return new Person();
    }
}
