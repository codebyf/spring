package com.byf;

import com.byf.bean.Person;
import com.byf.config.MainConfig1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App
{
    public static void main( String[] args )
    {
        /*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);*/

        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(MainConfig1.class);
        Person person = (Person) applicationContext.getBean("lisi");
        System.out.println(person);

    }
}
