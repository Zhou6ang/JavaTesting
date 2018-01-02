package com.example.servlet.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.servlet.HelloListener;

@WebServlet(value={"/testing"},asyncSupported=true)
public class HelloNIOServletForTesting extends HttpServlet {

	/**
	 * 1. here is testing for servlet: NIO, Asynchronous processing
	 * 2. put the war package to tomcat and startup.
	 * 2. visit http://127.0.0.1:8080/servlet-testing/testing in browser.
	 * 
	 */
	private static final long serialVersionUID = -4723118051340073526L;
	private ExecutorService pool = null;
	@Override
	public void init() throws ServletException {
		Object obj = getServletContext().getAttribute(HelloListener.THREAD_POOL);
		if (obj != null)
			pool = (ExecutorService) obj;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Connection", "Keep-Alive");
        resp.setContentType("text/html;charset=utf-8");
        AsyncContext asy = req.startAsync();
        
        pool.submit(()->{
        	System.out.println("Launching thread to send http request..."+Thread.currentThread());
        	try{
	    		PrintWriter out = resp.getWriter();
	    		
	    		URLConnection urlstr = new URL(req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+"/"+req.getContextPath()+"/nio").openConnection();
	    		System.out.println(urlstr.getURL());
	    		HttpURLConnection url = (HttpURLConnection)urlstr;
	    		url.setDoOutput(true);
	    		url.setRequestMethod("GET");
	    		
	//    		System.out.println(url.getResponseCode());
	    		
	    		url.setChunkedStreamingMode(2);
	    		OutputStream output = url.getOutputStream();
	    		IntStream.range(1, 15).forEach(e->{
	    			try {
	    				String str = e+"sxxxxxxaaaaaaaaaaaaaaaaaaaaaaaxxxxx"+e;
	    				out.println("<br>sending data: "+str);
	    				out.flush();
	    				
	    				output.write(str.getBytes());
	    				output.flush();
	    				Thread.sleep(500);
	    			} catch (Exception e1) {
	    				e1.printStackTrace();
	    			}
	    		});
	    			
	    		InputStream input = url.getInputStream();
	    		
	    		int i= -1;
	    		byte[] b = new byte[1024];
	    		while((i = input.read(b)) != -1){
	    			out.println("<br>received data:"+new String(b,0,i));
	    			out.flush();
	    		}
	    		out.println("<br>All steps done successfully .....");
	    		output.close();
	    		input.close();
	    		url.disconnect();
	    		asy.complete();
        	}catch (Exception e) {
            	e.printStackTrace();
    		}
        });
        
		
	}

	
}
