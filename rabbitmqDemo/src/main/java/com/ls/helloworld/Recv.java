package com.ls.helloworld;

import java.io.IOException;

import com.ls.ContextUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv {
	
	private final static String QUEUE_NAME="testhello";
	
	public static void main(String[] argv) throws Exception {
		Connection connection = ContextUtil.getConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		
		
			Consumer consumer = new DefaultConsumer(channel) {
			  @Override
			  public void handleDelivery(String consumerTag, Envelope envelope,
			                             AMQP.BasicProperties properties, byte[] body)
			      throws IOException {
			    String message = new String(body, "UTF-8");
			    System.out.println(" [x] Received '" + message + "'");
			  }
		 };
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}