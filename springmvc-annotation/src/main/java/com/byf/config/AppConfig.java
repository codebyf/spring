package com.byf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.byf.controller.MyFirstInterceptor;

// SpringMVC只扫描Controller子容器
// useDefaultFilters=false 禁用默认过滤规则
@ComponentScan(value="com.byf", includeFilters={
		@Filter(type=FilterType.ANNOTATION,classes={Controller.class})
},useDefaultFilters=false)
@EnableWebMvc
@Async
public class AppConfig extends WebMvcConfigurerAdapter{
	// 定制
	
	// 视图解析器
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// 默认所有页面都从/WEB-INF / xx.jsp
		registry.jsp("/WEB-INF/views/",".jsp");
	}
	
	// 静态资源访问
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	// 拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**");
	}
}
