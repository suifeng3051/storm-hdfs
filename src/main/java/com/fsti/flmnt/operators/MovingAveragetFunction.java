package com.fsti.flmnt.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Values;

import com.fsti.flmnt.model.EWMA;
import com.fsti.flmnt.model.EWMA.Time;

/** 
 * @{#} MovingAveragetFunction.java Create on 2015年5月1日 上午7:50:00
 * <p>
 * 	执行移动平均值的统计计算
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
*/ 
public class MovingAveragetFunction extends BaseFunction {
    
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(MovingAveragetFunction.class);

	private EWMA ewma;
    private Time emitRatePer;
    
    public MovingAveragetFunction(EWMA ewma, Time emitRatePer) {
		this.ewma = ewma;
		this.emitRatePer = emitRatePer;
	}

	public void execute(TridentTuple tuple, TridentCollector collector) {
		LOG.info("***tuple.getLong(0):{}***" + tuple.getLong(0));
		this.ewma = this.ewma.sliding(123.456, Time.MINUTES);
		LOG.info("***ewma sliding:{}***" + ewma);
//		this.ewma.mark(tuple.getLong(0));
//		double av = this.ewma.getAverage();
//		double av = this.ewma.getAverageIn(this.emitRatePer);
		double av = this.ewma.getAveragePer(this.emitRatePer);
		LOG.info("***Rate:{}***" + av);
        collector.emit(new Values(av));
    }
}