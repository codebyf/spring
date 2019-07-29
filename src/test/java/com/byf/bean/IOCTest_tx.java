package com.byf.bean;

import com.byf.tx.TxConfig;
import com.byf.tx.UserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_tx {

    @Test
    public void test(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserDao userDao = applicationContext.getBean(UserDao.class);
        printBeans(applicationContext);
        userDao.insert();
    }

    public void printBeans(ApplicationContext applicationContext){
        String[]  definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames)
        {
            System.out.println(name);
        }
    }
}
