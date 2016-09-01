package com.ai.ch.order.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.alibaba.fastjson.JSON;

@Controller
public class UnPaidOrderController {
	
	@RequestMapping("/toUnpaidOrder")
	public ModelAndView register(HttpServletRequest request) {
		BehindQueryOrderListRequest orderListRequest=new BehindQueryOrderListRequest();
		orderListRequest.setTenantId("SLP");
		orderListRequest.setPageNo(1);
		orderListRequest.setPageSize(5);
		IOrderListSV orderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
		BehindQueryOrderListResponse response = orderListSV.behindQueryOrderList(orderListRequest);
		System.out.println(JSON.toJSONString(response));
		return new ModelAndView("jsp/order/unpaidOrderList");
	}
}
