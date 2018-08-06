package com.ls.topic;

import com.ls.ContextUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


/**
 * ·��ģʽ
 * @author liusheng
 *
 */
public class Sender {

	private final static String EXCHANGE_NAME = "testtopic";//���彻����������
	 
	public static void main(String[] args) throws Exception {
		Connection connection = ContextUtil.getConnection();
		Channel channel = connection.createChannel();
		//����������
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");//����·�ɸ�ʽ�Ľ�����
		//��������ģʽ�ģ���Ϊ��Ϣ���ȷ����������У�����������û�б��湦�ܵģ��������û�������ߣ���Ϣ�ᶪʧ
		String message="topic message====================="+ContextUtil.getDateTimeStr();
		String routeKey="key.22222";
		channel.basicPublish(EXCHANGE_NAME, routeKey, null, message.getBytes());
		channel.clearConfirmListeners();
		connection.close();
		
		System.out.println("=========>"+message);
	}
}
