package com.ai.ch.order.web.controller.order;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.platform.common.api.sysuser.interfaces.ISysUserQuerySV;
import com.ai.platform.common.api.sysuser.param.SysUserQueryRequest;
import com.ai.platform.common.api.sysuser.param.SysUserQueryResponse;
import com.ai.slp.order.api.ordermodify.interfaces.INotPaidOrderModifySV;
import com.ai.slp.order.api.ordermodify.param.OrderModifyRequest;

@Controller
public class UnPaidOrderController {
	private static final Logger LOG = LoggerFactory.getLogger(UnPaidOrderController.class);
	
		//修改金额
		@RequestMapping("/changeMoney")
		@ResponseBody
		public ResponseData<String> Change(HttpServletRequest request, String orderId,String changeInfo,String money) {
			ResponseData<String> responseData = null;
			OrderModifyRequest req = new OrderModifyRequest();
			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
			try {
				INotPaidOrderModifySV iNotPaidOrderModifySV = DubboConsumerFactory.getService(INotPaidOrderModifySV.class);
				ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
				SysUserQueryRequest  userReq = new SysUserQueryRequest ();
				userReq.setTenantId(user.getTenantId());
				userReq.setId(user.getUserId());
				SysUserQueryResponse  response = iSysUserQuerySV.queryUserInfo(userReq);
				if(response!=null){
					String no = response.getNo();
					req.setOperId(no);
				}
				Long Id = Long.parseLong(orderId);
				req.setTenantId(user.getTenantId());
				req.setOrderId(Id);
				if(!StringUtil.isBlank(changeInfo)){
					req.setUpdateRemark(changeInfo);
				}
				//将元转换里
				req.setUpdateAmount(AmountUtil.YToLi(money));
				BaseResponse base = iNotPaidOrderModifySV.modify(req);
				if(base.getResponseHeader().getIsSuccess()==true){
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "修改金额成功", null);
				}else{
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, base.getResponseHeader().getResultMessage(), null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("修改金额报错：", e);
			}
			return responseData;
		}
}
