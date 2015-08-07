/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.model;

import java.util.Date;

/**
 * @{# JCFileErrorRecord.java Create on 2015年6月15日 下午5:42:02
 *     <p>
 *     文件错误记录实体
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class JCFileErrorRecord {

	private String id;
	private String assetName;
	private String businessType;
	private Integer areaCD;
	private String fileName;
	private String filePath;
	private Date createDate;
	private String errorStr;
	private String serverName;
	private String ftpAccount;
	private Integer isAlarm;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getAreaCD() {
		return areaCD;
	}

	public void setAreaCD(Integer areaCD) {
		this.areaCD = areaCD;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getErrorStr() {
		return errorStr;
	}

	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getFtpAccount() {
		return ftpAccount;
	}

	public void setFtpAccount(String ftpAccount) {
		this.ftpAccount = ftpAccount;
	}

	public Integer getIsAlarm() {
		return isAlarm;
	}

	public void setIsAlarm(Integer isAlarm) {
		this.isAlarm = isAlarm;
	}

}
