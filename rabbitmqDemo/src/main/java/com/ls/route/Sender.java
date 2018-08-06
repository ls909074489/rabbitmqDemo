package com.ls.route;

import com.ls.ContextUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


/**
 * 路由模式
 * @author liusheng
 *
 */
public class Sender {

	private final static String EXCHANGE_NAME = "testroute";//定义交换机的名称
	 
	public static void main(String[] args) throws Exception {
		Connection connection = ContextUtil.getConnection();
		Channel channel = connection.createChannel();
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");//定义路由格式的交换机
		//发布订阅模式的，因为消息是先发到交换机中，而交换机是没有保存功能的，所以如果没有消费者，消息会丢失
		String message="route message====================="+ContextUtil.getDateTimeStr();
		String routeKey="key1";
		channel.basicPublish(EXCHANGE_NAME, routeKey, null, message.getBytes());
		System.out.println("=========>"+message);
		channel.clearConfirmListeners();
		connection.close();
	}
}
