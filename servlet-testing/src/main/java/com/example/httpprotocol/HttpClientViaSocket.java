package com.example.httpprotocol;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClientViaSocket {

	/**
	 * The purpose of this implementation is to simulate client, e.g. IE,chrome, curl,etc.
	 * It can send request to server side and get response from it.
	 */
	public static void main(String[] args) throws Exception {
		
		Socket soc = new Socket("localhost", 9998);
		OutputStream output = soc.getOutputStream();
		output.write("POST / HTTP/1.1\r\n".getBytes());
		output.write("Host: localhost:8080\r\n".getBytes());
		output.write("Accept: */*\r\n".getBytes());
		output.write(System.lineSeparator().getBytes());
//		output.write("GET http://localhost:8080 HTTP/1.1\r\nConnection: Upgrade\r\nContent-Length: 0\r\nUpgrade: zg\r\n\r\n".getBytes());
		output.flush();
		
		InputStream input = soc.getInputStream();
		
		int i = -1;
		StringBuilder fullcontext = new StringBuilder();
		int contentlength = 0;
		StringBuffer chunk = new StringBuffer("\r\n");
		StringBuffer head = new StringBuffer();
		StringBuffer body = new StringBuffer();
		boolean foundHead = false;
		while((i = input.read()) != -1){
			fullcontext.append((char)i);
			if(foundHead){
				chunk.append((char)i);
				if (chunk.toString().contains("\r\n0\r\n"))
					break;
				int idx = chunk.toString().indexOf("\r\n",1);
				
				if(idx != -1){
					int chunkLength = Integer.parseInt(chunk.substring(2, idx), 16);
					contentlength += chunkLength;
					byte[] b = new byte[chunkLength];
					int a = -1;
					while((a = input.read(b)) != -1){
						String s = new String(b,0,a);
						body.append(s);
						fullcontext.append(s);
						if(b.length - a > 0){
							b = new byte[b.length - a];
						}else{
							chunk.delete(0, chunk.length());
							System.out.println("chunk done.");
							break;
						}
						
					}
				}
			}else{
				head.append((char)i);
				if(head.toString().contains("\r\n\r\n")){
					foundHead = true;
				}
			}
			
		}
		System.out.println(fullcontext.toString());
		System.out.println("====================================");
		System.out.print("contentlength:"+contentlength);
		System.out.print("request body:"+body.toString());
		input.close();
		output.close();
		soc.close();
	}

}
