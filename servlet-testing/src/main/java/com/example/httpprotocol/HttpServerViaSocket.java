package com.example.httpprotocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HttpServerViaSocket {

	public static void main(String[] args) throws IOException  {
		
		new HttpServerViaSocket().createServer(9999);
	}

	/**
	 * The purpose of this implementation is to simulate container of server side via http/1.1, e.g. tomcat,jetty,etc.
	 * It can received request from client and send response to client, e.g. HttpClientViaSocket.
	 * @param port
	 * @throws IOException
	 */
	public void createServer(int port)throws IOException{
		ServerSocket ss = new ServerSocket(port);
		System.out.println("Server launching ...");
		while(true){
			String statusCode = "200";
			String resContent = "this is say <a href='http://www.baidu.com'>hello</a> from server side.";
			Socket sk = ss.accept();
			System.out.println("received request ...");
			InputStream input = sk.getInputStream();
			OutputStream output = sk.getOutputStream();
			int i = -1;
			StringBuffer sb = new StringBuffer();
			Map<String, String> headers = new HashMap<>();
			boolean isHttp = false;
			byte[] b = new byte[1024];
			while((i = input.read(b)) != -1){
				sb.append(new String(b,0,i));
				if(sb.toString().contains("\r\n\r\n")){
					String head = sb.toString().split("\r\n\r\n")[0];
					String[] headAry = head.split("\r\n");
					if(headAry.length <= 0){
						resContent = "Bad http request as not follow http protocol spec.";
						statusCode = "400";
						break;
					}
					for (int j = 0; j < headAry.length; j++) {
						if(j == 0){
							/**
							 * @TODO some check and change isHttp = true?
							 */
							String requestline = headAry[j];
							System.out.println("RequestLine:"+requestline);
							if(requestline.contains("HTTP/1.1") ){
								isHttp = true;
							}
						}else{
							//request headers.
							String header = headAry[j];
							if(header == null || "".equals(header)){
								resContent = "Bad http request as missing header.";
								statusCode = "400";
								break;
							}
							
							String[] kv = header.replaceFirst(":", "@@").split("@@");
							if(kv.length != 2){
								resContent = "Bad http request as error header.";
								statusCode = "400";
								break;
							}else{
								headers.put(kv[0].trim(), kv[1]);
							}
						}
					}
					// only process httprequest which does not include body, so break;
					break;
				}
			}
			
			
			System.out.println("headers:"+headers);
			/**
			 * send http response to client.
			 */
			if((headers.get("Host") == null) && isHttp){
				resContent = "Bad http request with missing Host header.";
				statusCode = "400";
			}
			output.write(constructStatusLine(statusCode).getBytes());
			output.write(constructResponseHeader().getBytes());
			output.write(System.lineSeparator().getBytes());
			output.write(constructMessageBody(resContent).getBytes());
			System.out.println("response sent ...");
			input.close();
			output.close();
			System.out.println("done ...");
			sk.close();
		}
		
//		ss.close();
	}
	
	private String constructMessageBody(String body) {
		String chunk_data = body+System.lineSeparator();
		String chunk = Integer.toHexString(body.length())+System.lineSeparator()+chunk_data;
		String last_chunk = "0"+System.lineSeparator();
		String messageEndFlag = System.lineSeparator();
		return chunk+last_chunk+messageEndFlag;
	}

	private String constructStatusLine(String statusCode) {
		return "HTTP/1.1 "+statusCode+" "+System.lineSeparator();
	}

	private String constructResponseHeader() {
		StringBuffer sb = new StringBuffer();
		sb.append("Content-Type: text/html;charset=UTF-8"+System.lineSeparator());
		sb.append("Transfer-Encoding: chunked"+System.lineSeparator());
		sb.append("Date: "+LocalDateTime.now()+System.lineSeparator());
		return sb.toString();
	}

}
