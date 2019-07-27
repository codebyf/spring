package com.byf.config;

import com.byf.bean.Person;
import com.byf.dao.BookDao;
import com.byf.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


//@Configuration
@ComponentScans(value = {@ComponentScan(value = "com.byf", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, value = {MyTypeFilter.class})
},useDefaultFilters = false)})
//FilterType.ANNOTATION：根据注解类注入
//FilterType.ASSIGNABLE_TYPE: 根据class名注入
//FilterType.ASPECTJ: 根据ASPECTJ表达式
//FilterType.REGEX：根据正则表达式
//FilterType.CUSTOM：根据自定义规则
/*@ComponentScans(value = {@ComponentScan(value = "com.byf", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class})
})})*/
public class MainConfig1 {
    @Bean("lisi")
    public Person person(){
        return new Person("李四", 21);
    }
}
