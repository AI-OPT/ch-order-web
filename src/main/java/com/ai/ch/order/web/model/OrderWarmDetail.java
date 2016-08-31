package com.ai.ch.order.web.model;

import java.util.List;

import com.ai.slp.order.api.warmorder.param.OrderWarmVo;

public class OrderWarmDetail extends OrderWarmVo {
	private static final long serialVersionUID = 1L;
	private List<ProductVo> productList;

	public List<ProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductVo> productList) {
		this.productList = productList;
	}

}
