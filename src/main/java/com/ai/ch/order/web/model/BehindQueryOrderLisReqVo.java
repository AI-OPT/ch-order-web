package com.ai.ch.order.web.model;

import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;

public class BehindQueryOrderLisReqVo extends BehindQueryOrderListRequest{
	private static final long serialVersionUID = 1L;
	/**
	 * 页面传入的订单id
	 */
	private String inputOrderId;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 用户名
	 */
	private String userName;
	

	public String getInputOrderId() {
		return inputOrderId;
	}

	public void setInputOrderId(String inputOrderId) {
		this.inputOrderId = inputOrderId;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
