package com.zg.spring.aop;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class TestCases_Ioc {

	public static void main(String[] args) throws IOException {
		try(ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc-context.xml")){
			A app = (A)ctx.getBean("test");
			System.out.println("########Case 1: Bean normal case########");
			app.testing(1,2);
			System.out.println("########Case 2: Bean for [lookup-method] attribute########");
			((C)ctx.getBean("c")).createB().createCmd();
			System.out.println("########Case 3: Bean for [qualifier] attribute########");
			((D)ctx.getBean("d")).running();
			System.out.println("########Case 3: Bean for [MessageSource] attribute########");
			//message from properties handling
			System.out.println("message resource: "+ app.getMessageSource().getMessage("message", null,"default value",null));
			System.out.println("message resource: "+app.getMessageSource().getMessage("arg_message", new String[]{"arggg"},"default value",null));
		
			//files from resources handling.
		 //classpath,http,file; * means load all sprxxx.xsd file from all classpath.
//		 String str = ctx.getResource("classpath*:spring-beans.xsd").getFile().getAbsolutePath();
//		 System.out.println("Resource Path:"+str);
//		 Files.copy(ctx.getResource("https://intranet.nokia.com/sites/news/insidenews/2016/july/PublishingImages/Nokia_IM_Bridge01_336x252_normal.jpg").getInputStream(), Paths.get("D://a.jpg"));
		 
		}
		
	}
	
	
	public void run(){
		System.out.println("running ...");
	}

}

class A{
	private int age;
	private List<Object> list;
	private TestCases_Ioc refObj;
	private Resource res;
	private MessageSource messageSource;
	private Properties properties;
	private Properties propsfromfile;
	
	public void testing(int a, int b){
		System.out.println("age:"+age+" list:"+list);
		refObj.run();
		System.out.println(replace("haha~~",10));
		try {
			System.out.println("Resource: "+res.getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Properties: "+properties.getProperty("name"));
		System.out.println("Properties read from file immediately: "+propsfromfile.getProperty("error"));
	}
	public A(int a,int b,String arg0,String arg1) {
		System.out.println("constructor-arg:"+a+","+b+","+arg0+","+arg1);
	}

	public void setPropsfromfile(Resource res) throws IOException{
		propsfromfile = new Properties();
		propsfromfile.load(res.getInputStream());
	}
	
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = new Properties(properties);
	}
	public MessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public void setRes(Resource res) {
		this.res = res;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	public void setRefObj(TestCases_Ioc refObj) {
		this.refObj = refObj;
	}
	
	public void setAbc(String age) {
		System.out.println(age);
	}
	
	public String replace(String str){
		System.out.println("replace methodA:"+str);
		return null;
	}
	
	public String replace(String str,Integer str1){
		System.out.println("replace methodB:"+str);
		return null;
	}
	
}

class B implements MethodReplacer{

	private String flag;
	private String parentFlag;
	
	public void setParentFlag(String parentFlag) {
		this.parentFlag = parentFlag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
		System.out.println("element:[replaced-method]-here is invoking reimplement...");
		
		//could not use method invoke since here is replace source method to output result.
//		Object result = method.invoke(obj, args);
		
		return args[0];
	}
	
	public String createCmd(){
		System.out.println("element:[lookup-method]");
		return null;
	}
	
	
	public void qualifier(){
		System.out.println("element:[qualifier]-"+flag);
		System.out.println("Attribute:[Parent]-"+parentFlag);
	}
	
	public void initMethod(){
		System.out.println("Attribute:[init-method]");
	}
	
	public void destroyMethod(){
		System.out.println("Attribute:[destroy-method]");
	}
}

abstract class C{
	protected abstract B createB();
}


class D{
	
	private B b;
	
	public void setB(@Qualifier("b1")B b){
		this.b=b;
	}
	public void running(){
		b.qualifier();
	}
}

interface AA{
	
}
