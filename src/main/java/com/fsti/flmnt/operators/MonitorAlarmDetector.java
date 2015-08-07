 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.operators;

import java.util.ArrayList;
import java.util.List;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/** 
 * @{#} MonitorAlarmDetector.java Create on 2015年6月16日 下午4:39:03
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class MonitorAlarmDetector extends BaseFunction {

	private static final long serialVersionUID = 1L;
    public static final int THRESHOLD = 10000;

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        /*String key = (String) tuple.getValue(0);
        Long count = (Long) tuple.getValue(1);
        System.out.println("count:" + count);
        if (count > THRESHOLD) {
            List<Object> values = new ArrayList<Object>();
            values.add("Monitor Alarm Detected for [" + key + "]!");
            System.err.println("Monitor Alarm Detected for [" + key + "]!");
            collector.emit(values);
        }*/
    	String key = "Key-flmnt";
    	List<Object> values = new ArrayList<Object>();
        values.add("Monitor Alarm Detected for [" + key + "]!");
        System.err.println("Monitor Alarm Detected for [" + key + "]!");
        collector.emit(values);
    }
}
