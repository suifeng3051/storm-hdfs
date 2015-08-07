/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.topology;

import org.apache.log4j.Logger;

import com.fsti.flmnt.ConstantValue;
import com.fsti.flmnt.operators.MonitorAlarmDetector;
import com.fsti.flmnt.operators.MonitorAssignment;
import com.fsti.flmnt.operators.MonitorJsonFunction;
import com.fsti.flmnt.operators.MonitorPersistanceFunction;
import com.fsti.flmnt.state.MonitorTrendFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.tuple.Fields;
import storm.kafka.BrokerHosts;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import storm.kafka.trident.OpaqueTridentKafkaSpout;
import storm.kafka.trident.TridentKafkaConfig;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;

/**
 * @{# AccessFileMonitorTridentTopology.java Create on 2015年6月8日 上午11:08:30
 * @version v 0.1
 */
public class MonitorTopology {

	public static final Logger LOG = Logger.getLogger(MonitorTopology.class);

	public final static String TOPOLOGY_NAME = "Monitor-Topology";

	// public static boolean isAccessFileStarted = false;
	//
	// public static boolean isFtpServerStarted = false;

	/**
	 * Storm集群实时流计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public static StormTopology buildTopology() throws Exception {
		BrokerHosts kafkaHosts = new ZkHosts(ConstantValue.ZK_HOST);
		TridentKafkaConfig spoutConf = new TridentKafkaConfig(kafkaHosts, ConstantValue.TOPIC);
		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		spoutConf.forceFromStart = ConstantValue.FORCE_FROM_START;

		OpaqueTridentKafkaSpout spout = new OpaqueTridentKafkaSpout(spoutConf);
		TridentTopology topology = new TridentTopology();

		Stream kafkaStream = topology.newStream("kafka-stream", spout);
		/*
		 * Fields outputFields = new Fields("business_type", "business_level",
		 * "asset_en_name", "asset_physical", "data_date", "area",
		 * "operate_type", "row_number", "begin_time", "end_time", "version",
		 * "server_name", "ftp_account", "file_path", "qualityList");
		 */
		Fields outputFields = new Fields("asset_physical", "begin_time", "client_address", "end_time", "file_path", "file_size", "ftp_account", "operate_type", "server_name", "version");

		Long currentTime = System.currentTimeMillis();
		// 启动定时任务
		// startTask();

		kafkaStream.each(new Fields("str"), new MonitorJsonFunction(outputFields), outputFields).each(outputFields, new MonitorAssignment(), new Fields("monitor"))
				.each(new Fields("monitor"), new MonitorPersistanceFunction(10, 5), new Fields()).groupBy(new Fields("timeStamp"))
				.persistentAggregate(new MonitorTrendFactory(), new Count(), new Fields("count")).newValuesStream().each(new Fields("count"), new MonitorAlarmDetector(), new Fields("alert"));
		
		return topology.build();
	}

	/*
	 * private static void startTask() {
	 * 
	 * if (!isAccessFileStarted) { // 接入文件监控任务 AccessFileMonitorTaskThread
	 * accessFileMonitorTask = new AccessFileMonitorTaskThread(); // 启动任务
	 * accessFileMonitorTask.start(); isAccessFileStarted = true; }
	 * 
	 * if (!isFtpServerStarted) { // 接入服务器监控 FtpServerMonitorTaskThread
	 * ftpServerMonitorTask = new FtpServerMonitorTaskThread(); // 启动任务
	 * ftpServerMonitorTask.start(); isFtpServerStarted = true; } }
	 */
	public static void main(String[] args) throws Exception {
		Config conf = new Config();

		if (args != null && args.length > 0) {
			conf.put(Config.NIMBUS_HOST, args[0]);
			conf.setNumWorkers(3);
			StormSubmitter.submitTopologyWithProgressBar(TOPOLOGY_NAME, conf, buildTopology());
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(TOPOLOGY_NAME, conf, buildTopology());
			Thread.sleep(200 * 1000);
			cluster.shutdown();
		}
	}

}
