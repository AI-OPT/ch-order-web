package com.ai.ch.order.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.order.api.deliveryorderprint.interfaces.IDeliveryOrderPrintSV;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderQueryResponse;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;

@Controller
@RequestMapping("/order")
public class orderController {
	
		private static final Logger LOG = LoggerFactory.getLogger(DeliveryOrderPrintController.class);
		
		@RequestMapping("/noPaidQuery")
		@ResponseBody
		public ResponseData<DeliveryOrderQueryResponse> noPaidQuery(HttpServletRequest request,String orderId) {
			ResponseData<DeliveryOrderQueryResponse> responseData =null;
			try {
				DeliveryOrderPrintRequest req=new DeliveryOrderPrintRequest();
				/*	 SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
				if (user==null)
				    throw new BusinessException("","请先登录");
				*/
				req.setOrderId(35913355l);
				req.setUserId("000000000000000945");
				req.setTenantId("SLP");
				IOrderListSV orderListSV=DubboConsumerFactory.getService(IOrderListSV.class);
				QueryOrderResponse response=orderListSV.queryOrder(null);
				if(response!=null && response.getResponseHeader().isSuccess()) {
					//responseData = new ResponseData<DeliveryOrderQueryResponse>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",response);
				}else{
					responseData = new ResponseData<DeliveryOrderQueryResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
				}
			} catch (Exception e) {
				responseData = new ResponseData<DeliveryOrderQueryResponse>(ResponseData.AJAX_STATUS_FAILURE, "查询出错,出现未知异常");
				LOG.error("查询信息出错",e);
			}
			return responseData;
		}
		

}
