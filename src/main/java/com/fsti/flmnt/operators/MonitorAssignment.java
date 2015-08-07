/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.operators;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.tuple.Values;

import com.fsti.flmnt.dao.MonitorDao;
import com.fsti.flmnt.model.MonitorDTO;
import com.fsti.flmnt.utils.DictionaryMappingUtil;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * @{# MonitorAssignment.java Create on 2015年6月24日 下午6:38:26
 *     <p>
 *     Monitor传输数据转换
 *     {"asset_physical":"01_100_1_00_20150624174314_00000.txt.gz",
 *     "begin_time":"20150624174913","client_address":"10.0.174.68","end_time":
 *     "20150624174913",
 *     "file_path":"/itf/dpi/qixin/yunnan/01_100_1_00_20150624174314_00000.txt.gz"
 *     , "file_size":"9204389","ftp_account":"dpimblqixinyunnan",
 *     "operate_type":1,"server_name":"10.0.173.138","version":"2.1"}
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class MonitorAssignment extends BaseFunction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(MonitorAssignment.class);

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		try {
			String assetPhysical = tuple.getStringByField("asset_physical");
			String beginTime = tuple.getStringByField("begin_time");
			String endTime = tuple.getStringByField("end_time");
			String clientAddress = tuple.getStringByField("client_address");// 获取对端IP

			String filePath = tuple.getStringByField("file_path");
			String fileSize = tuple.getStringByField("file_size");// 获取文件大小,单位为byte

			String ftpAccount = tuple.getStringByField("ftp_account");
			Long operateType = tuple.getLongByField("operate_type");
			String serverName = tuple.getStringByField("server_name");
			String version = tuple.getStringByField("version");

			// 由于业务变更，改成属性映射获取业务类型数据与属性字典(暂时只包括businessType和Area编码映射)
			Map<String, Object> dictionaryMap = DictionaryMappingUtil
					.transfer(filePath);

			// 获取业务类型编码
			String businessType = dictionaryMap.get(
					DictionaryMappingUtil.BUSINESSTYPE_KEY).toString();// "A0001";
			LOG.info("**businessType==>" + businessType);

			// 获取区域编码
			Long area = Long.parseLong(dictionaryMap.get(
					DictionaryMappingUtil.PROVINCECODE_KEY).toString());
			LOG.info("**area==>" + area);

			// 资产名从物理文件名中获取
			String assetEnName = DictionaryMappingUtil.getName(assetPhysical,
					businessType);
			LOG.info("**assetEnName==>" + assetEnName);

			MonitorDTO monitor = new MonitorDTO();
			monitor.setBusiness_type(businessType);
			monitor.setAsset_en_name(assetEnName);
			monitor.setAsset_physical(assetPhysical);
			monitor.setArea(area);
			monitor.setOperate_type(operateType);
			monitor.setBegin_time(beginTime);
			monitor.setEnd_time(endTime);
			monitor.setVersion(version);
			monitor.setServer_name(serverName);
			monitor.setFtp_account(ftpAccount);
			monitor.setFile_path(filePath);
			monitor.setFile_size(new Long(fileSize));
			monitor.setClient_address(clientAddress);

			Values values = new Values();
			values.add(monitor);

			collector.emit(values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
