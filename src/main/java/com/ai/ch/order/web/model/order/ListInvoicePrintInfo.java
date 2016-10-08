package com.ai.ch.order.web.model.order;

import java.util.ArrayList;
import java.util.List;

public class ListInvoicePrintInfo {

	private List<InvoicePrintInfo> list = new ArrayList<InvoicePrintInfo>();
	private String id;

	public List<InvoicePrintInfo> getList() {
		return list;
	}

	public void setList(List<InvoicePrintInfo> list) {
		this.list = list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
