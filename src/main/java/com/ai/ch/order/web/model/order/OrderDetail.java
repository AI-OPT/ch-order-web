package com.ai.ch.order.web.model.order;

import java.util.List;

import com.ai.slp.order.api.orderlist.param.OrdOrderVo;

public class OrderDetail extends OrdOrderVo {
	private static final long serialVersionUID = 1L;
	
	private List<OrdProdVo> prodList;
	
	private String ordAdjustFee;

	public List<OrdProdVo> getProdList() {
		return prodList;
	}

	public void setProdList(List<OrdProdVo> prodList) {
		this.prodList = prodList;
	}

	public String getOrdAdjustFee() {
		return ordAdjustFee;
	}

	public void setOrdAdjustFee(String ordAdjustFee) {
		this.ordAdjustFee = ordAdjustFee;
	}
	
}
