package org.zg.app.feign.client;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.cloud.netflix.feign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.netflix.feign.ribbon.LoadBalancerFeignClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.netflix.config.ConfigurationManager;

import feign.Client;
import feign.Feign;
import feign.Request;
import feign.Retryer;

@Configuration
public class MyConfiguration {
      /** The property to set the list of alarm servers. */
      private static final String LIST_OF_SERVERS_PROPERTY = "eureka-service-provider.ribbon.listOfServers";


      /**
       * Request options for the Feign http client.
       *
       */
      @Bean
      Request.Options requestOptions() {
        return new Request.Options(3000, 3000);
      }

      /**
       * The Feign retryer handles the retry logic for the HTTP requests.
       *
       */
      @Bean
      public Retryer retryer() {
        return new Retryer.Default(100, 100, 2);
      }

      /**
       * Feign builder. It disables the Hystrix supports
       *
       * @return the feign builder
       */

//      @Bean
//      @Scope("prototype")
//      public Feign.Builder feignBuilder() {
//        return Feign.builder();
//      }

      /**
       * Need to override default @LoadBalancerFeignClient to ignore the SSL cert.
       *
       */
      @Bean
      public Client feignClient(final CachingSpringLoadBalancerFactory cachingFactory, final SpringClientFactory clientFactory) {
        ConfigurationManager.getConfigInstance().setProperty(LIST_OF_SERVERS_PROPERTY, "127.0.0.1:1111");
        Client client = null;
        if (true) {
          client = new Client.Default(allTrustSocketFactory(), new HostnameVerifier() {
            @Override
            public boolean verify(final String hostname, final SSLSession sslSession) {
              return true;
            }
          });
        } else {
          client = new Client.Default(null, null);
        }
        return new LoadBalancerFeignClient(client, cachingFactory, clientFactory);
      }

      private SSLSocketFactory allTrustSocketFactory() {

        final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
          }

          @Override
          public void checkServerTrusted(final java.security.cert.X509Certificate[] chain, final String authType) throws CertificateException {
          }

          @Override
          public void checkClientTrusted(final java.security.cert.X509Certificate[] chain, final String authType) throws CertificateException {
          }
        } };

        try {
          final SSLContext sslContext = SSLContext.getInstance("TLS");
          sslContext.init(null, trustAllCerts, null);
          return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
          throw new RuntimeException(e);
        }
      }
}