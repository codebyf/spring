package com.byf.bean;

import com.byf.config.MyConfigOfAutowried;
import com.byf.dao.BookDao;
import com.byf.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Autowried {
    @Test
    public void test(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAutowried.class);
        /*BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);
        bookService.print();*/

        /*Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);
        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);
        Color color = applicationContext.getBean(Color.class);
        System.out.println(color);*/

        Red red = applicationContext.getBean(Red.class);
        System.out.println(red);
        /*BookDao bookDao = applicationContext.getBean(BookDao.class);
        System.out.println(bookDao);*/

    }
}
