package com.zg.spring.aop.pkg;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class MyAspect {

	public void beforeAdvice(JoinPoint joinPoint){
		//same with beforeAdvice(), JoinPoint is optional.
		// beforeAdvice() is high priority than beforeAdvice(JoinPoint joinPoint).
		System.out.println("[AOP-before]: before adviceeeeee...###"+joinPoint);
	}
	
//	public void beforeAdvice(){
//		System.out.println("[AOP]: before adviceeeeee with no args...");
//	}
	
	public void beforeAdviceWithArgs(JoinPoint joinPoint,Parameter a,Object b){
		//same with beforeAdviceWithArgs(Object a,Object b), JoinPoint is optional.
		// beforeAdviceWithArgs(Object a,Object b) is high priority than beforeAdvice(JoinPoint joinPoint) if they are appearing at same time.
		
		for (Object arg : joinPoint.getArgs()) {
			if(arg instanceof Parameter){
				((Parameter) arg).setId(8888);
				((Parameter) arg).setName("AOP setting");
			}
		}
		System.out.println("[Aspect Bean]:para1:"+a+", para2:"+b);
		System.out.println("[AOP-before]: before adviceeeeee with args...###"+a+"###"+b+"###"+joinPoint);
	}
	
	//Or it can be beforeAdviceWithArgs(JoinPoint joinPoint,Object a,Object b)
//	public void beforeAdviceWithArgs(Object a,Object b){
//		System.out.println("[AOP-before]: before adviceeeeee with args...###"+a+"###"+b);
//	}
	
	public void beforeAdviceWithArgs_this(JoinPoint joinPoint, Object p1,Object p2,Object here_is_this){
		//since types-matching of declare-parents,so the matched Bean will be implemented Introduction interface.
		((Introduction)here_is_this).execute();
		for (Class clz : here_is_this.getClass().getInterfaces()) {
			System.out.println("[Aspect Bean]:"+clz.getName());
		}
		System.out.println("[AOP-before]: before adviceeeeee with this(p0)...###"+here_is_this+";"+joinPoint);
	}
	
	public void beforeAdviceWithArgs_target(Object p1,Object p2,Object here_is_target){
		
		for (Class clz : here_is_target.getClass().getInterfaces()) {
			System.out.println("[Aspect Bean]:"+clz.getName());
		}
		System.out.println("[AOP-before]: before adviceeeeee with target(p1)...###"+here_is_target);
	}
	
	public void afterAdvice(JoinPoint joinPoint){
		
		String s = "";
		for (Object arg : joinPoint.getArgs()) {
			if(arg instanceof Parameter){
				s += ((Parameter) arg).getId()+","+((Parameter) arg).getName();
			}
		}
		System.out.println("[AOP-after]: after adviceeeeee with default args...###"+s+"###"+joinPoint);
		
	}
	public void afterAdvice(){
		System.out.println("[AOP-after]: after adviceeeeee with no args...");
	}
	
	public void afterAdviceWithArgs(JoinPoint joinPoint,Object obj0,Object obj1){
		System.out.println("[AOP-after]: after adviceeeeee with args...###"+obj0+"###"+obj1+";"+joinPoint);
	}
	
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		
		String s = "input: ";
		for (Object arg : joinPoint.getArgs()) {
			if(arg instanceof Parameter){
				s += ((Parameter) arg).getId()+","+((Parameter) arg).getName();
			}
		}
		System.out.println("[AOP-around]: start around adviceeeeee...###"+s+"###"+joinPoint);
		Object output = joinPoint.proceed(joinPoint.getArgs());
		System.out.println("[AOP-around]: end around adviceeeeee...###output:"+output+"###"+joinPoint);
		return output;
	}
	
	public Object aroundAdviceWithArgs(ProceedingJoinPoint joinPoint,Object joinpointPara0,Object joinpointPara1) throws Throwable{
		
		System.out.println("[AOP-around]: start around adviceeeeee...###"+joinpointPara0+"###"+joinpointPara1);
		System.out.println("[Aspect Bean]: Want to change id=1111 and name=... in some input parameter before passing to method.");
		if(joinpointPara0 instanceof Parameter){
			((Parameter)joinpointPara0).setId(1111);
			((Parameter)joinpointPara0).setName("changed before passing to method");
		}
		Object output = joinPoint.proceed(joinPoint.getArgs());
		System.out.println("[AOP-around]: end around adviceeeeee...###output:"+output+"###"+joinPoint+",Kind:"+joinPoint.getKind()+",Method:"+joinPoint.getSignature().getName()+",Signature:"+joinPoint.getSignature()+",StaticPart:"+joinPoint.getStaticPart());
		System.out.println("[Aspect Bean]: Want to change return value after method execution done, now is:false, original is:"+output);
		output = false;
		return output;
	}
	
	public void afterReturningAdvice(JoinPoint arg0,Object retValue){
		System.out.println("[AOP-after-returning]: after returning adviceeeeee...###"+retValue+"###"+arg0);
	}
	
	public void afterReturningAdviceWithArgs(Object inputPara0,Object inputPara1,Object outputOfJoinpoint){
		System.out.println("[AOP-after-returning]: after returning adviceeeeee...###"+inputPara0+"###"+inputPara1+" >>> "+outputOfJoinpoint);
	}
	
	public void afterThrowingAdvice(JoinPoint arg0,Object throwingValue){
		System.out.println("[AOP-after-throwing]: after throwing adviceeeeee...###"+throwingValue+"###"+arg0);
	}
	
	public void afterThrowingAdviceWithArgs(JoinPoint arg0,Object inputPara0,Object inputPara1,Object throwingOfJoinpoint){
		System.out.println("[AOP-after-throwing]: after throwing adviceeeeee...###"+inputPara0+"###"+inputPara1+">>>"+throwingOfJoinpoint+";"+arg0);
	}
}
