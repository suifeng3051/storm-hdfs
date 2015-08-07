 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Values;

import com.fsti.flmnt.ConstantValue;
import com.fsti.flmnt.model.MonitorDTO;

/** 
 * @{#} FileSizeSumAssignment.java Create on 2015年6月16日 下午1:34:20
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class TimeStampAssignment extends BaseFunction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TimeStampAssignment.class);
	
	Long timeStamp;
	
	public TimeStampAssignment(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		MonitorDTO monitor = (MonitorDTO) tuple.getValueByField("monitor");
		Double fileSize = (double) tuple.getDoubleByField("fileSize");
		Integer fileNumber = tuple.getIntegerByField("fileNumber");
		LOG.info("#_# Monitor:" + monitor.getBegin_time() + "@" + monitor.getEnd_time());
		LOG.info("#_# fileSize:" + fileSize);
		LOG.info("#_# fileNumber:" + fileNumber);
		LOG.info("#_# TimeStamp:" + timeStamp);
		
		Long afmTime = timeStamp + new Long(ConstantValue.ACCESS_FILE_MONITOR_TIME) * 1000L;
		Long fsmTime = timeStamp + new Long(ConstantValue.FTP_SERVER_MONITOR_TIME) * 1000L;
		Values values = new Values();
		values.add(afmTime);
		values.add(fsmTime);
		collector.emit(values);
	}
}
