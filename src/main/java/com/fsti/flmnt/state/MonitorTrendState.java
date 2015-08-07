 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.state;

import storm.trident.state.map.NonTransactionalMap;

/** 
 * @{#} MonitorTrendState.java Create on 2015年6月9日 下午1:10:22
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class MonitorTrendState extends NonTransactionalMap<Long> {

	protected MonitorTrendState(MonitorTrendBackingMap backing) {
		super(backing);
	}

}
