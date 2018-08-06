package com.ls;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.SimpleAttributeSet;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ContextUtil {

	public static Connection getConnection() throws Exception{
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("ls2008");
		connectionFactory.setPassword("123456");
		connectionFactory.setVirtualHost("/test");
		return connectionFactory.newConnection();
	}
	
	
	public static String getDateTimeStr(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		return sdf.format(new Date());
	}
}
