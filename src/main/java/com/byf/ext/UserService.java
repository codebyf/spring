package com.byf.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @EventListener
    public void listen(ApplicationEvent event){
        System.out.println("UserServer ：接收事件->" + event);
    }
}
