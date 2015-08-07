 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.service;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fsti.flmnt.model.MonitorDTO;
import com.fsti.flmnt.utils.DictionaryMappingUtil;

/** 
 * @{#} MonitorServiceTest.java Create on 2015年6月25日 下午12:46:51
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class MonitorServiceTest {
	
	private MonitorService monitorService = new MonitorService();
	private MonitorDTO monitor = new MonitorDTO();
	
	@Before
	public void init() {
		monitor.setAsset_physical("01_100_1_00_20150624174314_00000.txt.gz");
		monitor.setBegin_time("20150624174913");
		monitor.setClient_address("10.0.174.68");
		monitor.setEnd_time("20150624174923");
		monitor.setFile_path("/itf/dpi/qixin/yunnan/01_100_1_00_20150624174314_00000.txt.gz");
		monitor.setFile_size(9204389L);
		monitor.setFtp_account("dpimblqixinyunnan");
		monitor.setOperate_type(1L);
		monitor.setServer_name("10.0.173.138");
		Map<String, Object> dictionaryMap = DictionaryMappingUtil
				.transfer("/itf/dpi/qixin/yunnan/01_100_1_00_20150624174314_00000.txt.gz");
		Long area = Long.parseLong(dictionaryMap.get(
				DictionaryMappingUtil.PROVINCECODE_KEY).toString());
		System.out.println("Area::"+area);
		monitor.setArea(area);
		monitor.setVersion("2.1");
	}

	/**
	 * Test method for {@link com.fsti.flmnt.service.MonitorService#calculateFtpServerMonitor(com.fsti.flmnt.model.MonitorDTO)}.
	 */
	@Test
	public void testCalculateFtpServerMonitor() {
		monitorService.calculateFtpServerMonitor(monitor);
	}

	/**
	 * Test method for {@link com.fsti.flmnt.service.MonitorService#calculateAccessFileMonitor(com.fsti.flmnt.model.MonitorDTO)}.
	 */
	@Test
	public void testCalculateAccessFileMonitor() {
		monitorService.calculateAccessFileMonitor(monitor);
	}

	/**
	 * Test method for {@link com.fsti.flmnt.service.MonitorService#checkErrorRecord(com.fsti.flmnt.model.MonitorDTO)}.
	 */
	@Test
	public void testCheckErrorRecord() {
		monitor.setFile_size(0L);
		monitorService.checkErrorRecord(monitor);
	}

}
