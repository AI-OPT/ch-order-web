package com.ai.ch.order.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;

@Controller
@RequestMapping("/aftersaleorder")
public class AfterSaleOrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AfterSaleOrderController.class);
	
	@RequestMapping("/back")
	@ResponseBody
	public ResponseData<String> back(HttpServletRequest request, String orderId,String prodDetalId) {
		ResponseData<String> data=null;
		try {
			OrderReturnRequest req=new OrderReturnRequest();
			req.setOrderId(Long.parseLong(orderId));
			req.setProdDetalId(Long.parseLong(prodDetalId));
			req.setTenantId("SLP");
			req.setOperId("123");
			IOrderAfterSaleSV orderAfterSaleSV = DubboConsumerFactory.getService(IOrderAfterSaleSV.class);
			BaseResponse response = orderAfterSaleSV.back(req);
			if(response.getResponseHeader().isSuccess()) {
				data=new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退货成功", null);
			}else {
				data=new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage(), null);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			data=new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "退货失败,出现未知异常", null);
		}
		return data;
	}

}
