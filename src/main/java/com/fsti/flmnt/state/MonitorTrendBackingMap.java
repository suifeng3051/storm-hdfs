 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.state.map.IBackingMap;

/** 
 * @{#} MonitorTrendBackingMap.java Create on 2015年6月9日 下午1:13:01
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class MonitorTrendBackingMap implements IBackingMap<Long> {
    private static final Logger LOG = LoggerFactory.getLogger(MonitorTrendBackingMap.class);
    Map<String, Long> storage = new ConcurrentHashMap<String, Long>();

	@Override
	public List<Long> multiGet(List<List<Object>> keys) {
		List<Long> values = new ArrayList<Long>();
        for (List<Object> key : keys) {
            Long value = storage.get(key.get(0));
            if (value == null) {
                values.add(new Long(0));
            } else {
                values.add(value);
            }
        }
        return values;
	}

	@Override
	public void multiPut(List<List<Object>> keys, List<Long> vals) {
		for (int i = 0; i < keys.size(); i++) {
            LOG.info("Persisting [" + keys.get(i).get(0) + "] ==> [" + vals.get(i) + "]");
            storage.put((String) keys.get(i).get(0), vals.get(i));
        }
	}
    

}
