package com.byf.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;



@WebServlet(value="/async",asyncSupported=true)
public class HelloAsyncServlet extends HttpServlet{


	/*@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws javax.servlet.ServletException ,java.io.IOException {
		// 1、支持异步处理asyncSupported=true
		// 2、开启异步模式
		final AsyncContext startAsync = req.startAsync();
		// 3、开始异步处理
		startAsync.start(new Runnable(){

			@Override
			public void run() {
				try {
					sayHello();
					startAsync.complete();
					// 获取到异步上下文
					AsyncContext asyncContext = req.getAsyncContext();
					// 4、获取响应
					ServletResponse response = asyncContext.getResponse();
					response.getWriter().write("Hello async...");
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}
			
		});
	};*/
	
	public void sayHello() throws InterruptedException{
		System.out.println(Thread.currentThread() + "processing...");
		Thread.sleep(3000);
	}
}
