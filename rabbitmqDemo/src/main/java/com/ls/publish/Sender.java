package com.ls.publish;

import com.ls.ContextUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


/**
 * 发布订阅模式，需要先启动消费者，如果没有消费者，则消息会丢失
 * @author liusheng
 *
 */
public class Sender {

	 private final static String EXCHANGE_NAME = "testexchange";//定义交换机的名称
	 
	public static void main(String[] args) throws Exception {
		Connection connection = ContextUtil.getConnection();
		Channel channel = connection.createChannel();
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		//发布订阅模式的，因为消息是先发到交换机中，而交换机是没有保存功能的，所以如果没有消费者，消息会丢失
		String message="发布订阅模式的消息2222222222222";
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println("=========>"+message);
		channel.clearConfirmListeners();
		connection.close();
	}
}
