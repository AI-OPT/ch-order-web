package com.ai.ch.order.web.model.order;

import java.util.List;

import com.ai.ch.order.web.model.ProductVo;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;

public class OrderDetail extends OrdOrderVo {
	private static final long serialVersionUID = 1L;
	
	private List<ProductVo> prodList;

	public List<ProductVo> getProdList() {
		return prodList;
	}

	public void setProdList(List<ProductVo> prodList) {
		this.prodList = prodList;
	}
}
