/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.fsti.flmnt.ConstantValue;
import com.fsti.flmnt.dao.MonitorDao;
import com.fsti.flmnt.model.JCAccessFileMonitor;
import com.fsti.flmnt.model.JCFtpServerMonitor;
import com.fsti.flmnt.model.MonitorDTO;
import com.fsti.flmnt.utils.DictionaryMappingUtil;

/**
 * @{# MonitorManager.java Create on 2015年6月17日 下午11:31:54
 *     <p>
 * 
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class MonitorManager {
	/**
	 * 数据库持久类
	 */
	private MonitorDao monitorDao = new MonitorDao();

	/**
	 * 统计接入服务器监控记录
	 * 
	 * @param monitor
	 */
	// public void calculateFtpServerMonitor(Monitor monitor)
	// {//由于业务变更，改Monitor为MonitorDTO
	public void calculateFtpServerMonitor(MonitorDTO monitor) {
		try {
			if (monitor != null) {
				// 避免线程安全问题
				synchronized (ConstantValue.ftpServerLocker) {
					// 获取ftpserver对象
					JCFtpServerMonitor ftpServerMonitor = ConstantValue.ftpServerMonitor;
					if (ftpServerMonitor == null) {
						ftpServerMonitor = new JCFtpServerMonitor();
						ConstantValue.ftpServerMonitor = ftpServerMonitor;
					}

					// 获取文件总大小
					Double fileSize = ftpServerMonitor.getFileSize();
					if (fileSize != null) {
						// 累加
						fileSize = fileSize + monitor.getFile_size();
					} else {
						// 初始化 记录统计开始时间
						ftpServerMonitor.setServerName(monitor.getServer_name());
						ftpServerMonitor.setFtpAccount(monitor.getFtp_account());
						fileSize = Double.parseDouble(monitor.getFile_size()
								.toString());
					}
					// 保存文件大小
					ftpServerMonitor.setFileSize(fileSize);

					// 统计文件个数
					Integer fileNumber = ConstantValue.ftpServerMonitor
							.getFileNumber();
					if (fileNumber != null) {
						ConstantValue.ftpServerMonitor
								.setFileNumber(fileNumber + 1);
					} else {
						ConstantValue.ftpServerMonitor.setFileNumber(1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 统计错误记录监控记录
	 * 
	 * @param monitor
	 */
	public void checkErrorRecord(MonitorDTO monitor) {
		try {
			// 判断记录是否有错，错则直接插库
			if (monitor != null) {
				Long file_size = monitor.getFile_size();
				// 判断数据是否有误
				if (file_size != null && file_size <= 0) {
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");

					Map<String, Object> dictionaryMap = DictionaryMappingUtil
							.transfer(monitor.getFile_path());

					// 获取业务类型
					String businessType = dictionaryMap.get(
							DictionaryMappingUtil.BUSINESSTYPE_KEY).toString();
					monitor.setBusiness_type(businessType);
					// 获取省份编码
					Long area = Long.parseLong(dictionaryMap.get(
							DictionaryMappingUtil.PROVINCECODE_KEY).toString());
					monitor.setArea(area);
					// 获取资产名称
					String assetEnName = DictionaryMappingUtil.getName(
							monitor.getAsset_physical(), businessType);
					monitor.setAsset_en_name(assetEnName);
					// 获取当前时间
					Date createDate = new Date(System.currentTimeMillis());
					
					monitorDao.insertFileErrorRecord(monitor.getAsset_en_name(),
							monitor.getBusiness_type(), monitor.getArea(),
							monitor.getAsset_physical(), monitor.getFile_path(),
							format.format(createDate), "数据大小稽核有误",
							monitor.getServer_name(), monitor.getFtp_account(), 0L);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 统计接入文件监控记录
	 * 
	 * @param monitor
	 */
	public void calculateAccessFileMonitor(MonitorDTO monitor) {
		try {
			if (monitor != null) {
				synchronized (ConstantValue.accessFileLocker) {
					// 获取accessFile对象
					JCAccessFileMonitor accessFileMonitor = ConstantValue.accessFileMonitor;
					if (accessFileMonitor == null) {
						accessFileMonitor = new JCAccessFileMonitor();
						ConstantValue.accessFileMonitor = accessFileMonitor;
					}

					// 获取文件总大小
					Double fileSize = accessFileMonitor.getFileSize();
					if (fileSize != null) {
						// 累加
						fileSize = fileSize + monitor.getFile_size();
					} else {
						// 记录统计开始时间
						accessFileMonitor.setFirstTime(new Date(System
								.currentTimeMillis()));
						accessFileMonitor.setAssetName(monitor.getAsset_en_name());
						accessFileMonitor.setBusinessType(monitor
								.getBusiness_type());
						accessFileMonitor.setAreaCD(Integer.parseInt(monitor
								.getArea().toString()));
						fileSize = Double.parseDouble(monitor.getFile_size()
								.toString());
					}
					// 保存文件大小
					accessFileMonitor.setFileSize(fileSize);

					// 统计文件个数
					Integer fileNumber = accessFileMonitor.getFileNumber();
					if (fileNumber != null) {
						accessFileMonitor.setFileNumber(fileNumber + 1);
					} else {
						accessFileMonitor.setFileNumber(1);
						System.out.println("");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addAccessFileMonitor(MonitorDTO monitor) {
		try {
			monitorDao.insertAccessFileMonitor("testName", "A001", 849L, 1,
					"2015060612", 20987.0, 200l,
					new Date(System.currentTimeMillis()),
					new Date(System.currentTimeMillis()));
			System.out.println("测试数据插入成功！");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("测试数据插入失败！");
		}
	}
}
