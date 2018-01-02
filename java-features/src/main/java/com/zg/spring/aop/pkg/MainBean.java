package com.zg.spring.aop.pkg;

import org.springframework.aop.framework.AopContext;

import com.zg.spring.aop.DependencyBean;

public class MainBean {
	private DependencyBean b2;
	private String contextValue;
	
	public String getContextValue() {
		return contextValue;
	}
	public void setContextValue(String contextValue) {
		this.contextValue = contextValue;
	}
	public void setB2(DependencyBean b2){
		this.b2 = b2;
	}
	public void logic_before_after_around() {
		System.out.println("MainBean: do something in logic_before_after_around");
//		b2.logic_after_returning(new Parameter());
//		try {
//			b2.logic_throw_exception(new Parameter(), new Parameter());
//		} catch (Exception e) {
////			e.printStackTrace();
//		}
		System.out.println("\r\n########case 1: AOP normal case(pointcut,advice,aspect,tx)#########");
		b2.logic_around(new Parameter(), new Parameter());
		logic_3("[Without advice]:1", "2");
		System.out.println("\r\n########case 2: AOP valid for method call of inside Bean,set expose-proxy=\"true\" and using AopContext.currentProxy()#########");
		//if invoke logic_3 directly, then logic_3 couldn't be intercepted(advised) by AOP framework.
        //this.logic_3(..);
        //1. set expose-proxy="true" 2. then logic_3 will be intercepted(advised) by AOP framework as below.
		((MainBean)AopContext.currentProxy()).logic_3("[With advice]: since set expose-proxy=\"true\"", "Used AopContext.currentProxy()");
		System.out.println("\r\n########case 3: AOP not valid for private|protected|default method#########");
		((MainBean)AopContext.currentProxy()).showSomething("[Without advice]: even set expose-proxy=\"true\"", "Used AopContext.currentProxy()");
	}
	
	public void logic_throwing_exception() {
		System.out.println("MainBean: do something in logic_throwing_exception(..)");
		b2.logic_after_returning(new Parameter());
		try {
			System.out.println(b2.logic_throw_exception(new Parameter(), new Parameter()));
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	public void logic_3(String str0,String str1){
		System.out.println("MainBean:do something in logic_3(..) "+str0+" and "+str1);
		showSomething(str0, str1);
	}

	private void showSomething(String str0,String str1) {
		System.out.println("MainBean:private method showSomething(..) "+str0+" and "+str1);
	}
	
}
