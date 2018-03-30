0. Ribbon + Rest way to consume services which registed in Eureka Server.
1. the purpose of @EnableDiscoveryClient is that this service will using services which registed in Eureka Server.
2. the purpose of @LoadBalanced is making restTemplate with LoadBalanced when visit internal service of Eureka Server. 