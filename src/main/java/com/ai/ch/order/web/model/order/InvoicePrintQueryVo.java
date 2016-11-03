package com.ai.ch.order.web.model.order;

import com.ai.slp.order.api.invoiceprint.param.InvoicePrintVo;

public class InvoicePrintQueryVo extends InvoicePrintVo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 发票金额(元)
	 */
	private String invoiceMoney;
	
	/**
	 * 税额(元)
	 */
	private String taxMoney;

	public String getInvoiceMoney() {
		return invoiceMoney;
	}

	public void setInvoiceMoney(String invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}

	public String getTaxMoney() {
		return taxMoney;
	}

	public void setTaxMoney(String taxMoney) {
		this.taxMoney = taxMoney;
	}
}
