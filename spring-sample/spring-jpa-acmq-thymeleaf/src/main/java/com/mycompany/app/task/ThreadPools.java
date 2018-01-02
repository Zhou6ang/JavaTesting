package com.mycompany.app.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class ThreadPools {
	
	private ExecutorService es_1;
	private ExecutorService es_2;
	private ExecutorService es_3;
	
	
	@PostConstruct
	public void initialContext() {
		es_1 = Executors.newFixedThreadPool(2, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "My-Thread-Pool-1");
			}
		});

		es_2 = Executors.newFixedThreadPool(2, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "My-Thread-Pool-2");
			}
		});
		es_3 = Executors.newFixedThreadPool(2, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "My-Thread-Pool-3");
			}
		});
	}
	
	@PreDestroy
	public void preDestroy() {
		/**
		 * es_2.shutdownNow(), thread pool will be shutdown directly, and return inside tasks of thread pool queue.
		 * 
		 */
//		System.out.println("######Tasks have not been executed-1: "+es_1.shutdown());
//		System.out.println("######Tasks have not been executed-2: "+es_2.shutdownNow());
//		System.out.println("######Tasks have not been executed-3: "+es_3.shutdownNow());
		
		/**
		 * es_1.shutdown(), thread pool will be shutdown until submitted task completed and not accepted new submitted tasks.
		 * es_1.awaitTermination(1, TimeUnit.SECONDS), it just to check status of thread pool.
		 * if you want to wait for submitted tasks totally completed,
		 * then it should be using es_1.shutdown() combining with es_1.awaitTermination(1, TimeUnit.SECONDS) as below example.
		 */
		System.out.println("@####### thread pool will be shutdown ...");
		es_1.shutdown();
		es_2.shutdown();
		es_3.shutdown();
		try {
			while(!es_1.awaitTermination(1, TimeUnit.SECONDS)) {
				System.out.println("not shutdown");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//just used for check thread pool status is shutdown or not, no shutdown function, if need to shutdown, pls using shutdown() method.
//		try {
//			System.out.println(es_1.awaitTermination(3, TimeUnit.SECONDS));
//			System.out.println(es_2.awaitTermination(3, TimeUnit.SECONDS));
//			System.out.println(es_3.awaitTermination(3, TimeUnit.SECONDS));
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	public ExecutorService getEs_1() {
		return es_1;
	}

	public ExecutorService getEs_2() {
		return es_2;
	}

	public ExecutorService getEs_3() {
		return es_3;
	}
	
	
public static void main(String[] args) throws InterruptedException {
	ExecutorService es_1 = Executors.newFixedThreadPool(2, new ThreadFactory() {

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "My-Thread-Pool-1");
		}
	});
	
	es_1.submit(()->{
		while(true) {
			Thread.sleep(1000);
			System.out.println("working ...");
		}
	});
	es_1.shutdown();
	while(!es_1.awaitTermination(1, TimeUnit.SECONDS)) {
		System.out.println("not shutdown");
	}
	}

}
