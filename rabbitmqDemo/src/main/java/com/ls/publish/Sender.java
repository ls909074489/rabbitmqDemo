package com.ls.publish;

import com.ls.ContextUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


/**
 * ��������ģʽ����Ҫ�����������ߣ����û�������ߣ�����Ϣ�ᶪʧ
 * @author liusheng
 *
 */
public class Sender {

	 private final static String EXCHANGE_NAME = "testexchange";//���彻����������
	 
	public static void main(String[] args) throws Exception {
		Connection connection = ContextUtil.getConnection();
		Channel channel = connection.createChannel();
		//����������
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		//��������ģʽ�ģ���Ϊ��Ϣ���ȷ����������У�����������û�б��湦�ܵģ��������û�������ߣ���Ϣ�ᶪʧ
		String message="��������ģʽ����Ϣ2222222222222";
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println("=========>"+message);
		channel.clearConfirmListeners();
		connection.close();
	}
}
