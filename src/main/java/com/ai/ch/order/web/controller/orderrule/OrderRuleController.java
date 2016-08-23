package com.ai.ch.order.web.controller.orderrule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.ch.order.web.utils.RequestParameterUtils;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.order.api.orderrule.interfaces.IOrderRuleSV;
import com.ai.slp.order.api.orderrule.param.OrderRuleDetailResponse;
import com.ai.slp.order.api.orderrule.param.OrderRuleRequest;
import com.alibaba.fastjson.JSON;
@RequestMapping(value="/orderrule")
@RestController
public class OrderRuleController {
//	110000010000100010：合并
	public static final String MERGE_ORDER_SETTING_ID = "110000010000100010";
//	110000010000100011：时间
	public static final String TIME_MONITOR_ID = "110000010000100011";
//	110000010000100012：数量
	public static final String BUY_EMPLOYEE_MONITOR_ID = "110000010000100012";
//	110000010000100013：IP监控'
	public static final String BUY_IP_MONITOR_ID = "110000010000100013";
	
	private static final Logger log = LoggerFactory.getLogger(OrderRuleController.class);
	//text/html
	//application/json
	@RequestMapping(value="/orderRuleSetting",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String orderRuleSetting(HttpServletRequest request){
		//
		String flag = "true";
		OrderRuleRequest requestVo = RequestParameterUtils.request2Bean(request, OrderRuleRequest.class);
		requestVo.setTimeMonitorId(TIME_MONITOR_ID);
		requestVo.setBuyEmployeeMonitorId(BUY_EMPLOYEE_MONITOR_ID);
		requestVo.setBuyIpMonitorId(BUY_IP_MONITOR_ID);
		requestVo.setMergeOrderSettingId(MERGE_ORDER_SETTING_ID);
		//
		DubboConsumerFactory.getService(IOrderRuleSV.class).orderRuleSetting(requestVo);
		//
		return flag;
	}
	@RequestMapping(value="/findOrderRuleDetail",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public OrderRuleDetailResponse findOrderRuleDetail(HttpServletRequest request){
		OrderRuleDetailResponse response = DubboConsumerFactory.getService(IOrderRuleSV.class).findOrderRuleDetail();
		//
		log.info("response:"+JSON.toJSONString(response));
		return response;
	}
	

}
