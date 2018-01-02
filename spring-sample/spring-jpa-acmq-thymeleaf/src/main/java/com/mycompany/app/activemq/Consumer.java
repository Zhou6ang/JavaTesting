package com.mycompany.app.activemq;

import java.time.LocalDateTime;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	
	/**
	 * should be downloading ActiveMQ and startup: http://activemq.apache.org
	 */
	
	@JmsListener(destination="mytest.queue")
	@SendTo("annotation.queue")
	public String receivedMsg(String text) {
		System.out.println("Received msg : "+text);
		return "hi, activeMQ-2: if you want to using @SendTo to send out msg, then it must be combining with @JmsListener -_-!!!..."+LocalDateTime.now();
	}
	
	@JmsListener(destination="annotation.queue")
	public void receivedMsgFromAnnotationQueue(String text) {
		System.out.println("Received msg-1 : "+text);
	}
	
}
