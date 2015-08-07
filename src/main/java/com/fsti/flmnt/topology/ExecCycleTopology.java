 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.topology;

import org.apache.log4j.Logger;

import storm.kafka.BrokerHosts;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import storm.kafka.trident.OpaqueTridentKafkaSpout;
import storm.kafka.trident.TridentKafkaConfig;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.tuple.Fields;

import com.fsti.flmnt.ConstantValue;
import com.fsti.flmnt.model.EWMA;
import com.fsti.flmnt.model.EWMA.Time;
import com.fsti.flmnt.operators.FileSizeCalculatement;
import com.fsti.flmnt.operators.MonitorAssignment;
import com.fsti.flmnt.operators.MonitorJsonFunction;
import com.fsti.flmnt.operators.MovingAveragetFunction;
import com.fsti.flmnt.operators.TimeStampAssignment;

/** 
 * @{#} ExecCycleTopology.java Create on 2015年6月29日 下午7:19:17
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class ExecCycleTopology {

	public static final Logger LOG = Logger.getLogger(ExecCycleTopology.class);

	public final static String TOPOLOGY_NAME = "Exec-Cycle-Topology";

	/**
	 * Storm集群实时流计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public static StormTopology buildTopology() throws Exception {
		BrokerHosts kafkaHosts = new ZkHosts(ConstantValue.ZK_HOST);
		TridentKafkaConfig spoutConf = new TridentKafkaConfig(kafkaHosts,
				ConstantValue.TOPIC);
		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		spoutConf.forceFromStart = ConstantValue.FORCE_FROM_START;

		OpaqueTridentKafkaSpout spout = new OpaqueTridentKafkaSpout(spoutConf);
		TridentTopology topology = new TridentTopology();

		Stream kafkaStream = topology.newStream("kafka-stream", spout);

		Fields outputFields = new Fields("asset_physical", "begin_time",
				"client_address", "end_time", "file_path", "file_size",
				"ftp_account", "operate_type", "server_name", "version");

		Long currentTime = System.currentTimeMillis();
		//计算移动平均值
        EWMA ewma = new EWMA().sliding(1.0, Time.MINUTES).withAlpha(EWMA.ONE_MINUTE_ALPHA);
        
		kafkaStream.each(new Fields("str"), new MonitorJsonFunction(outputFields), outputFields)
			.each(outputFields, new MonitorAssignment(), new Fields("monitor"))
			.each(new Fields("monitor"), new FileSizeCalculatement(), new Fields("fileSize", "fileNumber"))
			.each(new Fields("monitor", "fileSize", "fileNumber"), new TimeStampAssignment(currentTime), 
					new Fields("afmTime", "fsmTime"))
			.each(new Fields("afmTime"), new MovingAveragetFunction(ewma, Time.MINUTES), new Fields("average"))
			/*.each(new Fields("monitor", "fileSize", "fileNumber", "afmTime", "fsmTime"),
					new ExecutionCyclement(), new Fields())*/;
		
//		kafkaStream = kafkaStream.project(outputFields);
		/*Stream assignStream = kafkaStream.each(outputFields, 
				new MonitorAssignment(), new Fields("monitor"));
		Stream fileSizeStream = assignStream.each(new Fields("monitor"), 
				new FileSizeCalculatement(), new Fields("fileSize", "fileNumber"));
		Stream timeStampStream = fileSizeStream.each(
				new Fields("monitor", "fileSize", "fileNumber"), 
				new TimeStampAssignment(currentTime), 
				new Fields("afmTime", "fsmTime"));
		
        Stream averageStream = timeStampStream.each(new Fields("afmTime"), 
        		new MovingAveragetFunction(ewma, Time.MINUTES), new Fields("average"));*/
		
		return topology.build();
	}
	
	public static void main(String[] args) throws Exception {
		Config conf = new Config();

		if (args != null && args.length > 0) {
			conf.put(Config.NIMBUS_HOST, args[0]);
			conf.setNumWorkers(3);
			StormSubmitter.submitTopologyWithProgressBar(TOPOLOGY_NAME, conf,
					buildTopology());
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(TOPOLOGY_NAME, conf, buildTopology());
			Thread.sleep(200 * 1000);
			cluster.shutdown();
		}
	}
		
}
