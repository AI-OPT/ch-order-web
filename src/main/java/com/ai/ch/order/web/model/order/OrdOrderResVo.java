package com.ai.ch.order.web.model.order;

import java.util.List;

import com.ai.slp.order.api.orderlist.param.OrdOrderVo;

public class OrdOrderResVo extends OrdOrderVo {
	private static final long serialVersionUID = 1L;
	
	private List<OrdProdInfo> prodInfo;
	
	//支付金额
	private String ordAdjustFee;

	public String getOrdAdjustFee() {
		return ordAdjustFee;
	}

	public void setOrdAdjustFee(String ordAdjustFee) {
		this.ordAdjustFee = ordAdjustFee;
	}

	public List<OrdProdInfo> getProdInfo() {
		return prodInfo;
	}

	public void setProdInfo(List<OrdProdInfo> prodInfo) {
		this.prodInfo = prodInfo;
	}

}
