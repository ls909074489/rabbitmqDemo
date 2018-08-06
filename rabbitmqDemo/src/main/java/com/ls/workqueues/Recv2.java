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
		channel.basicQos(1);//���߷�������������û��ȷ�ϵ�ǰ��Ϣ���֮�䣬��Ҫ�����ڷ��͵���Ϣ
		
		System.out.println(" [������2] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("������2 ���գ� '" + message + "'");
			
				try {
					Thread.sleep(300);//ģ���ʱ
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		//ע�������ߣ� ����2 �ֶ�ȷ�ϣ����������յ���Ϣ����Ҫ�ֶ����߷����������յ���Ϣ��
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}