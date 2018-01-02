package com.example.servlet;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

@WebListener(value="this is test listener")
public class HelloListener implements AsyncListener, ServletContextListener, ServletContextAttributeListener,
		ServletRequestListener, ServletRequestAttributeListener, HttpSessionListener, HttpSessionActivationListener,
		HttpSessionBindingListener, HttpSessionAttributeListener, HttpSessionIdListener {
	public static final String THREAD_POOL = "threadPool";
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		Optional.ofNullable(arg0.getServletContext().getAttribute(THREAD_POOL)).ifPresent(e ->{
			ExecutorService es = (ExecutorService)e;
			es.shutdown();
		});
		System.out.println("=======contextDestroyed===ServerInfo:"+arg0.getServletContext().getServerInfo());
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		arg0.getServletContext().addFilter(arg0, arg1);
//		arg0.getServletContext().addServlet(arg0, arg1);
//		arg0.getServletContext().addListener(arg0);
		System.out.println("=======contextInitialized===ServerInfo:"+arg0.getServletContext().getServerInfo());
		ExecutorService es = Executors.newWorkStealingPool();
		arg0.getServletContext().setAttribute(THREAD_POOL, es);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		System.out.println("=======requestDestroyed");
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		System.out.println("=======requestInitialized");
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("=======sessionCreated");		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("=======sessionDestroyed");		
	}

	@Override
	public void sessionIdChanged(HttpSessionEvent arg0, String arg1) {
		System.out.println("=======sessionIdChanged");		
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		System.out.println("=======attributeAdded");		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		System.out.println("=======attributeRemoved");		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		System.out.println("=======attributeReplaced");		
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		System.out.println("=======valueBound");		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		System.out.println("=======valueUnbound");		
	}

	@Override
	public void sessionDidActivate(HttpSessionEvent arg0) {
		System.out.println("=======sessionDidActivate");		
	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent arg0) {
		System.out.println("=======sessionWillPassivate");		
	}

	@Override
	public void attributeAdded(ServletRequestAttributeEvent arg0) {
		System.out.println("===ServletRequestAttribute====attributeAdded:"+arg0.getName()+"="+arg0.getValue());		
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("====ServletRequestAttribute===attributeRemoved");
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("====ServletRequestAttribute===attributeReplaced");
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("=======attributeAdded==="+arg0.getName()+":"+arg0.getValue());
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("=======attributeRemoved");
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		System.out.println("=======attributeReplaced");
	}

	@Override
	public void onComplete(AsyncEvent arg0) throws IOException {
		System.out.println("=======onComplete===Exception:"+arg0.getThrowable());
	}

	@Override
	public void onError(AsyncEvent arg0) throws IOException {
		System.out.println("=======onError===Exception:"+arg0.getThrowable());
		//must invoke complete, otherwise it will throw java.io.IOException: Premature EOF
		arg0.getAsyncContext().complete();
	}

	@Override
	public void onStartAsync(AsyncEvent arg0) throws IOException {
		System.out.println("=======onStartAsync");
	}

	@Override
	public void onTimeout(AsyncEvent arg0) throws IOException {
		System.out.println("=======onTimeout===Exception:"+arg0.getThrowable());
		//must invoke complete, otherwise it will throw java.io.IOException: Premature EOF
		arg0.getAsyncContext().complete();
	}

}
