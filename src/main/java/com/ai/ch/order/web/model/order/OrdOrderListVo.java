package com.ai.ch.order.web.model.order;

import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;

public class OrdOrderListVo extends BehindParentOrdOrderVo{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单总优惠券
	 */
	private String orderTotalDiscountFee;
	
	/**
	 * 总实付
	 */
	private String totalAdjustFee;
	
	/**
	 * 总消费积分
	 */
	private Long totalJF;
		
	public String getTotalAdjustFee() {
		return totalAdjustFee;
	}
	public void setTotalAdjustFee(String totalAdjustFee) {
		this.totalAdjustFee = totalAdjustFee;
	}
	public Long getTotalJF() {
		return totalJF;
	}
	public void setTotalJF(Long totalJF) {
		this.totalJF = totalJF;
	}
	public String getOrderTotalDiscountFee() {
		return orderTotalDiscountFee;
	}
	public void setOrderTotalDiscountFee(String orderTotalDiscountFee) {
		this.orderTotalDiscountFee = orderTotalDiscountFee;
	}
}
