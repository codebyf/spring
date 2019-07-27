package com.byf.bean.life;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class DogWithAppContextAware  implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    public DogWithAppContextAware() {
        System.out.println("dog constructor");
    }
    @PostConstruct
    public void init(){
        System.out.println("Dog ... @PostConstruct");
    }
    @PreDestroy
    public void destory(){
        System.out.println("Dog ... @OreDestory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
