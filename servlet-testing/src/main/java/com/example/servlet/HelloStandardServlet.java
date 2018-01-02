package com.example.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Hello", urlPatterns = { "/a", "/b", "/c" }, loadOnStartup = 1, initParams = {
		@WebInitParam(name = "user", value = "tom") }, asyncSupported = true, displayName = "hello testing", description = "servlet description", largeIcon = "")
@MultipartConfig(maxFileSize=10000,maxRequestSize=1000,fileSizeThreshold=8888)
public class HelloStandardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ExecutorService pool = null;
	
	
	@Override
	public void init() throws ServletException {
		Object obj = getServletContext().getAttribute(HelloListener.THREAD_POOL);
		if (obj != null)
			pool = (ExecutorService) obj;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//the client will continue output.
		resp.setHeader("Connection", "Keep-Alive");
		resp.setContentType("text/html;charset=utf-8");
		
		AsyncContext asyn = req.startAsync();
		//no timeout.
		asyn.setTimeout(0);
		System.out.println("Async timeout:"+asyn.getTimeout());
		asyn.addListener(new HelloListener());
		Thread thd = Thread.currentThread();
		System.out.println("HelloServlet thread:"+thd);
		resp.getWriter().println("<br>Servlet start time: "+LocalDateTime.now()+" Thread:"+thd);
		resp.getWriter().flush();
		
		pool.submit(()->{
			try {
			String percent = asyn.getRequest().getParameter("percent");
			int total = percent != null ? Integer.parseInt(percent):50;
			PrintWriter out = asyn.getResponse().getWriter();
			Thread localThd = Thread.currentThread();
			System.out.println("Task launching time: "+LocalDateTime.now()+localThd);
			out.println("<br>Task launching time: "+LocalDateTime.now()+localThd);
			IntStream.range(1, total).forEach(e->{
				try {
					out.println("<br> "+localThd+":"+(e*100/total)+"%");
					out.flush();
					Thread.sleep(1000);
				} catch (Exception e1) {
					throw new RuntimeException("error happened:",e1);
				}
			});
			out.println("<br>Task stalling and response time:"+LocalDateTime.now());
			} catch (Exception e) {
				e.printStackTrace();
				getServletContext().log(e.getLocalizedMessage());
			}
			asyn.complete();
		});
		
//		boolean flag = req.authenticate(resp);
//		System.out.println(flag);
//		try {
		//using web-container username and password.
//			req.login("tomcat", "123456");
//			System.out.println("Authentication successfully.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(req.getRemoteUser());
//		System.out.println(req.getUserPrincipal());
//		System.out.println(req.getAuthType());
//		System.out.println(req.getRemoteUser());
		
		log("received message from request. sessionId:"+req.getRequestedSessionId());
		System.out.println("Servlet handling ...");
		System.out.println(req.getAttribute("key"));
		Enumeration<String> en = getInitParameterNames();
		String tmp="";
		while(en.hasMoreElements()){
			String k = en.nextElement();
			tmp +=k+":"+getInitParameter(k)+" ";
		}
		resp.getWriter().println("<br>Init params="+tmp);
		resp.getWriter().flush();
		resp.getWriter().println("<br>Servlet complete time: "+LocalDateTime.now());
		resp.getWriter().flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	
}
