package com.byf;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.byf.config.AppConfig;
import com.byf.config.RootConfig;


public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	// 获取根容器的配置类：（Spring的配置文件）父容器
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{RootConfig.class};
	}
	// 获取web容器的配置类（SpringMVC配置文件）子容器
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{AppConfig.class};
	}
	// 获取DispatcherServlet的映射信息
	/**
	 *	//:拦截所有请求（包括静态资源（xx.js,xx.png），但不包括*.jsp
	 *	//*：拦截
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

}
