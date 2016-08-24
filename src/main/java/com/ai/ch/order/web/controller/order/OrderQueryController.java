package com.ai.ch.order.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;

@Controller
@RequestMapping("/order")
public class OrderQueryController {
	
		private static final Logger LOG = LoggerFactory.getLogger(DeliveryOrderPrintController.class);
		
		@RequestMapping("/paidQuery")
		public String paidQuery(HttpServletRequest request,String orderId,Model model) {
			OrdOrderVo ordOrderVo=null;
			try {
				QueryOrderRequest req=new QueryOrderRequest();
				/*	 SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
				if (user==null)
				    throw new BusinessException("","请先登录");
				*/
				req.setOrderId(35913355l);
				req.setTenantId("SLP");
				IOrderListSV orderListSV=DubboConsumerFactory.getService(IOrderListSV.class);
				QueryOrderResponse response=orderListSV.queryOrder(req);
				ordOrderVo = response.getOrdOrderVo();
				if (!(response.getResponseHeader().getResultCode())
		                    .equals(ExceptCodeConstants.Special.SUCCESS)) {
					LOG.info(response.getResponseHeader().getResultMessage());
		            // 可能会出现创建订单失败的情况，如果这样就调到订单失败页面
		            return "redirect:/order/fail";
		         }
			} catch (Exception e) {
				LOG.info(e.getMessage());
	            // 遇到异常也同上处理
	            return "redirect:/order/fail";
			}
			model.addAttribute("orderInfos",ordOrderVo);
			return "jsp/order/paidOrderDetails";
		}
		
		
		
		@RequestMapping("/waitInvoiceQuery")
		public String waitInvoiceQuery(HttpServletRequest request,String orderId,Model model) {
			OrdOrderVo ordOrderVo=null;
			try {
				QueryOrderRequest req=new QueryOrderRequest();
				/*	 SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
				if (user==null)
				    throw new BusinessException("","请先登录");
				*/
				req.setOrderId(35913355l);
				req.setTenantId("SLP");
				IOrderListSV orderListSV=DubboConsumerFactory.getService(IOrderListSV.class);
				QueryOrderResponse response=orderListSV.queryOrder(req);
				ordOrderVo = response.getOrdOrderVo();
				if (!(response.getResponseHeader().getResultCode())
		                    .equals(ExceptCodeConstants.Special.SUCCESS)) {
					LOG.info(response.getResponseHeader().getResultMessage());
		            // 可能会出现创建订单失败的情况，如果这样就调到订单失败页面
		            return "redirect:/order/fail";
		         }
			} catch (Exception e) {
				LOG.info(e.getMessage());
	            // 遇到异常也同上处理
	            return "redirect:/order/fail";
			}
			model.addAttribute("orderInfos",ordOrderVo);
			return "jsp/order/waitInvoiceDetails";
		}
		
		
		
	   @RequestMapping("/fail")
	   public String toFailPage(HttpServletRequest request, Model model) {
	        return "jsp/order/orderQueryFail";
	   }
}
