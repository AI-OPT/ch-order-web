package com.ai.ch.order.web.controller.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.order.api.deliveryorderprint.interfaces.IDeliveryOrderPrintSV;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintInfosRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderQueryResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryProdPrintVo;
import com.alibaba.fastjson.JSON;
import com.esotericsoftware.minlog.Log;

@Controller
@RequestMapping("/order")
public class DeliveryOrderPrintController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeliveryOrderPrintController.class);
	
	@RequestMapping("/query")
	@ResponseBody
	public ResponseData<DeliveryOrderQueryResponse> query(HttpServletRequest request,String orderId) {
		ResponseData<DeliveryOrderQueryResponse> responseData =null;
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		try {
			DeliveryOrderPrintRequest req=new DeliveryOrderPrintRequest();
			req.setOrderId(Long.parseLong(orderId));
			//req.setUserId(user.getUserId());
			req.setUserId("000000000000000945");
			req.setTenantId(user.getTenantId());
			IDeliveryOrderPrintSV iDeliveryOrderPrintSV = DubboConsumerFactory.getService(IDeliveryOrderPrintSV.class);
			DeliveryOrderQueryResponse response = iDeliveryOrderPrintSV.query(req);
			if(response!=null && response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<DeliveryOrderQueryResponse>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",response);
			}else{
				responseData = new ResponseData<DeliveryOrderQueryResponse>(ResponseData.AJAX_STATUS_FAILURE, "查询打印的信息失败");
			}
		} catch (Exception e) {
			responseData = new ResponseData<DeliveryOrderQueryResponse>(ResponseData.AJAX_STATUS_FAILURE, "查询出错,出现未知异常");
			LOG.error("查询信息出错",e);
		}
		LOG.info(JSON.toJSONString(responseData));
		return responseData;
	}
	
	
	@RequestMapping("/noMergeQuery")
	@ResponseBody
	public ResponseData<DeliveryOrderPrintResponse> noMergeQuery(HttpServletRequest request,String orderId) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<DeliveryOrderPrintResponse> responseData =null;
		try{
			IDeliveryOrderPrintSV iDeliveryOrderPrintSV = DubboConsumerFactory.getService(IDeliveryOrderPrintSV.class);
			DeliveryOrderPrintRequest req=new DeliveryOrderPrintRequest();
			req.setOrderId(Long.parseLong(orderId));
			//req.setUserId(user.getUserId());
			req.setUserId("000000000000000945");
			req.setTenantId(user.getTenantId());
			DeliveryOrderPrintResponse response = iDeliveryOrderPrintSV.noMergePrint(req);
			if(response!=null && response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<DeliveryOrderPrintResponse>(ResponseData.AJAX_STATUS_SUCCESS, "提货单不合并查询成功",response);
			}else {
				responseData = new ResponseData<DeliveryOrderPrintResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			responseData = new ResponseData<DeliveryOrderPrintResponse>(ResponseData.AJAX_STATUS_FAILURE, "提货单不合并查询出错,出现未知异常");
			LOG.error("提货单不合并查询出错",e);
		}
		LOG.info("responseData:"+com.alibaba.fastjson.JSON.toJSONString(responseData));
    	return responseData;
	}
	
	
	
	@RequestMapping("/display")
	@ResponseBody
	public ResponseData<DeliveryOrderPrintResponse> display(HttpServletRequest request,String orderId) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<DeliveryOrderPrintResponse> responseData =null;
	    try {
			DeliveryOrderPrintRequest req=new DeliveryOrderPrintRequest();
			req.setOrderId(Long.parseLong(orderId));
			//req.setUserId(user.getUserId());
			//TODO
			req.setUserId("000000000000000945");
			req.setTenantId(user.getTenantId());
			IDeliveryOrderPrintSV iDeliveryOrderPrintSV = DubboConsumerFactory.getService(IDeliveryOrderPrintSV.class);
			DeliveryOrderPrintResponse response = iDeliveryOrderPrintSV.display(req);
			if(response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<DeliveryOrderPrintResponse>(ResponseData.AJAX_STATUS_SUCCESS, "提货单信息显示成功",response);
			}else {
				responseData = new ResponseData<DeliveryOrderPrintResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			responseData = new ResponseData<DeliveryOrderPrintResponse>(ResponseData.AJAX_STATUS_FAILURE, "提货单信息显示出错,出现未知异常");
			LOG.error("提货单信息显示出错",e);
		}
	    Log.info("responseData:"+JSON.toJSONString(responseData));
	    return responseData;
	}
	
	
	@RequestMapping("/print")
	@ResponseBody
	public ResponseData<Boolean> print(HttpServletRequest request,String orderId,
			String contactName,String orderInfos) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<Boolean> responseData =null;
	    try {
			DeliveryOrderPrintInfosRequest req=new DeliveryOrderPrintInfosRequest();
			List<DeliveryProdPrintVo> vos = JSON.parseArray(orderInfos, DeliveryProdPrintVo.class); 
			req.setOrderId(Long.valueOf(orderId));
			req.setContactName(contactName);
			req.setDeliveryProdPrintVos(vos);
			req.setTenantId(user.getTenantId());
			IDeliveryOrderPrintSV iDeliveryOrderPrintSV = DubboConsumerFactory.getService(IDeliveryOrderPrintSV.class);
			BaseResponse response = iDeliveryOrderPrintSV.print(req);
			if(response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "打印成功",true);
			}else {
				responseData = new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage(),false);
			}
		} catch (Exception e) {
			responseData = new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "打印出错,出现未知异常",
					false);
			LOG.error("打印出错",e);
		}
	    return responseData;
	 }
}
