package com.example.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.IntStream;

public class ServletNIOTest {

	/**
	 * The purpose of this Test is to verify Servlet NIO functionality.
	 * It's same with HelloNIOServletTest as visit http://127.0.0.1:8080/servlet-testing/testing.
	 * This is same way with HelloNIOServletTest, but here we don't need to open browser.
	 * 1. deploy war package to container, e.g. tomcat.
	 * 2. run it.
	 * @param args
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
		
		HttpURLConnection url = (HttpURLConnection)new URL("http://127.0.0.1:8080/servlet-testing/nio?a=10").openConnection();
		url.setDoOutput(true);
		url.setRequestMethod("GET");
		
//		System.out.println(url.getResponseCode());
		
		url.setChunkedStreamingMode(2);
		OutputStream output = url.getOutputStream();
		IntStream.range(1, 15).forEach(e->{
			try {
				System.out.println(e);
				output.write((e+"sxxxxxxaaaaaaaaaaaaaaaaaaaaaaaxxxxx"+e).getBytes());
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
			System.out.println("received data:"+new String(b,0,i));
		}
		System.out.println("Done ......");
		output.close();
		input.close();
		url.disconnect();
		
	}

}
