                                     |------------------> Web Server 01(Tomcat,Jetty)
Client(~3w/s)   --------------> Nginx|------------------> Web Server 02(Tomcat,Jetty)
                                     |------------------> Web Server 03(Tomcat,Jetty)

 
                                              |------------------> Web Server 01(Tomcat,Jetty)
                               |---->Nginx-01 |------------------> Web Server 02(Tomcat,Jetty)
                               |              |------------------> Web Server 03(Tomcat,Jetty)
Client(~5w/s)  ----------->LVS |
                               |              |------------------> Web Server 04(Tomcat,Jetty)
                               |---->Nginx-02 |------------------> Web Server 05(Tomcat,Jetty)
                                              |------------------> Web Server 06(Tomcat,Jetty)




                                                             |----> Web Server 01(Tomcat,Jetty)
                                              |-----Apache-01|----> Web Server 02(Tomcat,Jetty)
                                              |              |----> Web Server 03(Tomcat,Jetty)
                               |---->Nginx-01 |
                               |              |              |----> Web Server 04(Tomcat,Jetty)
                               |              |-----Apache-02|----> Web Server 05(Tomcat,Jetty)
                               |                             |----> Web Server 06(Tomcat,Jetty)
Client(>10w/s)  ---------->LVS |
                               |                             |----> Web Server 07(Tomcat,Jetty)
                               |              |-----Apache-03|----> Web Server 08(Tomcat,Jetty)
                               |              |              |----> Web Server 09(Tomcat,Jetty)
                               |---->Nginx-02 |
                                              |              |----> Web Server 10(Tomcat,Jetty)
                                              |-----Apache-04|----> Web Server 11(Tomcat,Jetty)
                                                             |----> Web Server 12(Tomcat,Jetty)