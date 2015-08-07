 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.operators;

import java.util.Map;

import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/** 
 * @{#} FtpJsonFunction.java Create on 2015年6月5日 下午3:56:03
 * <p>
 * 	Kafka数据流做Json转换
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class MonitorJsonFunction extends BaseFunction {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(MonitorJsonFunction.class);
	
    private Fields fields;
    
    public MonitorJsonFunction(Fields fields) {
		this.fields = fields;
	}
    
	/**
	 *	Json转换
	 *
	 *	{"business_type": 1,"business_level": 3,"asset_en_name": "good_asset",
	 *	"asset_physical":"AWS_FIN_PAY20150101010101.txt","data_date": "20151010",
	 *	"area": 33,"operate_type": 44,"row_number": 12,"begin_time": "2015052615063122",
	 *	"end_time": "2015052615063134","version":"V1.0","qualityList": [
	 *	{"check_type": 31,"check_object": "1","check_result": "31","result_number": 23},
	 *	{"check_type": 31,"check_object": "1","check_result": "31","result_number": 23}]}
	 */
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String json = tuple.getString(0);
		if(json == null && "".equals(json)) {
			json = "{}";
		}
		LOG.info("-----MonitorJsonFunction:{}-----" + json);
		Values values = new Values();
		Map<String, Object> map = (Map<String, Object>) JSONValue.parse(json);
		for (int i = 0; i < this.fields.size(); i++) {
//			LOG.info("***Fields:{}***" + map.get(this.fields.get(i)));
			values.add(map.get(this.fields.get(i)));
		}
        collector.emit(values);
	}


}
