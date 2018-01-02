package com.zg.spring.aop.pkg;

public class SpringIntroductionFeature implements Introduction{

	public void execute(){
		System.out.println("[Inject Interface]:here is default implementation for Introduction interface.");
	}

	//could not be defined same method of any intercepted Beans, otherwise it will throw stackoverflow error.
//	@Override
//	public boolean logic_around(Parameter arg0, Parameter arg1) {
//		return false;
//	}
//
//	@Override
//	public String logic_throw_exception(Parameter str0, Parameter str1) {
//		return "fack code ...";
//	}

}
