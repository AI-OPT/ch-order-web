package com.ai.ch.order.web.model.order;

import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;

public class OrderListQueryParams extends BehindQueryOrderListRequest{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单状态
	 */
	private String states;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 父订单id
	 */
	private String parentOrderId;
 
	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
}
