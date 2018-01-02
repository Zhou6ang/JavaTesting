package com.zg.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	new App().StreamDemo();
    	System.out.println("\n*******************************************");
    	System.out.println("*******************************************");
    	new App().FunctionsDemo();

    	
    }
    
    public void FunctionsDemo(){
    	System.out.println("========Predicate FunctionalInterface========");
    	Predicate<Integer> pre = (e)->e.intValue()>10;
    	
    	/**
    	 * and
    	 */
    	Predicate<Integer> pre1 = (e)->e.intValue()<50;
		System.out.println(pre.test(20));
		System.out.println(pre.and(e->e.intValue()<50).test(30));
		System.out.println(pre.and(pre1).test(30));
		
		/**
		 * or
		 */
		Predicate<Integer> pre2 = (e) -> e<-10;
		System.out.println(pre.or(pre2).test(30));
		System.out.println(pre.or(pre2).test(0));
		
		/**
		 * negate
		 */
		System.out.println(pre.negate().test(0));
		System.out.println(pre.negate().test(20));
		
		System.out.println("========Consumer FunctionalInterface========");
		Consumer<String> con = (e) -> System.out.println((e+"teSt").toUpperCase());;
		con.accept("abcdef");
		con.andThen((e)->System.out.println(e)).accept("fff");
		
		System.out.println("========Comparator FunctionalInterface========");
		Comparator<String> com = (a,b)->a.length()-b.length();
		System.out.println(com.compare("123456", "abc"));;
		System.out.println(com.reversed().compare("123456", "abc"));
		Comparator<String> com1 = (a,b)->a.charAt(0)-b.charAt(0);
		System.out.println(com.thenComparing(com1).compare("abc", "ecd"));
		System.out.println(com.thenComparingInt(e->e.charAt(0)).compare("abc", "ecd"));
		
		System.out.println("========Function FunctionalInterface========");
		Function<String, Integer> fun = (e) ->e.length();
		System.out.println("length:"+fun.apply("abc"));
		/**
		 * after <fun> done and run <fun1>
		 */
		Function<Integer, String> fun1 = (e) ->"length:"+e+" "+UUID.randomUUID().toString();
		System.out.println(fun.andThen(fun1).apply("abc"));
		
		/**
		 * before <fun> start and run <fun2>
		 */
		Function<Integer, String> fun2 = (e) -> "String:"+e;
		System.out.println("length:"+fun.compose(fun2).apply(100));
    }
    
    public void StreamDemo(){
    	System.out.println("========Stream.distinct========");
    	//remove duplicate
    	Arrays.asList(1,2,3,3,4,5,5,5,8,8,7,7,1,1).stream().distinct().forEach(e->System.out.print(e+" "));
    	System.out.println("\n========Stream.limit========");
    	IntStream.range(0, 100).limit(10).forEach(e->System.out.print(e+" "));
    	System.out.println("\n========Stream.skip========");
    	//skip front 10 numbers.
    	IntStream.range(0, 20).skip(10).forEach(e->System.out.print(e+" "));
    	System.out.println("\n========Stream.flatMap========");
    	IntStream.range(0, 5).flatMap(e->{return IntStream.of(e,1111,2222);}).forEach(System.out::println);
    	List<A> demo = Arrays.asList(new A(1, "a", 10),new A(2, "b", 20),new A(3, "c", 30));
    	System.out.println("========Stream.peek========");
    	//output A which is added "demo" in name for each one.
    	demo.parallelStream().peek(e->{e.setName(e.getName()+"demo");}).forEach(System.out::println);
    	System.out.println("========Stream.filter========");
    	//output age which > 20
    	demo.parallelStream().filter(e->e.getAge()>20).forEach(System.out::println);
    	System.out.println("========Stream.map========");
    	//output name;
    	demo.parallelStream().map(A::getName).forEach(System.out::println);
    	
    	System.out.println("========Stream All========");
    	//age+10 ---> age>20 ---> construct stream obj --->remove duplicate ---> map ---> output.
    	demo.parallelStream().peek(e->e.setAge(e.getAge()+10)).filter(e->e.getAge()>20).flatMap(e->Stream.of(e,new Integer(4),new Integer(6),new Integer(6),new Integer(5))).distinct().map(e->{
    		if(e instanceof Integer){
    			return new A(((Integer) e).longValue(),"test-"+e,(int)(Math.random()*100));
    		}else{
    			return e;
    		}
    	}).forEach(System.out::println);
    	
    	System.out.println("========Stream.collect.arraylist========");
    	//if no parallel,then no need to combiner method in collect.<R> R collect(Supplier<R> supplier,ObjIntConsumer<R> accumulator,BiConsumer<R, R> combiner);
    	IntStream.range(0, 30).collect(ArrayList::new, (list,e)->{list.add(e);}, (a,b)->{}).forEach(e->System.out.print(e+" "));
    	
    	//if it's parallel,then must has combiner method in collect, due to solve conflict.
    	/**
    	 * Wrong practice,due to no logic for (a,b)->{}
    	 */
    	System.out.println();
    	IntStream.range(0, 50).parallel().collect(ArrayList::new, (list,e)->{list.add(e);},(a,b)->{}).forEach(e->System.out.print(e+" "));
    	/**
    	 * should have implemented BiConsumer<R, R> combiner interface.
    	 */
    	System.out.println();
    	IntStream.range(0, 30).parallel().collect(ArrayList::new, (list,e)->{list.add(e);}, (mainList,otherList)->{mainList.addAll(otherList);}).forEach(e->System.out.print(e+" "));
    	System.out.println("\n========Stream.collect.hashmap========");
    	demo.parallelStream().collect(HashMap::new, (map,e)->{map.put(e.getId(), e);}, (mainMap,otherMap)->{mainMap.putAll(otherMap);}).values().forEach(System.out::println);
    	System.out.println();
    	demo.parallelStream().collect(Collectors.toMap(e->e.getId(), v->v)).values().forEach(System.out::println);
    	
    	System.out.println("========Stream.reduce========");
    	System.out.println(Arrays.asList(new A(1, "a", 10),new A(2, "b", 20),new A(3, "c", 30)).parallelStream().reduce((main,e)->{main.setAge(main.getAge()+e.getAge());return main;}).get().getAge());
    	//Error practice since it's parallelStream and should implement combiner for reduce.
    	System.out.println(Arrays.asList(new A(1, "a", 10),new A(2, "b", 20),new A(3, "c", 30)).parallelStream().reduce(new A(), (main,e)->{main.setAge(main.getAge()+e.getAge());return main;}).getAge());
     	
    	System.out.println(Arrays.asList(new A(1, "a", 10),new A(2, "b", 20),new A(3, "c", 30)).parallelStream().mapToInt(e->e.getAge()).sum());
    	
    	//TODO need investigate.
//    	System.out.println("***********************");
//		System.out.println(Arrays.asList(new A(1, "a", 5), new A(2, "b", 8), new A(3, "c", 15)).parallelStream()
//				.reduce(new A(), (main, e) -> {
//					System.out.println(Thread.currentThread().getName()+":" + main.getAge() + " " + e.getAge());
//					try {
//						Thread.sleep(e.getAge() * 1000);
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					main.setAge(main.getAge()+e.getAge());return main;},(main,e)->{System.out.println(Thread.currentThread().getName()+":"+main.getAge()+" "+e.getAge());main.setAge(main.getAge()+e.getAge());return main;}));
    
    	
    	System.out.println("========Stream.sorted========");
		Arrays.asList(1,5,2,9,4,10,8).stream().sorted().forEach(System.out::print);
		
		
		//error, could not use parallel to sort.
		System.out.print("\nerror:");
		Arrays.asList(1,5,2,9,4,10,8).parallelStream().sorted().forEach(System.out::print);
		System.out.println("\n========Arrays.parallelSort========");
		Integer[] integer= {1,5,2,9,4,10,8};
		Arrays.parallelSort(integer);
		System.out.println(Arrays.toString(integer));
		
		System.out.println("\n========Stream.Collectors========");
		System.out.println("======1.Stream.Collectors.groupingBy========");
		 List<A> list = Arrays.asList(new A(1, "a", 10),new A(2, "b", 20),new A(3, "c", 30),new A(4, "a", 10));
		 //Collectors.groupingBy(key),default value is List
		 Map<Integer, List<A>> map = list.parallelStream().collect(Collectors.groupingBy(e->e.getAge()));
		 map.forEach((a,b)->{System.out.println("key:"+a+",value:"+b);});
		 
		//Collectors.groupingBy(key,value)
		 Map<Integer, Map<Long,App.A>> map1 = list.parallelStream().collect(Collectors.groupingBy((A e)->e.getAge(), Collectors.toMap((A e1)->e1.getId(),(A e2)->e2)));
		 map1.forEach((k,v)->{
			 System.out.println("key:"+k+",value"+v);
		 });
		 
		//Collectors.groupingBy(key,map,value),map only extends from Map factory.
		 TreeMap<Integer, Map<Long, A>> treemap = list.parallelStream().collect(Collectors.groupingBy((A e)->e.getAge(),TreeMap::new,Collectors.toMap((a)->a.getId(),(a1)->a1)));
		 treemap.forEach((k,v)->{
			 System.out.println("key:"+k+", value:"+v);
		 });
		 System.out.println("======2.Stream.Collectors.partitioningBy========");
		 //Collectors.partitioningBy(predicate),key could not be changed, default value is List.
		 Map<Boolean, List<A>> partitionMap = list.parallelStream().collect(Collectors.partitioningBy((A e)->e.getAge()>20));
//		 System.out.println(partitionMap.get(true));
		 partitionMap.forEach((k,v)->{
			 System.out.println(k+"="+v);
		 });
		//Collectors.partitioningBy(predicate,value),key could not be changed, changed value to List.
		 Map<Boolean, Map<Long, A>> partitionMap1 = list.parallelStream().collect(Collectors.partitioningBy(e->e.getAge()>20, Collectors.toMap((A e)->e.getId(), (A v)->v)));
		 partitionMap1.forEach((k,v)->{
			 System.out.println(k+"="+v);
		 });
    }
    static class A{
    	long id;
    	String name;
    	int age = 0;
    	public A() {
		}
    	public A(long id,String name,int age) {
    		this.id =id;
    		this.name = name;
    		this.age = age;
    	}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		@Override
		public String toString() {
			return "id:"+id+" name:"+name+" age:"+age;
		}
    	
		
    }
}
