package com.byf.bean;

import com.byf.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Ext {
    @Test
    public void test(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
        // 发布事件：
        applicationContext.publishEvent(new ApplicationEvent(new String("我发布了一个事件")) {
        });
        applicationContext.close();
    }
}
