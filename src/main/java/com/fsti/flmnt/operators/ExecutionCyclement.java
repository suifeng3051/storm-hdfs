 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fsti.flmnt.ConstantValue;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/** 
 * @{#} ExecutionCyclement.java Create on 2015年6月26日 下午4:56:41
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class ExecutionCyclement extends BaseFunction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ExecutionCyclement.class);

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		Long afmTime = tuple.getLongByField("afmTime");
		Long fsmTime =	tuple.getLongByField("fsmTime");
		
		if(afmTime.equals(System.currentTimeMillis()))
			LOG.info("开始执行接入文件监控!");
		if(fsmTime.equals(System.currentTimeMillis()))
			LOG.info("开始执行接入服务器监控!");
	}

}
