/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.service.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fsti.flmnt.ConstantValue;
import com.fsti.flmnt.dao.MonitorDao;
import com.fsti.flmnt.model.JCFtpServerMonitor;

/**
 * @{# FtpServerMonitorTaskThread.java Create on 2015年6月16日 下午6:49:36
 *     <p>
 *     接入服务器监控数据任务
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class FtpServerMonitorTaskThread extends Thread {
		
	private MonitorDao monitorDao = new MonitorDao();

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(new Long(ConstantValue.FTP_SERVER_MONITOR_TIME) * 1000L);
				System.out.println("接入服务器监控数据任务开始运行...");
				// 持久化服务器监控数据
				saveFtpServerMonitorData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 持久化服务器监控数据
	 */
	private void saveFtpServerMonitorData() {
		try {
			// 加锁，线程安全
			synchronized (ConstantValue.ftpServerLocker) {
				// 获取服务器监控数据
				JCFtpServerMonitor ftpServerMonitor = ConstantValue.ftpServerMonitor;
				if (ftpServerMonitor != null&&ftpServerMonitor.getServerName()!=null) {

					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
					ftpServerMonitor.setTimeStamp(format.format(new Date(System
							.currentTimeMillis())));

					// 持久化服务器监控数据
					monitorDao.insertFtpserverMonitor(
							ftpServerMonitor.getServerName() == null ? ""
									: ftpServerMonitor.getServerName(),
							ftpServerMonitor.getFileSize() == null ? -1
									: ftpServerMonitor.getFileSize(),
							ftpServerMonitor.getFileNumber() == null ? -1
									: ftpServerMonitor.getFileNumber(),
							ftpServerMonitor.getTimeStamp(), ftpServerMonitor
									.getFtpAccount() == null ? ""
									: ftpServerMonitor.getFtpAccount());
					System.out.println("成功插入ftp监控数据!");
					// 归零
					ConstantValue.ftpServerMonitor = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
