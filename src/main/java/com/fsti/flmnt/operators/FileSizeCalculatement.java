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

import com.fsti.flmnt.model.MonitorDTO;

/** 
 * @{#} FileSizeCalculatement.java Create on 2015年6月25日 下午4:05:00
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class FileSizeCalculatement extends BaseFunction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TimeStampAssignment.class);
	
	Double fileSize = (double) 0L;
	Integer fileNumber = 0;
	
	/*public FileSizeCalculatement(Double fileSize, Integer fileNumber) {
		this.fileSize = fileSize;
		this.fileNumber = fileNumber;
	}*/
	
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		MonitorDTO monitor = (MonitorDTO) tuple.getValueByField("monitor");
		fileSize = fileSize + monitor.getFile_size();
		LOG.info("@+@ FileSize:" + fileSize);
		fileNumber = fileNumber + 1;
		LOG.info("@+@ FileNumber:" + fileNumber);
		Values values = new Values();
		values.add(fileSize);
		values.add(fileNumber);
		collector.emit(values);
	}
}
