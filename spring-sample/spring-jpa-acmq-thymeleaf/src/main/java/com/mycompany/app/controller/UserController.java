package com.mycompany.app.controller;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.app.config.AppConfig;
import com.mycompany.app.model.User;
import com.mycompany.app.model.User1;
import com.mycompany.app.model.User2;
import com.mycompany.app.model.UserInfo;
import com.mycompany.app.model.UserInfo1;
import com.mycompany.app.service.UserService;
import com.mycompany.app.service.impl.ComponentAnn;
import com.mycompany.app.service.impl.OtherService;
import com.mycompany.app.task.ThreadPools;

@RequestMapping("/app")
@RestController
public class UserController {

	@Autowired
	@Qualifier("a")
	private UserService us;
	
	@Autowired
	private OtherService os;
	
	@Autowired
	private UserInfo userinfo;
	
	@Autowired
	private User user;
	
	@Autowired
	private UserInfo1 userInfo1;
	
	@Autowired
	private ComponentAnn componentAnn;
	
	@Autowired
	private User1 user1;
	
	@Autowired
	private User2 user2;
	
	@Autowired
	private AppConfig appConfig;
	
	@Value("${k1.k2.k3}")
	private String prop1;
	
	@Value("${server.port}")
	private String prop2;
	
	@Value("${app.map.b}")
	private Object prop3;
	
	@Autowired
	private ThreadPools tp;
	
	
	@RequestMapping("/userinfos")
	public String getUserInfos() {
		return os.getUserInfo();
	}
	
	@RequestMapping("/username")
	public String getUserName(){
		return us.getUserName();
	}
	
	/**
	 * http://127.0.0.1:8080/app/user and http://127.0.0.1:8080/app/userinfo
	 * should have same User instance(Bean) since using @Configutation annotation in ConfigurationAnn.java
	 */
	
	@RequestMapping("/userinfo")
	public UserInfo getUserinfo(){
		return userinfo;
	}
	
	@RequestMapping("/user")
	public User getUser(){
		//here user instance == user instance of UserInfo
		return user;
	}
	
	/**
	 * http://127.0.0.1:8080/app/user1 and http://127.0.0.1:8080/app/userinfo1
	 * should have not same User instance(Bean) since using @Component annotation in Component.java
	 */
	
	@RequestMapping("/userinfo1")
	public UserInfo1 getUserinfo1(){
		return userInfo1;
	}
	
	@RequestMapping("/user1")
	public User1 getUser1(){
		//here user instance != user instance of UserInfo
		return user1;
	}
	
	//when @Bean defined inside of @Component, as we know we can get instance of @Bean annotation, besides we can also get instance of @Component.
	@RequestMapping("/componentMethodResult")
	public String getInstanceOfComponentAnnInvokeMethodResult() {
		return componentAnn.getInstance();
	}
	
	/**
	 * Test using @Bean annotation stand alone, which means out of scope for @Configuration||@Component||@Service||@Repository||@Controller
	 * result: Could not startup with error: Field user2 in com.mycompany.app.controller.UserController required a bean of type 'com.mycompany.app.model.User2' that could not be found.
	 */
	@RequestMapping("/standaloneBean")
	public String standaloneBean(){
		return user2.getName();
	}
	
	/**
	 * Test application.yml file
	 * 
	 */
	@RequestMapping("/config/list")
	public Object getListOfConfigurationFile() {
		return appConfig.getList();
	}
	
	@RequestMapping("/config/map")
	public Object getMapOfConfigurationFile() {
		return appConfig.getMap();
	}
	
	@RequestMapping("/config/complexlist")
	public Object getcomplexListOfConfigurationFile() {
		return appConfig.getComplexList();
	}
	
	@RequestMapping("/config/complexmap")
	public Object getcomplexMapOfConfigurationFile() {
		return appConfig.getComplexMap();
	}
	
	/**
	 * when execute: java -jar -Dspring.profiles.active=a xxx-xxx-xxx-SNAPSHOT.jar
	 * the application-a.yml will be effective and some properties of application.yml will
	 * be overridden by application-a.yml, so here will be showing "here is application-a.yml"
	 * which from application-a.yml file, not application.yml file.
	 */
	@RequestMapping("/config/prop1")
	public Object getProp1OfConfigurationFile() {
		return prop1;
	}
	
	/**
	 * 
	 * server.port property will be overridden by application-a.yml also.
	 */
	@RequestMapping("/config/prop2")
	public Object getProp2OfConfigurationFile() {
		return prop2;
	}
	
	@RequestMapping("/config/prop3")
	public Object getProp3OfConfigurationFile() {
		return prop3;
	}
	
	/**
	 * test threadpool if there are many requests coming.
	 */
	@RequestMapping("/task/submit")
	public Object submitTasks() {
		//simulate submit 10 tasks
		IntStream.range(0, 10).forEach(e->{
			//tp.getEs_1().submit();
			//tp.getEs_2().submit();
			//tp.getEs_3().submit();
		});
		
		return "success";
	}
	
	
}
