package com.ai.ch.order.web.model.order;

import java.util.List;

import com.ai.slp.order.api.orderlist.param.OrdOrderVo;

public class OrderDetail extends OrdOrderVo {
	private static final long serialVersionUID = 1L;

	private List<OrdProdVo> prodList;
	/**
	 * 应收费用
	 */
	private String ordAdjustFee;
	/**
	 * 总优惠费用
	 */

	private String ordDiscountFee;
	/**
	 * 运费
	 */
	private String ordFreight;
	/**
	 * 总费用
	 */
	private String ordTotalFee;
	/**
	 * 退款修改金额
	 */
	private String updateFee;
	/**
	 * 售后操作人
	 */
	private String afterSalesOperator;
;
	/**
	 * 快递公司
	 */
	private String expressName;
	
	private List<LogisticsDetail> logisticsDetail;
	

	public List<LogisticsDetail> getLogisticsDetail() {
		return logisticsDetail;
	}

	public void setLogisticsDetail(List<LogisticsDetail> logisticsDetail) {
		this.logisticsDetail = logisticsDetail;
	}

	public String getOrdDiscountFee() {
		return ordDiscountFee;
	}

	public void setOrdDiscountFee(String ordDiscountFee) {
		this.ordDiscountFee = ordDiscountFee;
	}

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

	public String getOrdFreight() {
		return ordFreight;
	}

	public void setOrdFreight(String ordFreight) {
		this.ordFreight = ordFreight;
	}

	public String getOrdTotalFee() {
		return ordTotalFee;
	}

	public void setOrdTotalFee(String ordTotalFee) {
		this.ordTotalFee = ordTotalFee;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getUpdateFee() {
		return updateFee;
	}

	public void setUpdateFee(String updateFee) {
		this.updateFee = updateFee;
	}

	public String getAfterSalesOperator() {
		return afterSalesOperator;
	}

	public void setAfterSalesOperator(String afterSalesOperator) {
		this.afterSalesOperator = afterSalesOperator;
	}
	
}
