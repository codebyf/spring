package com.byf.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Person {
    // 使用@Value进行赋值
    // 1、基本数值
    // 2、可以写SpEL：#{}
    // 3、可以写${}：取出配置文件【properties文件】中的值（在运行环境变量里面的值）

    @Value("Tom")
    private String name;
    @Value("#{20 -2}")
    private int age;
    @Value("${person.nickName}")
    private String nickName;
    public Person(){

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
