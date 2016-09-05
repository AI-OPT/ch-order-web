package com.ai.ch.order.web.model.order;

import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;

public class OrdOrderListVo extends BehindParentOrdOrderVo{
	
	private static final long serialVersionUID = 1L;
	
	private String totalDiscountFee;
	
	private String totalAdjustFee;
	
	private String totalJF;
	
	public String getTotalDiscountFee() {
		return totalDiscountFee;
	}
	public void setTotalDiscountFee(String totalDiscountFee) {
		this.totalDiscountFee = totalDiscountFee;
	}
	public String getTotalAdjustFee() {
		return totalAdjustFee;
	}
	public void setTotalAdjustFee(String totalAdjustFee) {
		this.totalAdjustFee = totalAdjustFee;
	}
	public String getTotalJF() {
		return totalJF;
	}
	public void setTotalJF(String totalJF) {
		this.totalJF = totalJF;
	}
}
