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

public class MonitorManagerTest {

	private MonitorManager monitorManager = new MonitorManager();
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
	
	@Test
	public void testCalculateFtpServerMonitor() {
		monitorManager.calculateFtpServerMonitor(monitor);
	}

	@Test
	public void testCheckErrorRecord() {
		monitor.setFile_size(0L);
		monitorManager.checkErrorRecord(monitor);
	}

	@Test
	public void testCalculateAccessFileMonitor() {
		monitorManager.calculateAccessFileMonitor(monitor);
	}

}
