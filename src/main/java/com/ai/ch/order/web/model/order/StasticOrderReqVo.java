package com.ai.ch.order.web.model.order;

import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;

public class StasticOrderReqVo extends StasticsOrderRequest {
	private static final long serialVersionUID = 1L;
	private String startTime;
	private String endTime;
	private String ordParenOrderId;
	private String userName;
	private String supplierName;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOrdParenOrderId() {
		return ordParenOrderId;
	}

	public void setOrdParenOrderId(String ordParenOrderId) {
		this.ordParenOrderId = ordParenOrderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}
