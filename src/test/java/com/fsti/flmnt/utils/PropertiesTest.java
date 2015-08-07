/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.fsti.flmnt.topology.MonitorTopology;

/**
 * @{# PropertiesTest.java Create on 2015年6月11日 下午1:47:45
 *     <p>
 * 
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class PropertiesTest {
	public static void main(String[] args) {
		//加载属性文件，读取数据库连接配置信息
		Properties pro = new Properties();
		try {
//			pro.load(getResourceAsStream("/application.properties"));
			pro.load(MonitorTopology.class.getClassLoader().getResourceAsStream("application.properties"));
			String jdbcDriver = pro.getProperty("jdbc-driver");
			String url = pro.getProperty("url");
			String user = pro.getProperty("user");
			String password = pro.getProperty("password");
			System.out.println("配置文件属性---->" + jdbcDriver  + "#" + url + "#" + user + "#" + password + "#");
		} catch (IOException e) {
			System.out.println("未找到配置文件！！！");
		}
		
//		String readfile = "e:" + File.separator + "readfile.properties";
//		String writefile = "e:" + File.separator + "writefile.properties";
//		String readxmlfile = "e:" + File.separator + "readxmlfile.xml";
//		String writexmlfile = "e:" + File.separator + "writexmlfile.xml";
//		String readtxtfile = "e:" + File.separator + "readtxtfile.txt";
//		String writetxtfile = "e:" + File.separator + "writetxtfile.txt";

//		readPropertiesFile(readfile); // 读取properties文件
//		writePropertiesFile(writefile); // 写properties文件
//		readPropertiesFileFromXML(readxmlfile); // 读取XML文件
//		writePropertiesFileToXML(writexmlfile); // 写XML文件
//		readPropertiesFile(readtxtfile); // 读取txt文件
//		writePropertiesFile(writetxtfile); // 写txt文件
	}

	// 读取资源文件,并处理中文乱码
	public static void readPropertiesFile(String filename) {
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(filename);
			properties.load(inputStream);
			inputStream.close(); // 关闭流
		} catch (IOException e) {
			e.printStackTrace();
		}
		String username = properties.getProperty("username");
		String passsword = properties.getProperty("password");
		String chinese = properties.getProperty("chinese");
		try {
			chinese = new String(chinese.getBytes("ISO-8859-1"), "GBK"); // 处理中文乱码
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(username);
		System.out.println(passsword);
		System.out.println(chinese);
	}

	// 读取XML文件,并处理中文乱码
	public static void readPropertiesFileFromXML(String filename) {
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(filename);
			properties.loadFromXML(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String username = properties.getProperty("username");
		String passsword = properties.getProperty("password");
		String chinese = properties.getProperty("chinese"); // XML中的中文不用处理乱码，正常显示
		System.out.println(username);
		System.out.println(passsword);
		System.out.println(chinese);
	}

	// 写资源文件，含中文
	public static void writePropertiesFile(String filename) {
		Properties properties = new Properties();
		try {
			OutputStream outputStream = new FileOutputStream(filename);
			properties.setProperty("username", "myname");
			properties.setProperty("password", "mypassword");
			properties.setProperty("chinese", "中文");
			properties.store(outputStream, "author: shixing_11@sina.com");
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 写资源文件到XML文件，含中文
	public static void writePropertiesFileToXML(String filename) {
		Properties properties = new Properties();
		try {
			OutputStream outputStream = new FileOutputStream(filename);
			properties.setProperty("username", "myname");
			properties.setProperty("password", "mypassword");
			properties.setProperty("chinese", "中文");
			properties.storeToXML(outputStream, "author: shixing_11@sina.com");
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
