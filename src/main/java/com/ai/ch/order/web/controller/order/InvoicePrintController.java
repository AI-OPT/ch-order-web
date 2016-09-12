package com.ai.ch.order.web.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintInfosRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintVo;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/invoice")
public class InvoicePrintController {
	
	private static final Logger LOG = LoggerFactory.getLogger(InvoicePrintController.class);
	
	@RequestMapping("/query")
	@ResponseBody
	public ResponseData<InvoicePrintResponse> query(HttpServletRequest request,String orderId) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<InvoicePrintResponse> responseData =null;
		try {
			InvoicePrintRequest req=new InvoicePrintRequest();
			req.setOrderId(Long.valueOf(orderId));
			req.setTenantId(user.getTenantId());
			IInvoicePrintSV iInvoicePrintSV = DubboConsumerFactory.getService(IInvoicePrintSV.class);
			InvoicePrintResponse response = iInvoicePrintSV.query(req);
			if(response!=null && response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<InvoicePrintResponse>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",response);
			}else {
				responseData = new ResponseData<InvoicePrintResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {			
			responseData = new ResponseData<InvoicePrintResponse>(ResponseData.AJAX_STATUS_FAILURE, "查询出错,出现未知异常");
			LOG.error("查询信息出错",e);
		}
		return responseData;
	}
	
	
	@RequestMapping("/print")
	@ResponseBody
	public ResponseData<BaseResponse> print(HttpServletRequest request,String orderId,
			String orderInfos) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<BaseResponse> responseData =null;
		try {
			InvoicePrintInfosRequest req=new InvoicePrintInfosRequest();
			List<InvoicePrintVo> invoicePrintVos = JSON.parseArray(orderInfos, InvoicePrintVo.class); 
			req.setOrderId(Long.valueOf(orderId));
			req.setInvoicePrintVos(invoicePrintVos);
			req.setTenantId(user.getTenantId());
			IInvoicePrintSV invoicePrintSV = DubboConsumerFactory.getService(IInvoicePrintSV.class);
			BaseResponse response = invoicePrintSV.print(req);
			if(response!=null && response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<BaseResponse>(ResponseData.AJAX_STATUS_SUCCESS, "发货单打印成功",response);
			}else {
				responseData = new ResponseData<BaseResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {			responseData = new ResponseData<BaseResponse>(ResponseData.AJAX_STATUS_FAILURE, "打印出错,出现未知异常");
			LOG.error("打印信息出错",e);
		}
		return responseData;
	}
	
	
	@RequestMapping("/deliverGoods")
	public ModelAndView deliverGoods(HttpServletRequest request,String orderId) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("orderId", orderId);
		return new ModelAndView("jsp/order/sendGoods",model);
	}
	
}
