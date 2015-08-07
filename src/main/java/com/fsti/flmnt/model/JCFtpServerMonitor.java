/*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.model;

import java.util.Date;

/**
 * @{# JCFtpServerMonitor.java Create on 2015年6月15日 下午5:50:25
 *     <p>
 *     接入服务器监控实体
 *     </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>
 * @version v 0.1
 */
public class JCFtpServerMonitor {

	private String id;
	private String serverName;
	private Double fileSize;
	private Integer fileNumber;
	private String timeStamp;
	private String ftpAccount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(Integer fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getFtpAccount() {
		return ftpAccount;
	}

	public void setFtpAccount(String ftpAccount) {
		this.ftpAccount = ftpAccount;
	}

}
