package com.ai.ch.order.web.controller.order;

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
import com.ai.platform.common.api.sysuser.interfaces.ISysUserQuerySV;
import com.ai.platform.common.api.sysuser.param.SysUserQueryRequest;
import com.ai.platform.common.api.sysuser.param.SysUserQueryResponse;
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;

@Controller
@RequestMapping("/aftersaleorder")
public class AfterSaleOrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AfterSaleOrderController.class);
	
	@RequestMapping("/back")
	@ResponseBody
	public ResponseData<String> back(HttpServletRequest request, String orderId,
			String prodDetalId,String prodSum) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<String> data=null;
		try {
			OrderReturnRequest req=new OrderReturnRequest();
			req.setOrderId(Long.parseLong(orderId));
			req.setProdDetalId(Long.parseLong(prodDetalId));
			req.setProdSum(Long.parseLong(prodSum));
			req.setTenantId(user.getTenantId());
			//获取售后操作人
			ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
			SysUserQueryRequest  userReq = new SysUserQueryRequest ();
			userReq.setTenantId(user.getTenantId());
			userReq.setId(user.getUserId());
			SysUserQueryResponse  responseSys = iSysUserQuerySV.queryUserInfo(userReq);
			if(responseSys!=null){
				String no = responseSys.getNo();
				req.setOperId(no);
			}
			IOrderAfterSaleSV orderAfterSaleSV = DubboConsumerFactory.getService(IOrderAfterSaleSV.class);
			BaseResponse response = orderAfterSaleSV.back(req);
			if(response.getResponseHeader().isSuccess()) {
				data=new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退货成功", null);
			}else {
				data=new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "退货出错", null);
			}
		} catch (Exception e) {
			LOG.info(e.getMessage());
			data=new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "退货失败,出现未知异常", null);
		}
		return data;
	}
	
	@RequestMapping("/exchange")
	@ResponseBody
	public ResponseData<String> exchange(HttpServletRequest request, String orderId,String prodDetalId) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<String> data=null;
		try {
			OrderReturnRequest req=new OrderReturnRequest();
			req.setOrderId(Long.parseLong(orderId));
			req.setProdDetalId(Long.parseLong(prodDetalId));
			req.setTenantId(user.getTenantId());
			//获取售后操作人
			ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
			SysUserQueryRequest  userReq = new SysUserQueryRequest ();
			userReq.setTenantId(user.getTenantId());
			userReq.setId(user.getUserId());
			SysUserQueryResponse  responseSys = iSysUserQuerySV.queryUserInfo(userReq);
			if(responseSys!=null){
				String no = responseSys.getNo();
				req.setOperId(no);
			}
			IOrderAfterSaleSV orderAfterSaleSV = DubboConsumerFactory.getService(IOrderAfterSaleSV.class);
			BaseResponse response = orderAfterSaleSV.exchange(req);
			if(response.getResponseHeader().isSuccess()) {
				data=new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "换货成功", null);
			}else {
				data=new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "换货出错", null);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			data=new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "换货失败,出现未知异常", null);
		}
		return data;
	}
	
	@RequestMapping("/refund")
	@ResponseBody
	public ResponseData<String> refund(HttpServletRequest request, String orderId,String prodDetalId) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<String> data=null;
		try {
			OrderReturnRequest req=new OrderReturnRequest();
			req.setOrderId(Long.parseLong(orderId));
			req.setProdDetalId(Long.parseLong(prodDetalId));
			req.setTenantId(user.getTenantId());
			//获取售后操作人
			ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
			SysUserQueryRequest  userReq = new SysUserQueryRequest ();
			userReq.setTenantId(user.getTenantId());
			userReq.setId(user.getUserId());
			SysUserQueryResponse  responseSys = iSysUserQuerySV.queryUserInfo(userReq);
			if(responseSys!=null){
				String no = responseSys.getNo();
				req.setOperId(no);
			}
			IOrderAfterSaleSV orderAfterSaleSV = DubboConsumerFactory.getService(IOrderAfterSaleSV.class);
			BaseResponse response = orderAfterSaleSV.refund(req);
			if(response.getResponseHeader().isSuccess()) {
				data=new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款成功", null);
			}else {
				data=new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "退款出错", null);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			data=new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "退款失败,出现未知异常", null);
		}
		return data;
	}

}
