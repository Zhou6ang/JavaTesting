package com.mycompany.app.activemq;

import java.time.LocalDateTime;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class Producer {
	/**
	 * should be downloading ActiveMQ and startup: http://activemq.apache.org
	 */
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Scheduled(fixedDelayString="${sendmsg.scheduled.time}")
	public void sendMsg() {
		
		jmsMessagingTemplate.convertAndSend(new ActiveMQQueue("mytest.queue"), "hi, active MQ ..."+LocalDateTime.now());
	}
}
