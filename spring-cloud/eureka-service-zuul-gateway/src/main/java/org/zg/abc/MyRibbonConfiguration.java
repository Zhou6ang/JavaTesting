package org.zg.abc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.PollingServerListUpdater;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

@Configuration
public class MyRibbonConfiguration {
    
    private static Logger logger = LoggerFactory.getLogger(MyRibbonConfiguration.class);
    
    @Autowired
    private IClientConfig clientConfig;
    
    @Bean
    public BaseLoadBalancer loadbalancer(final IPing ping, final IRule rule, final ServerList<Server> serverServerList, final ServerListFilter<Server> serverListFilter) {
        logger.debug("BaseLoadBalancer: here is some loadbalancer implementation.");
        BaseLoadBalancer loadBalancer = new ZoneAwareLoadBalancer<>(clientConfig, rule, ping, serverServerList, serverListFilter, new PollingServerListUpdater(clientConfig));
        loadBalancer.setPingInterval(10);
        return loadBalancer;
    }
    
    @Bean
    public IPing myPing() {
        clientConfig.getProperties().forEach((k,v)->{
            logger.debug("IClientConfig: {}->{}",k,v);
        });
        return new PingUrl(true, "test");
    }
    
    @Bean
    public ServerList<Server> myServerList(){
        return new ServerList<Server>() {

            @Override
            public List<Server> getInitialListOfServers() {
                
                return new ArrayList<>();
            }

            @Override
            public List<Server> getUpdatedListOfServers() {
                return Arrays.asList(new Server("www.baidu.com", 80),new Server("www.qq.com", 80),new Server("sports.qq.com", 80));
            }
            
        };
    }
}
