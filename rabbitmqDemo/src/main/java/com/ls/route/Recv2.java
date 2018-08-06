package com.ls.route;

import java.io.IOException;

import com.ls.ContextUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv2 {

	private final static String EXCHANGE_NAME = "testroute";//���彻����������

	public static void main(String[] argv) throws Exception {
		Connection connection = ContextUtil.getConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare("testroutequeue2", false, false, false, null);
		//�󶨶��е�������,����3���󶨵���������ʱ��ָ��һ����־��ֻ�к���һ������Ϣ�����յ�
		channel.queueBind("testroutequeue2", EXCHANGE_NAME, "key1");
		//���Ҫ���ն����־��ֻ��Ҫ��󶨼���
		channel.queueBind("testroutequeue2", EXCHANGE_NAME, "key3");
		
		channel.basicQos(1);
		
		
		System.out.println(" [������2] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("������2 ���գ� '" + message + "'");
			
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		//ע�������ߣ� ����2 �ֶ�ȷ�ϣ����������յ���Ϣ����Ҫ�ֶ����߷����������յ���Ϣ��
		channel.basicConsume("testroutequeue2", false, consumer);
	}
}