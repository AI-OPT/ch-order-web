package com.ai.ch.order.web.controller.order;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.OrderListQueryParams;
import com.ai.net.xss.util.StringUtil;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;

@Controller
@RequestMapping("/order")
public class OrderListController {
	
	private static final Logger logger = Logger.getLogger(OrderListController.class);
	
	@RequestMapping("/toOrderList")
	public ModelAndView toAlertOrder(HttpServletRequest request) {
		return new ModelAndView("jsp/order/orderList");
	}
	
	/**
     * 订单信息查询
     */
    @RequestMapping("/getOrderListData")
    @ResponseBody
    public ResponseData<PageInfo<BehindParentOrdOrderVo>> getList(HttpServletRequest request,OrderListQueryParams queryParams){
    	ResponseData<PageInfo<BehindParentOrdOrderVo>> responseData = null;
	    try{
	    	BehindQueryOrderListRequest queryRequest = new BehindQueryOrderListRequest();
			BeanUtils.copyProperties(queryRequest, queryParams);
			String states = queryParams.getStates();
			if(!StringUtil.isBlank(states)){
				String[] stateArray = states.split(",");
				List<String> stateList = new LinkedList<String>();
				for(String state : stateArray){
					stateList.add(state);
				}
				queryRequest.setStateList(stateList);
			}
			String orderTimeBegin = queryRequest.getOrderTimeBegin();
			if (!StringUtil.isBlank(orderTimeBegin)) {
				queryRequest.setOrderTimeBegin(orderTimeBegin + " 00:00:00");
			}else{
				queryRequest.setOrderTimeBegin(null);
			}
			String orderTimeEnd = queryRequest.getOrderTimeEnd();
			if (!StringUtil.isBlank(orderTimeEnd)) {
				queryRequest.setOrderTimeEnd(orderTimeEnd + " 23:59:59");
			}else{
				queryRequest.setOrderTimeEnd(null);
			}
			queryRequest.setTenantId("SLP");
			//用户信息  根据username获取用户id??
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
			BehindQueryOrderListResponse orderListResponse = iOrderListSV.behindQueryOrderList(queryRequest);
			if (orderListResponse != null && orderListResponse.getResponseHeader().isSuccess()) {
				PageInfo<BehindParentOrdOrderVo> pageInfo = orderListResponse.getPageInfo();
				responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", pageInfo);
			} else {
				responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询订单列表失败：", e);
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
		}
	    return responseData;
    }


    @RequestMapping("/orderListDetail")
	public ModelAndView changeFirstDetail(HttpServletRequest request, String orderId) {
    	try {
			QueryOrderRequest queryRequest=new QueryOrderRequest();
			queryRequest.setOrderId(Long.parseLong(orderId));
			queryRequest.setTenantId(Constants.TENANT_ID);
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
			QueryOrderResponse orderResponse = iOrderListSV.queryOrder(queryRequest);
			if(orderResponse!=null&&orderResponse.getResponseHeader().isSuccess()) {
				OrdOrderVo ordOrderVo = orderResponse.getOrdOrderVo();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
    	
		return null;
	}

}
