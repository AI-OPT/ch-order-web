package com.ai.ch.order.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SendGoodsController {
	private static final Logger LOG = Logger.getLogger(SendGoodsController.class);
	@RequestMapping("/toSendGoods")
	public ModelAndView toSendGoods(HttpServletRequest request) {

		return new ModelAndView("jsp/order/sendGoods");
	}
}
