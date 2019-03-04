package org.zg.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.zg.abc.MyRibbonConfiguration;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@RibbonClient(name="anyName",configuration= {MyRibbonConfiguration.class})//here anyName is matched with zuul.routes.[anyName].path
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }
    
}
