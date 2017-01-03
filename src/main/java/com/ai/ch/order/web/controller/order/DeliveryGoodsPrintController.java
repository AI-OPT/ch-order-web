package com.ai.ch.order.web.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.api.delivergoods.interfaces.IDeliverGoodsPrintSV;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfoVo;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfosRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/deliveryPrint")
public class DeliveryGoodsPrintController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeliveryGoodsPrintController.class);
	
	@RequestMapping("/query")
	@ResponseBody
	public ResponseData<DeliverGoodsPrintResponse> query(HttpServletRequest request,String orderId) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<DeliverGoodsPrintResponse> responseData =null;
		try {
			DeliverGoodsPrintRequest req=new DeliverGoodsPrintRequest();
			req.setOrderId(Long.valueOf(orderId));
			req.setTenantId(user.getTenantId());
			IDeliverGoodsPrintSV deliverGoodsPrintSV = DubboConsumerFactory.getService(IDeliverGoodsPrintSV.class);
			DeliverGoodsPrintResponse response = deliverGoodsPrintSV.query(req);
			if(response!=null && response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<DeliverGoodsPrintResponse>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",response);
			}else {
				responseData = new ResponseData<DeliverGoodsPrintResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {			
			responseData = new ResponseData<DeliverGoodsPrintResponse>(ResponseData.AJAX_STATUS_FAILURE, "查询出错,出现未知异常");
			LOG.error("查询信息出错",e);
		}
		System.out.println(JSON.toJSON(responseData));
		return responseData;
	}
	
	
	@RequestMapping("/print")
	@ResponseBody
	public ResponseData<BaseResponse> print(HttpServletRequest request,String orderId,
			String orderInfos) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<BaseResponse> responseData =null;
		List<DeliverGoodsPrintInfoVo> vos =new ArrayList<DeliverGoodsPrintInfoVo>();
		    try {
		    	JSONArray jsStr = JSONArray.parseArray(orderInfos);
	            Iterator<Object> it = jsStr.iterator();
	            while (it.hasNext()) {
	            	List<Long> list=new ArrayList<Long>();
	                JSONObject ob = (JSONObject) it.next();
	                DeliverGoodsPrintInfoVo passport = (DeliverGoodsPrintInfoVo)JSON.toJavaObject(ob, DeliverGoodsPrintInfoVo.class);
	                String mergeOrderId = ob.getString("mergeOrderId");
	                String price= ob.getString("price");
	                passport.setSalePrice(AmountUtil.YToLi(price));
	                if(!StringUtil.isBlank(mergeOrderId)){
	                	String[] mer = mergeOrderId.split(",");
	                	for (String str : mer) {
	                		list.add(Long.parseLong(str));
						}
	                }
	                passport.setHorOrderId(list);
	                vos.add(passport);
	            }
			DeliverGoodsPrintInfosRequest req=new DeliverGoodsPrintInfosRequest();
			req.setOrderId(Long.valueOf(orderId));
			req.setInvoicePrintVos(vos);
			req.setTenantId(user.getTenantId());
			IDeliverGoodsPrintSV deliveryOrderPrintSV = DubboConsumerFactory.getService(IDeliverGoodsPrintSV.class);
			BaseResponse response = deliveryOrderPrintSV.print(req);
			if(response!=null && response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<BaseResponse>(ResponseData.AJAX_STATUS_SUCCESS, "发货单打印成功",response);
			}else {
				responseData = new ResponseData<BaseResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {			
			responseData = new ResponseData<BaseResponse>(ResponseData.AJAX_STATUS_FAILURE, "打印出错,出现未知异常");
			LOG.error("打印信息出错",e);
		}
		return responseData;
	}
	
	
	@RequestMapping("/deliverGoods")
	public ModelAndView deliverGoods(HttpServletRequest request,String orderId,String pOrderId,String state,String busiCode,String Flag) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("orderId", orderId);
		model.put("pOrderId", pOrderId);
		model.put("state", state);
		model.put("busiCode", busiCode);
		model.put("Flag", Flag);
		return new ModelAndView("jsp/order/sendGoods",model);
	}
}
