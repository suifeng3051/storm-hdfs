/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt;

import java.io.IOException;
import java.util.Properties;

import com.fsti.flmnt.model.JCAccessFileMonitor;
import com.fsti.flmnt.model.JCFileErrorRecord;
import com.fsti.flmnt.model.JCFtpServerMonitor;

/**
 * @{# ConstantValue.java Create on 2015年6月15日 下午5:38:52
 *     <p>
 *     公共处理结果类
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class ConstantValue {
	
	public final static String APP_PROP_NAME = "application.properties";//生产集群环境属性文件
	public final static String APP_LOCAL_PROP_NAME = "application-local.properties";//本地开发环境属性文件
	
	public static String TOPIC;
	public static String ZK_HOST;
	public static String JDBC_DRIVER;
	public static String JDBC_URL;
	public static String JDBC_USER;
	public static String JDBC_PASSWORD;
	public static String ACCESS_FILE_MONITOR_TIME;
	public static String FTP_SERVER_MONITOR_TIME;
	public static String HDFS_URL;
	public static String HDFS_DAAS_PATH;
	public static String HDFS_DAAS_USER;//forceFromStart
	public static boolean FORCE_FROM_START;
	static {
		Properties pro = new Properties();
		try {
			// 加载属性文件，读取配置信息
			pro.load(ConstantValue.class.getClassLoader().getResourceAsStream(
					APP_LOCAL_PROP_NAME));
			//Kafka配置信息
			TOPIC = pro.getProperty("kafka.topic");
			ZK_HOST = pro.getProperty("zookeeper.connect");
			FORCE_FROM_START = new Boolean(pro.getProperty("forceFromStart"));
			//HDFS配置信息
			HDFS_URL = pro.getProperty("hdfs.url");
			HDFS_DAAS_PATH = pro.getProperty("hdfs.daas.path");
			HDFS_DAAS_USER = pro.getProperty("hdfs.daas.user");
			//数据库连接配置信息
			JDBC_DRIVER = pro.getProperty("jdbc-driver");
			JDBC_URL = pro.getProperty("url");
			JDBC_USER = pro.getProperty("user");
			JDBC_PASSWORD = pro.getProperty("password");
			//监控周期配置信息
			ACCESS_FILE_MONITOR_TIME = pro.getProperty("access-file-monitor-time");
			FTP_SERVER_MONITOR_TIME = pro.getProperty("ftp-server-monitor-time");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 接入文件监控临时变量
	 */
	public static JCAccessFileMonitor accessFileMonitor;
	
	/**
	 * 接入服务器监控临时变量
	 */
	public static JCFtpServerMonitor ftpServerMonitor;
	
	/**
	 * 文件错误记录临时变量
	 */
	public static JCFileErrorRecord fileErrorRecord;
	
	/*** 接入文件监控锁 **/
	public static Object  accessFileLocker = new Object();
	
	/*** 接入服务器监控锁 **/
	public static Object ftpServerLocker = new Object();

}
