/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.service.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fsti.flmnt.ConstantValue;
import com.fsti.flmnt.dao.MonitorDao;
import com.fsti.flmnt.model.JCAccessFileMonitor;

/**
 * @{# TaskThread.java Create on 2015年6月16日 下午6:19:10
 *     <p>
 *     接入文件监控记录定时任务
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class AccessFileMonitorTaskThread extends Thread {
	
	private MonitorDao monitorDao = new MonitorDao();

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(new Long(ConstantValue.ACCESS_FILE_MONITOR_TIME) * 1000L);
				System.out.println("接入文件监控记录定时任务开始运行...");
				// 持久化接入文件监控记录
				writeAccessFileMonitorData();
				//
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 持久化接入文件监控记录
	 */
	private void writeAccessFileMonitorData() {
		try {
			// 加锁，避免线程安全
			synchronized (ConstantValue.accessFileLocker) {
				// 获得
				JCAccessFileMonitor accessFileMonitor = ConstantValue.accessFileMonitor;
				if (accessFileMonitor != null
						&& accessFileMonitor.getFirstTime() != null) {

					// 设置时间戳
					Date date = new Date(System.currentTimeMillis());
					// 设置结束时间
					accessFileMonitor.setLastTime(date);
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
					String timeStamp = format.format(date);
					
					//插入参数
					String assetName = accessFileMonitor.getAssetName() == null ? ""
							: accessFileMonitor.getAssetName();
					String businessType = accessFileMonitor.getBusinessType() == null ? ""
							: accessFileMonitor.getBusinessType();
					Long areaCD = Long
							.parseLong(accessFileMonitor.getAreaCD() == null ? "-1"
									: accessFileMonitor.getAreaCD().toString());
					Double fileSize = Double.parseDouble(accessFileMonitor
							.getFileSize() == null ? "-1" : accessFileMonitor
							.getFileSize().toString()) / 1024;
					Long fileNumber = Long.parseLong(accessFileMonitor
							.getFileNumber() == null ? "-1" : accessFileMonitor
							.getFileNumber().toString());
					Date firstTime = accessFileMonitor.getFirstTime();
					Date lastTime = accessFileMonitor.getLastTime();
					
					System.out.println(assetName + " @ " + businessType + " @ " + 
							areaCD + " @ " + timeStamp + " @ " + fileSize + " @ " + 
							fileNumber + " @ " + firstTime + " @ " + lastTime);
					// 持久化件监控记录
					monitorDao.insertAccessFileMonitor(assetName, businessType,
							areaCD, -1, timeStamp, fileSize, fileNumber, firstTime,
							lastTime);
					
					System.out.println("成功插入accessFile数据!");

					ConstantValue.accessFileMonitor = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
