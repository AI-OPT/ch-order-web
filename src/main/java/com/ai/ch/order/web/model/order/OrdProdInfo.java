package com.ai.ch.order.web.model.order;

import com.ai.slp.order.api.orderlist.param.OrdProductVo;

public class OrdProdInfo extends OrdProductVo {
	private static final long serialVersionUID = 1L;
	private String prodSalePrice;
	private String prodDiscountFee;
	private String prodAdjustFee;

	public String getProdSalePrice() {
		return prodSalePrice;
	}

	public void setProdSalePrice(String prodSalePrice) {
		this.prodSalePrice = prodSalePrice;
	}

	public String getProdDiscountFee() {
		return prodDiscountFee;
	}

	public void setProdDiscountFee(String prodDiscountFee) {
		this.prodDiscountFee = prodDiscountFee;
	}

	public String getProdAdjustFee() {
		return prodAdjustFee;
	}

	public void setProdAdjustFee(String prodAdjustFee) {
		this.prodAdjustFee = prodAdjustFee;
	}

}
