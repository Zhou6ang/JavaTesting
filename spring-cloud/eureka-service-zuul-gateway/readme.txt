0. this service is using zuul as gateway service in cloud solution.
1. register this service to Eureka Server http://localhost:8888/eureka/
2. then you can check this service from Eureka Server: http://localhost:8888/
startup with application-dev.xml:
  1. request this service via http://localhost:8886/api-a/ from brower, and it will response with other content(baidu.com which defined in application-dev.xml).
  2. request this service via http://localhost:8886/anyService/hi from brower, and it will response with other service content(EUREKA-SERVICE-PROVIDER which defined in application-dev.xml).
startup with application.xml:
  1. request this service via http://localhost:8886/somepath/ from brower, and it will response with other website content through round robin.
  Note: MyRibbonConfiguration.java couldn't be same package with App.java, otherwise, the springboot couldn't startup and will report couldn't found IClientConfig bean.
