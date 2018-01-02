package com.zg.http_https.urlconnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.Base64;

public class Http {

	public static void main(String[] args) throws IOException {
		/**
		 * download zip with HTTPs
		 */
		URL url = new URL("https://eslinv70.emea.nsn-net.net:8080/job/med_n16-5_dynamic-adaptation_ris/ws/implementation/NE3SWS-DynamicAdaptation-Mediation/dynamicAdaptation-pdds-se/target/dynamicAdaptation-pdds-se-1.0-installer.zip");
		
		/**
		 * Send a request with HTTP
		 */
//		URL url = new URL("http://www.qq.com");
		/**
		 * Send a http request via Proxy.
		 */
		HttpURLConnection urlconn = (HttpURLConnection)url.openConnection(new Proxy(Type.HTTP, new InetSocketAddress("10.144.1.10", 8080)));
//		HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
		urlconn.setDoOutput(true);
		urlconn.setDoInput(true);
		urlconn.setRequestMethod("GET");
		
		//Option 1: set username and pwd with Property
		String pwd = Base64.getUrlEncoder().encodeToString("ganzhou:Happy,1111".getBytes("UTF-8"));
		urlconn.setRequestProperty("Authorization","Basic "+ pwd);
		
		/**
		 * Simulator IE/Chrome sending httprequest.
		 */
//		urlconn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36");
//		urlconn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp;q=0.8");
		
		/**
		 * indicate that response message was encoding with gzip or others.
		 */
//		urlconn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		
//		urlconn.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
//		urlconn.setRequestProperty("DNT", "1");
//		urlconn.setRequestProperty("Upgrade-Insecure-Requests", "1");
//		urlconn.setRequestProperty("Cookie", "JSESSIONID.c6e4dd15=s5p7jlx9m14upwy39mez189d; screenResolution=1920x1080; jenkins-timestamper-offset=-28800000; JSESSIONID.b293f709=b2yoys8jav9d13d9psk1mb9g6");
		
		/**
		 * Option 2:Set Username and Password
		 */
//		Authenticator.setDefault(new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication("ganzhou", "Happy2222".toCharArray());
//			}
//			
//		});
		
		/**
		 * optional to invoke urlconn.connect()
		 */
//		urlconn.connect();
		
		/**
		 * Print out all of Header for HTTP Response message.
		 */
		urlconn.getHeaderFields().forEach((a,b) -> {
			System.out.print(a+"=");
			b.forEach(System.out::print);
			System.out.println();
		});
		
		System.out.println(urlconn.getResponseCode());
		
		/**
		 * Print out Content-Type: text/plain text/html ...
		 */
		BufferedReader bw = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
		bw.lines().forEach(System.out::println);
		
		/**
		 * put the inputstream to file.
		 */
//		InputStream input = urlconn.getInputStream();
//		Files.copy(input, Paths.get(new File("D:/c.zip").toURI()));
		
		
//		urlconn.getRequestProperties().forEach((a,b) -> {
//			System.out.print(a+"==");
//			b.forEach(System.out::print);
//			System.out.println();
//		});
		urlconn.disconnect();

	}

}
