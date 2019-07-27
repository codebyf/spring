package com.byf.bean;

import org.springframework.beans.factory.FactoryBean;

// 创建一个Spring定义的工厂FactoryBean
public class ColorFactoryBean implements FactoryBean<Color> {
    // 返回一个对象Color，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean -> getObject()");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    // 是否单例
    // true：这个Bean在容器中保存一份
    // false：多实例
    @Override
    public boolean isSingleton() {
        return true;
    }
}
