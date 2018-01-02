package com.zg.dynamic.proxy;

import java.lang.reflect.Proxy;
import java.util.stream.IntStream;

public class Demo {

	public static void main(String[] args) throws Exception {
		new Demo().run();
	}
	
	public void run(){
		Person p = new Worker();
		Person w = proxy(p);
		w.name("farmer");
		w.counting(1, 10);
		w.work("just testing...");
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public  <T> T proxy(T anInterface) {
		
		if(anInterface.getClass().getInterfaces().length == 0){
			throw new RuntimeException("The "+anInterface.getClass().getName()+" is not implements a interface. Could's use Proxy.");
		}
		
		return (T)Proxy.newProxyInstance(anInterface.getClass().getClassLoader(), anInterface.getClass().getInterfaces(),
				(proxy, method, args) -> {
					method.isDefault();
					System.out.println("Method invoking bbbbbbbbbbbbefore.");
					Object obj = method.invoke(anInterface, args);
					System.out.println("Method invoked aaaaaaaaaaafter.");
					
					return obj;
				});
	}

}
interface Person{
	void name(String name);
	int counting(int a,int b);
	Object work(String input);
}
class A{
	public void name(String name) {
		System.out.println(name);
	}
}
class Worker implements Person{
	
	public int id = 0;
	
	@Override
	public void name(String name) {
		System.out.println(name);
	}


	@Override
	public int counting(int a, int b) {
		IntStream.range(0, 10).forEach(e1 ->{
			System.out.println(++id);
		});
		return id;
	}


	@Override
	public Object work(String input) {
		System.out.println("here is working ....");
		return new Integer(100);
	}
	
}