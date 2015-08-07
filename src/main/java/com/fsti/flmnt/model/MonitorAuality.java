 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.model;

/** 
 * @{#} MonitorAuality.java Create on 2015年6月15日 上午1:45:58
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class MonitorAuality {
	private static final long serialVersionUID = 1L;

	private Integer check_type;
	private String check_object;
	private String check_result;
	private Integer result_number;

	public Integer getCheck_type() {
		return check_type;
	}

	public void setCheck_type(Integer check_type) {
		this.check_type = check_type;
	}

	public String getCheck_object() {
		return check_object;
	}

	public void setCheck_object(String check_object) {
		this.check_object = check_object;
	}

	public String getCheck_result() {
		return check_result;
	}

	public void setCheck_result(String check_result) {
		this.check_result = check_result;
	}

	public Integer getResult_number() {
		return result_number;
	}

	public void setResult_number(Integer result_number) {
		this.result_number = result_number;
	}

	@Override
	public String toString() {
		return "MonitorAuality [check_type=" + check_type + ", check_object="
				+ check_object + ", check_result=" + check_result
				+ ", result_number=" + result_number + "]";
	}
}
