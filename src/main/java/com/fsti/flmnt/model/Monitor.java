/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.model;

import java.io.Serializable;
import java.util.List;

/**
 * @{# Monitor.java Create on 2015年6月10日 下午3:18:00
 *     <p>
 *     Kafka接入数据: {"business_type": "1","business_level": 3,"asset_en_name":
 *     "good_asset1",
 *     "asset_physical":"AWS_FIN_PAY20150101010101.txt","data_date": "20151010",
 *     "area": 33,"operate_type": 43,"row_number": 12, "begin_time":
 *     "2015-05-26 15:06:31:22","end_time": "2015-05-26 15:06:34:55",
 *     "version":"V1.0","server_name": "clto111", "ftp_account":
 *     "admin","file_path": "c:/etc/a/w/s/", "qualityList": [{"check_type":
 *     2001,"check_object": "1","check_result": "543216789","result_number":
 *     23}, {"check_type": 2004,"check_object": "1","check_result":
 *     "2","result_number": 23}]}
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class Monitor implements Serializable {
	private static final long serialVersionUID = 1L;

	String business_type;
	Long business_level;
	String asset_en_name;
	String asset_physical;
	String data_date;
	Long area;
	Long operate_type;
	Long row_number;
	String begin_time;
	String end_time;
	String version;
	String server_name;
	String ftp_account;
	String file_path;
	List<MonitorAuality> qualityList;

	public String getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}

	public Long getBusiness_level() {
		return business_level;
	}

	public void setBusiness_level(Long business_level) {
		this.business_level = business_level;
	}

	public String getAsset_en_name() {
		return asset_en_name;
	}

	public void setAsset_en_name(String asset_en_name) {
		this.asset_en_name = asset_en_name;
	}

	public String getAsset_physical() {
		return asset_physical;
	}

	public void setAsset_physical(String asset_physical) {
		this.asset_physical = asset_physical;
	}

	public String getData_date() {
		return data_date;
	}

	public void setData_date(String data_date) {
		this.data_date = data_date;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getOperate_type() {
		return operate_type;
	}

	public void setOperate_type(Long operate_type) {
		this.operate_type = operate_type;
	}

	public Long getRow_number() {
		return row_number;
	}

	public void setRow_number(Long row_number) {
		this.row_number = row_number;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public String getFtp_account() {
		return ftp_account;
	}

	public void setFtp_account(String ftp_account) {
		this.ftp_account = ftp_account;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public List<MonitorAuality> getQualityList() {
		return qualityList;
	}

	public void setQualityList(List<MonitorAuality> qualityList) {
		this.qualityList = qualityList;
	}
	
	@Override
	public String toString() {
		return "Monitor [business_type=" + business_type + ", business_level="
				+ business_level + ", asset_en_name=" + asset_en_name
				+ ", asset_physical=" + asset_physical + "data_date=" + data_date
				+ ", area=" + area + "operate_type=" + operate_type
				+ ", row_number=" + row_number + "begin_time=" + begin_time
				+ ", end_time=" + end_time + "version=" + version
				+ ", server_name=" + server_name + "ftp_account=" + ftp_account
				+ ", file_path=" + file_path + "qualityList=" + qualityList + "]";
	}
}
