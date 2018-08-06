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

	private final static String EXCHANGE_NAME = "testtopic";//���彻����������

	public static void main(String[] argv) throws Exception {
		Connection connection = ContextUtil.getConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare("testtopicqueue1", false, false, false, null);
		//�󶨶��е�������,����3���󶨵���������ʱ��ָ��һ����־��ֻ�к���һ������Ϣ�����յ�
		channel.queueBind("testtopicqueue1", EXCHANGE_NAME, "key.*");
		//���Ҫ���ն����־��ֻ��Ҫ��󶨼���
		channel.queueBind("testtopicqueue1", EXCHANGE_NAME, "abc.#");
		
		channel.basicQos(1);
		
		
		System.out.println(" [������1] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("������1 ���գ� '" + message + "'");
			
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		//ע�������ߣ� ����2 �ֶ�ȷ�ϣ����������յ���Ϣ����Ҫ�ֶ����߷����������յ���Ϣ��
		channel.basicConsume("testtopicqueue1", false, consumer);
	}
}