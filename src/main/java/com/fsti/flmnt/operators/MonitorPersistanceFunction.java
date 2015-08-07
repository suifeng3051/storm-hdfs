/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.operators;

import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

import com.fsti.flmnt.dao.MonitorDao;
import com.fsti.flmnt.model.Monitor;
import com.fsti.flmnt.model.MonitorDTO;
import com.fsti.flmnt.service.MonitorManager;
import com.fsti.flmnt.service.task.AccessFileMonitorTaskThread;
import com.fsti.flmnt.service.task.FtpServerMonitorTaskThread;

/**
 * @{# PersistFunction.java Create on 2015年6月8日 下午2:09:48
 *     <p>
 *     实时流数据持久化操作
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class MonitorPersistanceFunction extends BaseFunction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(MonitorPersistanceFunction.class);

	// private MonitorService monitorService;
	private MonitorManager monitorManager;
	private int count;
	private long afMonitorTime;
	private long fsMonitorTime;

	public static boolean isAccessFileStarted = false;

	public static boolean isFtpServerStarted = false;

	private Queue<TridentTuple> tupleQueue = new ConcurrentLinkedQueue<TridentTuple>();

	public MonitorPersistanceFunction(int n) {
		count = n; // 批量处理的Tuple记录条数
		// lastTime = System.currentTimeMillis(); // 上次批量处理的时间戳
	}

	public MonitorPersistanceFunction(long afMonitorTime, long fsMonitorTime) {
		this.afMonitorTime = afMonitorTime;
		this.fsMonitorTime = fsMonitorTime;
	}

	/**
	 * 实时流持久化操作
	 * 
	 * @see storm.trident.operation.Function#execute(storm.trident.tuple.TridentTuple,
	 *      storm.trident.operation.TridentCollector)
	 */
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {

		MonitorDTO monitor = (MonitorDTO) tuple.getValueByField("monitor");
		LOG.info("tupleQueue.size()==" + tupleQueue.size());
		// LOG.info("timeStamp==" + tuple.getLongByField("timeStamp"));

		// 启动定时任务
		startTask(monitor);

		/************* 稽核汇总逻辑开始 *****************/
		// 一。统计接入文件监控记录
		monitorManager.calculateAccessFileMonitor(monitor);
		// 二。统计错误记录监控记录
		monitorManager.checkErrorRecord(monitor);
		// 三。统计接入服务器监控记录
		monitorManager.calculateFtpServerMonitor(monitor);
		/************** 稽核汇总逻辑结束 ****************/

		tupleQueue.add(tuple);
		/*
		 * if (tupleQueue.size() <= 5) { TridentTuple tup = tupleQueue.poll();
		 * System.out.println("TridentTuple.size-->" + tupleQueue.size()); }
		 */

		/*
		 * LOG.info("afMonitorTime==" + afMonitorTime);
		 * LOG.info("fsMonitorTime==" + fsMonitorTime);
		 * LOG.info("currentTimeMillis==" + System.currentTimeMillis()); long
		 * startTime = System.currentTimeMillis();
		 * 
		 * tupleQueue.add(tuple);
		 * 
		 * try { Thread.sleep(afMonitorTime * 1000L); long currentTime =
		 * System.currentTimeMillis(); if(currentTime - startTime >
		 * afMonitorTime) System.out.println("持久化接入文件监控记录");
		 * Thread.sleep(afMonitorTime * 1000L); if(currentTime - startTime >
		 * fsMonitorTime) System.out.println("持久化服务器监控数据"); } catch (Exception
		 * e) { e.printStackTrace(); } if (tupleQueue.size() == 5) {
		 * TridentTuple tup = tupleQueue.poll();
		 * System.out.println("TridentTuple-->" + tup); }
		 */
		/*
		 * try { Thread.sleep(new Long(ConstantValue.FTP_SERVER_MONITOR_TIME) *
		 * 1000L); System.out.println("持久化服务器监控数据"); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		// 每count条tuple批量提交一次，或者每个1秒钟提交一次
		// if (tupleQueue.size() >= count || currentTime >= lastTime + 1000) {
		/*
		 * if (tupleQueue.size() >= count) { for (int i = 0; i < count; i++) {
		 * tupleQueue.poll(); }
		 * System.out.println("batch insert data into database, total records: "
		 * + count); }
		 */
	}

//	private MonitorDao dao = new MonitorDao();

	/**
	 * 启动定时任务
	 */
	private void startTask(MonitorDTO monitor) {

		if (!isAccessFileStarted) {
			// // 接入文件监控任务
			// AccessFileMonitorTaskThread accessFileMonitorTask = new
			// AccessFileMonitorTaskThread();
			// // 启动任务
			// accessFileMonitorTask.start();
			// isAccessFileStarted = true;
			// yyyy-MM-dd HH:mm:ss (String, String, long, int, String, Double,
			// long, Date, Date)
			try {
				/*dao.insertAccessFileMonitor("testName", "A001", 849L, 1,
						"2015060612", 20987.0, 200l,
						new Date(System.currentTimeMillis()),
						new Date(System.currentTimeMillis()));*/
				
				System.out.println("测试数据开插入.......！");
				monitorManager.addAccessFileMonitor(monitor);
				isAccessFileStarted = true;
				System.out.println("测试数据插入成功.......！");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("测试数据插入失败！");
			}

		}

		if (!isFtpServerStarted) {
			// 接入服务器监控
			FtpServerMonitorTaskThread ftpServerMonitorTask = new FtpServerMonitorTaskThread();
			// 启动任务
			ftpServerMonitorTask.start();
			isFtpServerStarted = true;
		}
	}

	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		// monitorService = new MonitorService();
		monitorManager = new MonitorManager();
		int np = context.numPartitions();
		LOG.info("&&&numPartitions" + np);
	}

}
