 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.state;

import java.util.Map;

import storm.trident.state.State;
import storm.trident.state.StateFactory;
import backtype.storm.task.IMetricsContext;

/** 
 * @{#} MonitorTrendFactory.java Create on 2015年6月9日 下午1:16:10
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class MonitorTrendFactory implements StateFactory {
    private static final long serialVersionUID = 1L;

    @Override
    public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
    	return new MonitorTrendState(new MonitorTrendBackingMap());
    }

}
