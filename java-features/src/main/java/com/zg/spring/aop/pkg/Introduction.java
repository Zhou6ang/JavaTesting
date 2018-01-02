package com.zg.spring.aop.pkg;

public interface Introduction {
	void execute();
	
//could not be defined same method of any Beans which the Bean will be intercepted by AOP, otherwise it will throw stackoverflow error.
//	boolean logic_around(Parameter arg0, Parameter arg1);
//	public String logic_throw_exception(Parameter str0,Parameter str1);
}
