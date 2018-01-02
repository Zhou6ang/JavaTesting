package com.example.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class SocketTest {
	
	/**
	 * The purpose of this Test is to verify Http protocol upgrade based on http/1.1
	 * e.g. upgrade http protocol from http/1.1 to http/2.0 or websocket or zg or other self defined protocol.
	 * scenarios: 
	 * 1. send req to server side to negotiate protocol zg.
	 * 2. after negotiation successfully and send msg to server side.
	 * 3. server side received msg and send response to client.
	 * 
	 */
	private Map<String, String> headers = new HashMap<>();
	public static void main(String[] args) throws Exception {
		new SocketTest().createSocket();
	}
	
	public void createSocket() throws Exception{
		Socket soc = new Socket("localhost", 8080);
		OutputStream output = soc.getOutputStream();
		output.write("POST /servlet-testing/protocolupgrade HTTP/1.1\r\n".getBytes());
		output.write("Host: localhost:8080\r\n".getBytes());
		output.write("Accept: */*\r\n".getBytes());
		output.write("Connection: Upgrade\r\n".getBytes());
		output.write("Upgrade: zg\r\n".getBytes());
		output.write(System.lineSeparator().getBytes());
		output.flush();
		
		InputStream input = soc.getInputStream();
		
		if(isNegotiationSuccess(input)){
			
			System.out.println("===========Sending data with new protocol=================");
			IntStream.range(0, 5).forEach(e->{
				try {
					System.out.println(e);
					output.write(("task: "+e+".this data sent from client side after negotiated between client and server.").getBytes());
					output.flush();
					Thread.sleep(1000);
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
				
			});
			//write data done.
			output.write("\r\n".getBytes());
			System.out.println("===========Receiving data with new protocol=================\r\n");
			int i = -1;
			byte[] b = new byte[8192*10];
			Path path = Paths.get("D:\\output.txt");
			Files.deleteIfExists(path);
			Files.createFile(path);
			while((i = input.read(b)) != -1){
				String s = new String(b,0,i);
				s=s.replaceAll("\\.", "").replaceAll("\r\n", "");
				System.out.println(s);
				
				Files.write(path, (s+"\r\n").getBytes(), StandardOpenOption.APPEND);
				if(s.contains("@End@")){
					System.out.println("Done ...");
					break;
				}
			}
		}else{
			System.out.println("Negotiation was failed...");
		}
		output.close();
		input.close();
		soc.close();
	}

	/**
	 * Check if protocol negotiation was Success.
	 * @param input
	 * @return
	 * @throws IOException
	 */
	private boolean isNegotiationSuccess(InputStream input) throws IOException {
		boolean flag = true;
		int i = -1;
		StringBuffer head = new StringBuffer();
		while ((i = input.read()) != -1) {
			head.append((char) i);
			if (head.toString().contains("\r\n\r\n")) {
				// parseHeader(head.toString());
				String headstr = head.toString().split("\r\n\r\n")[0];
				String[] headAry = headstr.split("\r\n");
				if (headAry.length <= 0) {
					// statusCode = "400 Bad http request as not follow http
					// protocol spec.";
					System.out.println("400 Bad http request as not follow http protocol spec.");
					flag = flag && false;
					break;
				}
				for (int j = 0; j < headAry.length; j++) {
					if (j == 0) {
						/**
						 * @TODO some check and change isHttp = true?
						 */
						String StatusLine = headAry[j];
						System.out.println("StatusLine:" + StatusLine);
						 if(StatusLine.contains("101") ){
							 System.out.println("Protocol negotiation successfully.");
							 flag = flag && true;
							 break;
						 }
					} else {
						// request headers.
						String header = headAry[j];
						if (header == null || "".equals(header)) {
							// resContent = "Bad http request as missing
							// header.";
							// statusCode = "400";
							System.out.println("Bad http request as missing header.");
							flag = flag && false;
							break;
						}

						String[] kv = header.replaceFirst(":", "@@").split("@@");
						if (kv.length != 2) {
							// resContent = "Bad http request as error header.";
							// statusCode = "400";
							System.out.println("Bad http request as error header.");
							flag = flag && false;
							break;
						} else {
							headers.put(kv[0], kv[1]);
						}
					}
				}
				//parse header end.
				break;
			}
		}
		
		return flag;
	}
	
}
