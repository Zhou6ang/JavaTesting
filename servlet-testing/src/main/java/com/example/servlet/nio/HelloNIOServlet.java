package com.example.servlet.nio;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.servlet.HelloListener;

@WebServlet(asyncSupported=true,name="HelloNIOServlet",urlPatterns={"/nio"})
public class HelloNIOServlet extends HttpServlet {

	/**
	 * The purpose of this servlet is to implement NIO, Listener, Filter,Asynchronous processing
	 * It's combined with HelloNIOServletTest or ServletNIOTest
	 * To verify this servlet we can go through HelloNIOServletTest or ServletNIOTest.
	 */
	private static final long serialVersionUID = 1L;
	BlockingQueue<String> queue = new LinkedBlockingQueue<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Launching NIO servlet ... "+LocalDateTime.now()+" "+Thread.currentThread());
		AsyncContext asy= req.startAsync();
		asy.addListener(new HelloListener());
		asy.setTimeout(0);//no timeout
		
		System.out.println("========================Request: None blocking IO==============================");
		ServletInputStream input = req.getInputStream();
		
		/**
		 * None blocking IO.
		 */
		input.setReadListener(new ReadListener() {
			
			@Override
			public void onError(Throwable arg0) {
				arg0.printStackTrace();
				asy.complete();
			}
			
			@Override
			public void onDataAvailable() throws IOException {
				System.out.println("Thread of ReadListener:"+Thread.currentThread());
				int i = -1;
				byte[] b = new byte[10];
				StringBuilder sb = new StringBuilder();
				//must invoke isReady(), otherwise could not read rest of data.
				while(input.isReady() && (i=input.read(b)) != -1){
					String s = new String(b,0,i);
					sb.append(s);
					System.out.println(s);
					
				}
				System.out.println("Received data:"+sb);
				System.out.println("Insert to queue: "+queue.add(sb.toString()));
				
			}
			
			@Override
			public void onAllDataRead() throws IOException {
				System.out.println("all of data received done. isFinished:"+input.isFinished()+" "+Thread.currentThread());
//				asy.complete();
				
				//if no need to response and uncomment above.
				System.out.println("========================Response: None blocking IO==============================");
				ServletOutputStream output = resp.getOutputStream();
				/**
				 * None blocking IO.
				 */
				//default value of buffer size is 8192 bytes, set valid value which must be > 8192.
//				resp.setBufferSize(1024*10);
				output.setWriteListener(new WriteListener() {
					int total = 0;
					int times = 0;
					@Override
					public void onWritePossible() throws IOException {
						/**
						 * the default buffer size is 8192bytes(resp.getBufferSize()), so to block
						 * response(make output.isReady to false) that should write bytes > 8192.
						 */
						
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Thread thd = Thread.currentThread();
						System.out.println("Thread of WriteListener:"+thd);
						
						while (output.isReady()) {
							if (queue.isEmpty()) {
								asy.complete();
								break;
							}
							String tmp = LocalDateTime.now() + " Server response: received data [" + queue.poll() + "] "
									+ thd;
							for (int i = 0; i < 1000; i++) {
								tmp += i;
							}
							// could not use output.println(tmp) and output.flush() in this case, since it will throw
							// exception.
							output.write(tmp.getBytes());
							System.out.println("Content write length:"+tmp.length());
							total += tmp.length();
						}
						times += 1;
						System.out.println("total write:"+total+" byte, remaining in buffer:"+(total%resp.getBufferSize())+" byte, flush times:"+times+", bufferSize:"+resp.getBufferSize()+" byte");
					}
					
					@Override
					public void onError(Throwable arg0) {
						arg0.printStackTrace();
						System.out.println("==onError="+arg0.getMessage());
						asy.complete();
					}
				});
			}
		});
		
		System.out.println("Stalling NIO servlet ... "+LocalDateTime.now());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	
}
