package org.zg.app.feign.client;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class HystrixClientFallbackFactory implements FallbackFactory<MyFeignClient>{

    @Override
    public MyFeignClient create(Throwable cause) {
        cause.printStackTrace();
        return ()->"this is Hystrix example for CircuitBreaker: "+cause.getMessage();
    }

}
