package com.ai.ch.order.web.model.order;

import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;

public class StasticOrderReqVo extends StasticsOrderRequest {
	private static final long serialVersionUID = 1L;
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String ordParenOrderId;//父订单id
	private String userName;//用户名称
	private String supplierName;//商户名称

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
