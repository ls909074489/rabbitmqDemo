package com.ls.workqueues;

import com.ls.ContextUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

	private final static String QUEUE = "testhello";

	public static void main(String[] args) throws Exception {
		Connection connection = ContextUtil.getConnection();

		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE, false, false, false, null);
		
		for(int i=0;i<100;i++){
			String message = "Hello World:" + i;
			channel.basicPublish("", QUEUE, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		channel.close();
		connection.close();
	}
}
