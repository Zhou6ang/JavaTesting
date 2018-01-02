package com.zg.http_https.urlconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.Proxy.Type;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

public class Https {

	public static void main(String[] args) throws Exception {
//		HttpsUrlConnectionWithoutCA();
		HttpsUrlConnection(false);
	}

	
	public static void HttpsUrlConnection(boolean openProxy) throws IOException {
		/**
		 * download zip with HTTPs
		 */
//		URL url = new URL("https://eslinv70.emea.nsn-net.net:8080/job/med_n16-5_dynamic-adaptation_ris/ws/implementation/NE3SWS-DynamicAdaptation-Mediation/dynamicAdaptation-pdds-se/target/dynamicAdaptation-pdds-se-1.0-installer.zip");
		
		/**
		 * Send a request with HTTPs
		 */
		URL url = new URL("https://svne1.access.nsn.com/isource/svnroot/nas_mediations/trunk/com.nsn.oss.dynamicadaptation-8/implementation/NE3SWS-DynamicAdaptation-Mediation/pom.xml");

		/**
		 * if https in server side is not in java trustStore, then set it as below.
		 * Set java env. with dedicate keyStore,trustStore.
		 * 1. get the certificate chain and put it as a xxx.cer|xxx.pem
		 * 2. import it to default java keyStore as: keytool -importcert -file a.cer
		 * 	  Or dedicate keyStore: keytool -importcert -file a.cer -keystore xxx.keystore -storepass 123456
		 */
		System.setProperty("javax.net.ssl.keyStoreType", "jks");
		System.setProperty("javax.net.ssl.keyStoreProvider", "SUN");
		System.setProperty("javax.net.ssl.keyStore", "c://Users//ganzhou//.keystore");
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		
		System.setProperty("javax.net.ssl.trustStoreProvider", "SUN");
		System.setProperty("javax.net.ssl.trustStoreType", "jks");
		System.setProperty("javax.net.ssl.trustStore", "c://Users//ganzhou//.keystore");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		
		/**
		 * Send a http request via Proxy.
		 */
		HttpURLConnection urlconn = null;
		if (openProxy) {
			urlconn = (HttpURLConnection) url
					.openConnection(new Proxy(Type.HTTP, new InetSocketAddress("10.144.1.10", 8080)));
		} else {
			urlconn = (HttpURLConnection) url.openConnection();
		}
		
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
	
	/**
     * This is some kind of test how to read from https url data with credentials
     *
     * @throws Exception
     */
    public static void HttpsUrlConnectionWithoutCA() throws Exception
    {
        
        URL url = new URL("https://svne1.access.nokiasiemensnetworks.com/isource/svnroot/nas_adaptdata/trunk/com.nsn.imscaf/com.nsn.imscaf-2.1x92/o2ml/src/main/resources/static");
        String userpass = "anonymous:password";


        try
        {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager()
            {
                @Override
                public void checkClientTrusted(final X509Certificate[] chain, final String authType)
                {
                }

                @Override
                public void checkServerTrusted(final X509Certificate[] chain, final String authType)
                {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            // All set up, we can get a resource through https now:
            final URLConnection urlCon = url.openConnection();
            String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes());
            urlCon.setRequestProperty("Authorization", basicAuth);
            // Tell the url connection object to use our socket factory which bypasses security checks
            ((HttpsURLConnection) urlCon).setSSLSocketFactory(sslSocketFactory);

            final InputStream input = urlCon.getInputStream();
            int c;
            while ((c = input.read()) != -1)
            {
                System.out.print((char) c);
            }
            input.close();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
}
