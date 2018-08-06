package com.ls.workqueues;

import java.io.IOException;

import com.ls.ContextUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv2 {

	private final static String QUEUE_NAME = "testhello";

	public static void main(String[] argv) throws Exception {
		Connection connection = ContextUtil.getConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.basicQos(1);//告诉服务器，在外面没有确认当前消息完成之间，不要给我在发型的消息
		
		System.out.println(" [消费者2] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("消费者2 接收： '" + message + "'");
			
				try {
					Thread.sleep(300);//模拟耗时
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		//注册消费者， 参数2 手动确认，代表我们收到消息后需要手动告诉服务器我们收到消息了
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}