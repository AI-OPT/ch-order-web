package com.ai.ch.order.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InvoiceContoller {
	@RequestMapping("/toInvoice")
	public ModelAndView toInvoice(HttpServletRequest request) {
		
		return new ModelAndView("jsp/order/invoice");
	}
}
