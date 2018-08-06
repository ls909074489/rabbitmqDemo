package com.ls.topic;

import java.io.IOException;

import com.ls.ContextUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv1 {

	private final static String EXCHANGE_NAME = "testtopic";//定义交换机的名称

	public static void main(String[] argv) throws Exception {
		Connection connection = ContextUtil.getConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare("testtopicqueue1", false, false, false, null);
		//绑定队列到交换机,参数3：绑定到交换机的时候指定一个标志，只有和它一样的消息才能收到
		channel.queueBind("testtopicqueue1", EXCHANGE_NAME, "key.*");
		//如果要接收多个标志，只需要多绑定即可
		channel.queueBind("testtopicqueue1", EXCHANGE_NAME, "abc.#");
		
		channel.basicQos(1);
		
		
		System.out.println(" [消费者1] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("消费者1 接收： '" + message + "'");
			
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		//注册消费者， 参数2 手动确认，代表我们收到消息后需要手动告诉服务器我们收到消息了
		channel.basicConsume("testtopicqueue1", false, consumer);
	}
}