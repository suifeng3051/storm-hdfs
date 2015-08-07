/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.model;

import java.util.Date;

/**
 * @{# JCAccessFileMonitor.java Create on 2015年6月15日 下午5:46:08
 *     <p>
 *     接入文件监控实体
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class JCAccessFileMonitor {

	private String id;
	private String assetName;
	private String businessType;
	private Integer areaCD;
	private Integer dataLevel;
	private String timeStamp;
	private Double fileSize;
	private Date lastTime;
	private Integer fileNumber;
	private Date firstTime;

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

	public Integer getDataLevel() {
		return dataLevel;
	}

	public void setDataLevel(Integer dataLevel) {
		this.dataLevel = dataLevel;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(Integer fileNumber) {
		this.fileNumber = fileNumber;
	}

	public Date getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

}
