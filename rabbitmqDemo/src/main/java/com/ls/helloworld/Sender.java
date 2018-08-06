package com.ls.helloworld;

import com.ls.ContextUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

	private final static String QUEUE = "testhello";

	public static void main(String[] args) throws Exception {
		Connection connection = ContextUtil.getConnection();

		for (int i = 0; i < 10; i++) {
			Channel channel = connection.createChannel();

			channel.queueDeclare(QUEUE, false, false, false, null);
			String message = "Hello World:" + System.currentTimeMillis();
			channel.basicPublish("", QUEUE, null, message.getBytes());

			System.out.println(" [x] Sent '" + message + "'");

			Thread.sleep(1000);

			channel.close();
		}
		connection.close();
	}
}
