/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.operators;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fsti.flmnt.model.Monitor;
import com.fsti.flmnt.model.MonitorAuality;
import com.fsti.flmnt.model.MonitorDTO;
import com.fsti.flmnt.utils.DictionaryMappingUtil;
import com.fsti.flmnt.utils.JsonMapper;

/**
 * @{# CheckTypeResultAssignment.java Create on 2015年6月15日 下午3:15:38
 *     <p>
 * 
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class AualityAssignment extends BaseFunction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(AualityAssignment.class);

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {

		Long businessLevel = tuple.getLongByField("business_level");
		String assetPhysical = tuple.getStringByField("asset_physical");
		// String timeStamp = tuple.getStringByField("data_date");

		Long operateType = tuple.getLongByField("operate_type");
		Long rowNumber = tuple.getLongByField("row_number");

		String beginTime = tuple.getStringByField("begin_time");
		String endTime = tuple.getStringByField("end_time");
		String version = tuple.getStringByField("version");
		String serverName = tuple.getStringByField("server_name");
		String ftpAccount = tuple.getStringByField("ftp_account");
		String filePath = tuple.getStringByField("file_path");

		// 获取文件大小（单位为byte）
		Long fileSize = tuple.getLongByField("file_size");
		// 获取对端IP
		String clientAddress = tuple.getStringByField("client_address");

		LOG.info("**filePath==>" + filePath);
		Map<String, Object> dictionaryMap = DictionaryMappingUtil
				.transfer(filePath);
		// 由于业务变更，改成属性映射获取业务类型数据
		// String businessType = tuple.getStringByField("business_type");
		// 获取属性字典(暂时只包括businessType和Area编码映射)
		// 获取业务类型编码
		LOG.info("**BUSINESSTYPE_KEY==>"
				+ DictionaryMappingUtil.BUSINESSTYPE_KEY);
		String businessType = dictionaryMap.get(
				DictionaryMappingUtil.BUSINESSTYPE_KEY).toString();
		LOG.info("**businessType==>" + businessType);

		// 由于业务变更，改成属性映射获取区域编码
		// Long area = tuple.getLongByField("area");
		// 获取区域编码
		LOG.info("**PROVINCECODE_KEY==>"
				+ DictionaryMappingUtil.PROVINCECODE_KEY);
		Long area = Long.parseLong(dictionaryMap.get(
				DictionaryMappingUtil.PROVINCECODE_KEY).toString());
		LOG.info("**area==>" + area);

		// 由于业务变更，改成资产名从物理文件名中获取
		// String assetEnName = tuple.getStringByField("asset_en_name");
		String assetEnName = DictionaryMappingUtil.getName(assetPhysical,
				businessType);
		LOG.info("**assetEnName==>" + assetEnName);

		/*JsonMapper binder = JsonMapper.nonDefaultMapper();
		String qualityListStr = binder.toJson(tuple
				.getValueByField("qualityList"));
		LOG.info("qualityListStr---->" + qualityListStr);

		List<MonitorAuality> aualityList = null;
		try {
			aualityList = binder.getMapper().readValue(qualityListStr,
					new TypeReference<List<MonitorAuality>>() {
					});
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}*/

		// 由于业务变更将DTo取代monitor，在 DTO中添加了一些属性
		// Monitor monitor = new Monitor();
		MonitorDTO monitor = new MonitorDTO();
		monitor.setBusiness_type(businessType);
		monitor.setBusiness_level(businessLevel);
		monitor.setAsset_en_name(assetEnName);
		monitor.setAsset_physical(assetPhysical);
		// monitor.setData_date(timeStamp);
		monitor.setArea(area);
		monitor.setOperate_type(operateType);
		monitor.setRow_number(rowNumber);
		monitor.setBegin_time(beginTime);
		monitor.setEnd_time(endTime);
		monitor.setVersion(version);
		monitor.setServer_name(serverName);
		monitor.setFtp_account(ftpAccount);
		monitor.setFile_path(filePath);
		monitor.setFile_size(fileSize);
		monitor.setClient_address(clientAddress);

		// monitor.setQualityList(aualityList);
		LOG.info("@*@ " + monitor.toString());

		Values values = new Values();
		values.add(monitor);
		collector.emit(values);

	}
}
