package com.zg.spring.aop;

import com.zg.spring.aop.pkg.Parameter;

public class DependencyBean{
	
	public void logic_before_after_around(){
		System.out.println("DependencyBean:do something in logic_before_after_around");
	}
	
	public String logic_after_returning(Parameter str){
		System.out.println("DependencyBean:do something in logic_after_returning:"+str.getId()+":"+str.getName());
		return "valueeee";
	}
	
	public String logic_throw_exception(Parameter str0,Parameter str1) {
		System.out.println("DependencyBean:throwing exception in logic_throw_exception:"+str0+","+str1);
		throw new RuntimeException("some customized exception.");
	}
	
	public boolean logic_around(Parameter str0,Parameter str1){
		System.out.println("DependencyBean:logic_around:"+str0+","+str1);
		showSomething(str0);
		return true;
	}

	private void showSomething(Parameter str0) {
		System.out.println("DependencyBean:private method showSomething:"+str0);
	}

	
}
