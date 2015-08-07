 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fsti.flmnt.ConstantValue;
import com.fsti.flmnt.utils.UUIDGenerator;

/** 
 * @{#} MonitorDao.java Create on 2015年6月15日 上午12:32:11
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class MonitorDao {

	private static final Logger LOG = LoggerFactory.getLogger(MonitorDao.class);
	
	// 创建静态全局变量  
	private static Connection conn;  //创建用于连接数据库的Connection对象
	private static Statement st;
	private static PreparedStatement pstmt;

	/**
     * 获取数据库连接的函数
     * 
     * @return
     */
    public Connection getConnection() {
		try {
			Class.forName(ConstantValue.JDBC_DRIVER);// 加载JDBC数据驱动
			conn = DriverManager.getConnection(ConstantValue.JDBC_URL, 
					ConstantValue.JDBC_USER, ConstantValue.JDBC_PASSWORD);// 创建数据连接
        } catch (Exception e) {
        	LOG.error("数据库连接失败:" + e.getMessage());
        }
        return conn; //返回所建立的数据库连接  
    }
    
	/**
     * 接入服务器监控表(jc_ftpserver_monitor)
     * 表中字段:server_name,file_size,file_number,time_stamp,ftp_account
     * 
     * @param serverName
     * @param fileSize
     * @param fileNumber
     * @param timeStamp
     * @param ftpAccount
     */
    public void insertFtpserverMonitor(String serverName, double fileSize,
			long fileNumber, String timeStamp, String ftpAccount) {
    	String uid = UUIDGenerator.getUUID();
    	String sql = "INSERT INTO jc_ftpserver_monitor "
        		+ "VALUES ('" + uid + "', '" + serverName + "', " 
        		+ fileSize + ", " + fileNumber + ", '" 
        		+ timeStamp + "', '" + ftpAccount + "')";  // 插入数据的sql语句 
    	int count = excuteSQL(sql);
    	LOG.info("向服务器监控表中插入 " + count + " 条数据"); //输出插入操作的处理结果 
    }
	
    /**
     * 接入文件错误记录表(jc_file_error_record)
     * 表中字段:asset_name,business_type,asset_type,area_cd,file_name,file_path,
     * create_date,error_str,server_name,ftp_account,is_alarm
     * 
     * @param assetEnName
     * @param businessType
     * @param assetType
     * @param area
     * @param fileName
     * @param filePath
     * @param createDate
     * @param errorStr
     * @param serverName
     * @param ftpAccount
     * @param isAlarm
     */
    public void insertFileErrorRecord(String assetEnName, String businessType, 
    		long area, String fileName,
			String filePath, String createDate, String errorStr,
			String serverName, String ftpAccount, long isAlarm) {
    	String uid = UUIDGenerator.getUUID();
    	
		String sql = "INSERT INTO jc_file_error_record " + "VALUES ('" + uid
				+ "', '" + assetEnName + "', '" + businessType + "', "
				+ area + ", '" + fileName + "', '"
				+ filePath + "', '" + createDate + "', '" + errorStr + "', '"
				+ serverName + "', '" + ftpAccount + "', " + isAlarm + ")"; // 插入数据的sql语句
		int count = excuteSQL(sql);
    	LOG.info("向文件错误记录表中插入 " + count + " 条数据"); //输出插入操作的处理结果 
    }

    /**
     * 接入文件监控表(jc_access_file_monitor)
     * 表中字段:asset_type,asset_name,business_type,area_cd,data_level,time_stamp,
     * file_size,lase_time,file_number,first_time,ftp_account
     * 
     * @param assetType	资产类别
     * @param assetEnName	资产名称
     * @param businessType	业务类型
     * @param area	地域
     * @param businessLevel	时间粒度
     * @param timeStamp	时间戳
     * @param fileSize	文件大小
     * @param fileNumber	文件个数
     * @param firstTime	最早文件时间
     * @param lastTime	最晚文件时间
     * @param ftpAccount	FTP账户
     */
	public void insertAccessFileMonitor(String assetEnName,
			String businessType, long area, int data_level, String timeStamp,
			Double fileSize, long fileNumber, Date firstTime, Date lastTime) {

		String uid = UUIDGenerator.getUUID();
		SimpleDateFormat dateFormat = null;
		String fTime = null;
		String lTime = null;
		try {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fTime = dateFormat.format(firstTime);
			lTime = dateFormat.format(lastTime);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		String callsql = "{call accessFileMonitor(?,?,?,?,?,?,?,?,?)}";
		conn = getConnection(); // 首先要获取连接，即连接到数据库
		boolean transactionCompleted = false;
		int retryCount = 5;
		do {
			transactionCompleted = true;
			try {
				CallableStatement cst = conn.prepareCall(callsql);
				cst.setString(1, uid);//.setString("tId", uid);
				cst.setString("assetName", assetEnName);
				cst.setString("businessType", businessType);
				cst.setLong("areaCd", area);
				cst.setString("time_stamp", timeStamp);
				cst.setDouble("fileSize", fileSize);
				cst.setString("lastTime", lTime);
				cst.setLong("fileNumber", fileNumber);
				cst.setString("firstTime", fTime);

				int count = cst.executeUpdate();
				LOG.info("向文件监控表中插入 " + count + " 条数据"); // 输出插入操作的处理结果
//				conn.close(); // 关闭数据库连接
			} catch (SQLException e) {
				LOG.error("向文件监控表中插入数据失败:" + e.getMessage());
				e.printStackTrace();
				String sqlState = e.getSQLState();
				// 这个08S01就是这个异常的sql状态。单独处理手动重新链接就可以了。
				if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {
					retryCount--;
				} else {
					retryCount = 0;
				}
			}
		} while (!transactionCompleted && (retryCount > 0));
	}

    /**
     * 执行sql语句操作，并返回插入数据的个数
     * 
     * @param sql
     * @return
     */
    public int excuteSQL(String sql) {
    	int count = 0;
    	conn = getConnection(); // 首先要获取连接，即连接到数据库
    	try {
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象 
			count = st.executeUpdate(sql);  // 执行插入操作的sql语句，并返回插入数据的个数 
    		conn.close();   //关闭数据库连接
		} catch (SQLException e) {
			LOG.error("插入数据失败:" + e.getMessage());
		}
    	return count;
    }
}
