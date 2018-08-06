package com.ls.route;

import com.ls.ContextUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


/**
 * ·��ģʽ
 * @author liusheng
 *
 */
public class Sender {

	private final static String EXCHANGE_NAME = "testroute";//���彻����������
	 
	public static void main(String[] args) throws Exception {
		Connection connection = ContextUtil.getConnection();
		Channel channel = connection.createChannel();
		//����������
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");//����·�ɸ�ʽ�Ľ�����
		//��������ģʽ�ģ���Ϊ��Ϣ���ȷ����������У�����������û�б��湦�ܵģ��������û�������ߣ���Ϣ�ᶪʧ
		String message="route message====================="+ContextUtil.getDateTimeStr();
		String routeKey="key1";
		channel.basicPublish(EXCHANGE_NAME, routeKey, null, message.getBytes());
		System.out.println("=========>"+message);
		channel.clearConfirmListeners();
		connection.close();
	}
}
