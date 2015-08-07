/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.model;

import java.util.List;

/**
 * @{# MonitorDTO.java Create on 2015年6月24日 下午1:50:35
 *     <p>
 *     { "asset_physical":""//文件名称 "operate_type":1, 对于ftpserver写死即可
 *     "client_address":"10.0.0.0",//对端IP "server_name":"10.0.173.130",//服务端IP
 *     "ftp_account":"abc",//用户名
 *     "file_path":"/data/abc/feiman/FIX_0_0.dat"//文件绝对路径 "
 *     version": "V2.1",//版本号 "file_size": 1312//b为单位 "begin_time":
 *     "yyyyMMddHHmmssSS",//任务开始时间 "end_time": "yyyyMMddHHmmssSS",//任务结束时间 }
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class MonitorDTO {

	private static final long serialVersionUID = 1L;

	private String business_type;
	private Long business_level;
	private String asset_en_name;
	private String asset_physical;
	private String data_date;
	private Long area;
	private Long operate_type;
	private Long row_number;
	private String begin_time;
	private String end_time;
	private String version;
	private String server_name;
	private String ftp_account;
	private String file_path;
	private String client_address;
	private Long file_size;
	private List<MonitorAuality> qualityList;

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

	public String getClient_address() {
		return client_address;
	}

	public void setClient_address(String client_address) {
		this.client_address = client_address;
	}

	public Long getFile_size() {
		return file_size;
	}

	public void setFile_size(Long file_size) {
		this.file_size = file_size;
	}

	public List<MonitorAuality> getQualityList() {
		return qualityList;
	}

	public void setQualityList(List<MonitorAuality> qualityList) {
		this.qualityList = qualityList;
	}

}
