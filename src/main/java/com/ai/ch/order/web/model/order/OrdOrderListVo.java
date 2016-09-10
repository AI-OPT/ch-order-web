package com.ai.ch.order.web.model.order;

import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;

public class OrdOrderListVo extends BehindParentOrdOrderVo{
	
	private static final long serialVersionUID = 1L;
	
	private String orderTotalCouponFee;
	
	private String totalAdjustFee;
	
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
	public String getOrderTotalCouponFee() {
		return orderTotalCouponFee;
	}
	public void setOrderTotalCouponFee(String orderTotalCouponFee) {
		this.orderTotalCouponFee = orderTotalCouponFee;
	}
}
