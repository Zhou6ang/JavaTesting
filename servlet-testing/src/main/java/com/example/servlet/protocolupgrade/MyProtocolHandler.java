package com.example.servlet.protocolupgrade;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.IntStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;

public class MyProtocolHandler implements HttpUpgradeHandler{
	public static final String END_FLAG = "\r\n\r\n";
	public BlockingQueue<String> msg = new LinkedBlockingQueue<String>();
	
	@Override
	public void init(WebConnection conn) {
		//construct data factory which will generate a batch of data per 3s, and put them to msg queue.
		batchDataFactory();
		
		System.out.println("invoke init method ....");
		/**
		 * None blocking IO.
		 */
		//request NIO.
		/**
		 * Note: Could not use try(ServletInputStream input = conn.getInputStream()){...}
		 */
		try{
			ServletInputStream input = conn.getInputStream();
			input.setReadListener(new ReadListener() {
//				ServletOutputStream output = null;
				boolean sendingout = false;
				@Override
				public void onError(Throwable arg0) {
					System.out.println("====ReadListener===onError:"+arg0.getMessage());
					arg0.printStackTrace();
					System.out.println("====ReadListener===onError");
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onDataAvailable() throws IOException {
					byte[] b = new byte[512];
					int i = -1;
					StringBuffer sb = new StringBuffer();
					while(input.isReady() && (i = input.read(b)) != -1){
						sb.append(new String(b,0,i));
					}
					System.out.println(sb.toString()+"|||"+Thread.currentThread());
					//close connection.
					if(sb.toString().contains("\r\n\r\n")){
						try {
							conn.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					//content received done.
					}else if(sb.toString().contains("\r\n")){
						sendingout = true;
					}else{
						msg.add(sb.toString());
					}
					
					//response NIO.
					if (sendingout) {
						try{
							ServletOutputStream output = conn.getOutputStream();
							output.setWriteListener(new WriteListener() {
								int totalLength = 0;
								
								@Override
								public void onWritePossible() throws IOException {
									System.out.println("WriteListener:" + Thread.currentThread()+" writing ...");
									System.out.println("output Ready:"+output.isReady()+" "+msg.isEmpty());
									int blocklength = 0;
									while (output.isReady() && (!msg.isEmpty())) {
										String m = msg.poll();
										String s = Thread.currentThread() + " server recieved data:" + m;
										blocklength +=s.length();
										output.write(s.getBytes());
										if(msg.isEmpty()){
											 System.out.println("ending ...");
											 output.write("@End@\r\n\r\n".getBytes());
											 try {
												conn.close();
											} catch (Exception e) {
												e.printStackTrace();
											}
										}else{
											while(output.isReady()){
												output.write(46);
												blocklength +=1;
											}
										}
										
									}
									totalLength +=blocklength;
									System.out.println(Thread.currentThread()+", content write length="+blocklength+", total write length="+totalLength);
									try {
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void onError(Throwable arg0) {
									System.out.println("======WriteListener====onError:" + arg0.getMessage());
									arg0.printStackTrace();
									try {
										output.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						sendingout = false;
						System.out.println("initial response NIO done ...");
					}
				}
				
				@Override
				public void onAllDataRead() throws IOException {
					System.out.println("data received complete: "+input.isFinished()+" "+Thread.currentThread());
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		/**
		 * blocking IO.
		 */
//		byte[] b = new byte[10];
//		try (ServletInputStream input = conn.getInputStream()) {
//			
//			int i = -1;
//			while ((i = input.read(b)) != -1) {
//				System.out.println(new String(b, 0, i));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("send response ....");
//		try(ServletOutputStream output = conn.getOutputStream()){
//			output.write(("server already recieved data."+END_FLAG).getBytes());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		System.out.println("send response done....");
	}

	/**
	 * The purpose of this method is to generate fake data per 3s, and obvious NIO outputStream continues processing without stream close.
	 */
	private void batchDataFactory() {
		Random r = new Random();
		new Thread(()->{
				IntStream.range(0, 20).forEach(e->{
					try {
						msg.put("["+e+"].This record was from server side, random:"+r.nextInt(1000));
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				});
		}).start();
	}

	@Override 
	public void destroy() {
		System.out.println("destroy method invoked ...");
	}
	
	
}