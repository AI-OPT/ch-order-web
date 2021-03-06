package com.ai.ch.order.web.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.LogisticsDetail;
import com.ai.ch.order.web.model.order.OrdOrderListVo;
import com.ai.ch.order.web.model.order.OrdProdVo;
import com.ai.ch.order.web.model.order.OrderDetail;
import com.ai.ch.order.web.model.order.OrderListQueryParams;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.ch.order.web.utils.TranslateFiledsUtil;
import com.ai.net.xss.util.CollectionUtil;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.sysuser.interfaces.ISysUserQuerySV;
import com.ai.platform.common.api.sysuser.param.SysUserQueryRequest;
import com.ai.platform.common.api.sysuser.param.SysUserQueryResponse;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.ai.slp.route.api.routemanage.interfaces.IRouteManageSV;
import com.ai.slp.route.api.routemanage.param.RouteIdParamRequest;
import com.ai.slp.route.api.routemanage.param.RouteResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/order")
public class OrderListController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderListController.class);
	
	//跳转到订单列表
	@RequestMapping("/toOrderList")
	public ModelAndView toAlertOrder(HttpServletRequest request,String stateFlag ) {
		request.setAttribute("stateFlag", stateFlag);
		return new ModelAndView("jsp/order/orderList");
	}
	
	/**
     * 订单信息查询
     */
    @RequestMapping("/getOrderListData")
    @ResponseBody
    public ResponseData<PageInfo<OrdOrderListVo>> getList(HttpServletRequest request,OrderListQueryParams queryParams){
    	ResponseData<PageInfo<OrdOrderListVo>> responseData = null;
	    try{
	    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
	    	BehindQueryOrderListRequest queryRequest = new BehindQueryOrderListRequest();
			//拷贝属性
	    	BeanUtils.copyProperties(queryRequest, queryParams);
	    	//父订单参数判断及类型转换
			if(!StringUtil.isBlank(queryParams.getParentOrderId())) {
				boolean isNum = queryParams.getParentOrderId().matches("[0-9]+");
				if(isNum) {
					queryRequest.setOrderId(Long.parseLong(queryParams.getParentOrderId()));
				}else {
					queryRequest.setOrderId(0l);
				}
			} else {
				queryRequest.setOrderId(null);
			}
			List<Object> stateList = this.stringToList(queryParams);
			queryRequest.setStateList(stateList);
			//订单业务标识
			List<Object> flagList = new ArrayList<Object>();
			flagList.add(Constants.OrdOrder.Flag.UPPLATFORM);
			queryRequest.setFlagList(flagList);
			
			if (!StringUtil.isBlank( queryRequest.getOrderTimeBegin())) {
				queryRequest.setOrderTimeBegin( queryRequest.getOrderTimeBegin() + " 00:00:00");
			}
			if (!StringUtil.isBlank(queryRequest.getOrderTimeEnd())) {
				queryRequest.setOrderTimeEnd(queryRequest.getOrderTimeEnd() + " 23:59:59");
			}
			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"5":request.getParameter("pageSize");
		    queryRequest.setPageNo(Integer.parseInt(strPageNo));
		    queryRequest.setPageSize(Integer.parseInt(strPageSize));
			queryRequest.setTenantId(Constants.TENANT_ID);
			queryRequest.setUserName(queryParams.getUsername());
			
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
			BehindQueryOrderListResponse orderListResponse = iOrderListSV.behindQueryOrderList(queryRequest);
			PageInfo<OrdOrderListVo> pageInfoVo = new PageInfo<OrdOrderListVo>();
			if (orderListResponse != null && orderListResponse.getResponseHeader().isSuccess()) {
				PageInfo<BehindParentOrdOrderVo> pageInfo = orderListResponse.getPageInfo();
				BeanUtils.copyProperties(pageInfoVo, pageInfo);
				List<BehindParentOrdOrderVo> result = pageInfo.getResult();
				List<OrdOrderListVo> orderList=new ArrayList<OrdOrderListVo>();
				if(!CollectionUtil.isEmpty(result)) {
					for (BehindParentOrdOrderVo behindParentOrdOrderVo : result) {
						OrdOrderListVo orderListVo=new OrdOrderListVo();
						BeanUtils.copyProperties(orderListVo, behindParentOrdOrderVo);
						//翻译订单来源
						SysParam chldParam = TranslateFiledsUtil.translateInfo(Constants.TENANT_ID, 
								Constants.TYPE_CODE,Constants.ORD_CHL_ID,orderListVo.getChlid(), iCacheSV);
						orderListVo.setChlidname(chldParam == null ? "" : chldParam.getColumnDesc());
						orderListVo.setTotalAdjustFee(AmountUtil.LiToYuan(behindParentOrdOrderVo.getAdjustfee()));
						orderListVo.setOrderTotalDiscountFee(AmountUtil.LiToYuan(behindParentOrdOrderVo.getDiscountfee()));
						orderListVo.setTotalJF(behindParentOrdOrderVo.getPoints());
						orderList.add(orderListVo);
					}
				}
				pageInfoVo.setResult(orderList);
				responseData = new ResponseData<PageInfo<OrdOrderListVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",pageInfoVo);
			} else {
				responseData = new ResponseData<PageInfo<OrdOrderListVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询订单列表失败：", e);
			responseData = new ResponseData<PageInfo<OrdOrderListVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }

    //订单详情查看
    @RequestMapping("/orderListDetail")
	public ModelAndView orderListDetail(HttpServletRequest request, String busiCode,
			String orderId,String state,String pOrderId,String Flag,String sourceFlag) {
    	try {
	    	//主要用来判断从订单处理来的还是从售后列表来进行查询的
	    	request.setAttribute("sourceFlag", sourceFlag);
	    	GeneralSSOClientUser user = (GeneralSSOClientUser) 
	    			request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
	    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
	    	Map<String, OrderDetail> model = new HashMap<String, OrderDetail>();
    	
			QueryOrderRequest queryRequest=new QueryOrderRequest();
			if(Constants.OrdOrder.State.WAIT_PAY.equals(state)||
            		Constants.OrdOrder.State.CANCEL.equals(state)){
				queryRequest.setOrderId(Long.parseLong(pOrderId));
			}else{
				queryRequest.setOrderId(Long.parseLong(orderId));
			}
			queryRequest.setTenantId(user.getTenantId());
			OrderDetail orderDetail = new OrderDetail();
			List<OrdProdVo> prodList = new ArrayList<OrdProdVo>();
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
			QueryOrderResponse orderResponse = iOrderListSV.queryOrder(queryRequest);
			OrdOrderVo ordOrderVo=null;
			if(orderResponse!=null&&orderResponse.getResponseHeader().isSuccess()) {
				ordOrderVo = orderResponse.getOrdOrderVo();
				if(ordOrderVo!=null) {
					BeanUtils.copyProperties(orderDetail, ordOrderVo);
					//获取售后操作人
					ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
					SysUserQueryRequest  userReq = new SysUserQueryRequest ();
					userReq.setTenantId(user.getTenantId());
					userReq.setNo(orderDetail.getOperid());
					SysUserQueryResponse response = iSysUserQuerySV.queryUserInfo(userReq);
					if(response!=null && response.getResponseHeader().isSuccess() ){
						orderDetail.setAfterSalesOperator(response.getName());
					}
					
					//翻译字段
					TranslateFiledsUtil.translateFileds(orderDetail,iCacheSV);
					//查询仓库信息
					if (orderDetail.getRouteid() != null) {
						// 查询仓库名称
						IRouteManageSV iRouteManageSV = DubboConsumerFactory.getService(IRouteManageSV.class);
						RouteIdParamRequest routeRequest = new RouteIdParamRequest();
						routeRequest.setRouteId(orderDetail.getRouteid());
						RouteResponse routeInfo = iRouteManageSV.findRouteInfo(routeRequest);
						if(routeInfo!=null && routeInfo.getResponseHeader().isSuccess()) {
							orderDetail.setRoutename(routeInfo.getRouteName()); 
						}
					}
            		//翻译订单应收/优惠金额、运费(厘转元)
					orderDetail.setOrdAdjustFee(AmountUtil.LiToYuan(ordOrderVo.getAdjustfee()));
					orderDetail.setOrdDiscountFee(AmountUtil.LiToYuan(ordOrderVo.getDiscountfee()));
					orderDetail.setOrdFreight(AmountUtil.LiToYuan(ordOrderVo.getFreight()));
					orderDetail.setUpdateFee(AmountUtil.LiToYuan(ordOrderVo.getPaidfee()));
					List<OrdProductVo> productList = ordOrderVo.getProductList();
					if(!CollectionUtil.isEmpty(productList)) {
						for (OrdProductVo ordProductVo : productList) {
							OrdProdVo product = new OrdProdVo();
							//翻译金额
							product.setProdSalePrice(AmountUtil.LiToYuan(ordProductVo.getSaleprice()));
							product.setProdAdjustFee(AmountUtil.LiToYuan(ordProductVo.getAdjustfee()));
							product.setImageUrl(ImageUtil.getImage(ordProductVo.getProductimage().getVfsId(), ordProductVo.getProductimage().getPicType()));
							product.setProdState(orderDetail.getBusicode());
							product.setProdName(ordProductVo.getProdname());
							product.setBuySum(ordProductVo.getBuysum());
							product.setProdCouponFee(AmountUtil.LiToYuan(ordProductVo.getCouponfee()));
							product.setJfFee(ordProductVo.getJffee());
							product.setGiveJF(ordProductVo.getGivejf());
							product.setCusServiceFlag(ordProductVo.getCusserviceflag());
							product.setProdDetalId(ordProductVo.getProddetalid());
							product.setSkuId(ordProductVo.getSkuid());
							prodList.add(product);
						}
					}
					orderDetail.setProdList(prodList);
					// 翻译物流信息
					orderDetail.setLogisticsDetail(getLogisticsDetails(orderDetail.getExpressid(),orderDetail.getExpressoddnumber()));
				}
			}
			model.put("orderDetail", orderDetail);
			if(Constants.OrdOrder.State.WAIT_PAY.equals(state)) { //待付款
				return new ModelAndView("jsp/order/unpaidOrderDetail", model);
			}
			/* up平台跳转页面*/
			if(Constants.OrdOrder.State.WAIT_DISTRIBUTION.equals(state) ||Constants.OrdOrder.State.PAID.equals(state)) { //已付款(待配货)
				return new ModelAndView("jsp/order/paidOrderDetails", model);
			}
			if(Constants.OrdOrder.State.WAIT_DELIVERY.equals(state)||
					Constants.OrdOrder.State.WAIT_SEND.equals(state)) { //待发货
				return new ModelAndView("jsp/order/waitInvoiceDetails", model);
			}
			if(Constants.OrdOrder.State.WAIT_CONFIRM.equals(state)) { //已发货
				return new ModelAndView("jsp/order/alreadySendGoods", model);
			}
			if(Constants.OrdOrder.State.COMPLETED.equals(state)) { //已完成
				return new ModelAndView("jsp/order/doneOrder", model);
			}
			if(Constants.OrdOrder.State.CANCEL.equals(state)) { //已关闭
				return new ModelAndView("jsp/order/closeOrder", model);
			}
			if(Constants.OrdOrder.State.RETURN_COMPLETE.equals(state)||         //退货完成
					Constants.OrdOrder.State.EXCHANGE_COMPLETE.equals(state)||  //换货完成 
					Constants.OrdOrder.State.REFUND_COMPLETE.equals(state)) {   //退款完成
				return new ModelAndView("jsp/order/afterComplete", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单详情查询报错：", e);
		}
		return null;
	}
    
    
	/**
	 * 获取物流信息
	 * @param com
	 * @param oderNo
	 * @return  List<LogisticsDetail>
	 */
	private static List<LogisticsDetail> getLogisticsDetails(String com,String oderNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderNo", oderNo);
		params.put("com", com);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appkey", Constants.LOGISTICS_APPKEY);
		String param = JSON.toJSONString(params);
		try {
			String result = HttpClientUtil.sendPost(Constants.LOGISTICS_URL,param,headers);
			 //将返回结果，转换为JSON对象 
	        JSONObject json=JSON.parseObject(result);
	        String reqResultCode=json.getString("resultCode");
	        if("000000".equals(reqResultCode)){
	        	JSONObject data=JSON.parseObject(json.getString("data"));
	        	JSONObject responseHeader=JSON.parseObject(data.getString("responseHeader"));
	        	String success = responseHeader.getString("success");
	        	if("true".equals(success)){
					String dataStr =data.getString("messages");
					JSONArray messages = JSONArray.parseArray(dataStr);
					Iterator<Object> it = messages.iterator();
					List<LogisticsDetail> logisticsDetails = new ArrayList<LogisticsDetail>();
					while (it.hasNext()) {
						LogisticsDetail detail = new LogisticsDetail();
						JSONObject ob = (JSONObject) it.next();
						detail.setTime(ob.getString("time"));
						detail.setContext(ob.getString("context"));
						logisticsDetails.add(detail);
					}
					return logisticsDetails;
	        	}
			} else {
				// 请求过程失败
				logger.error("物流信息请求失败,请求错误码："+ reqResultCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("物流信息请求失败,请求错误码：", e);
		}
		return null;
	}
	
	/**
	 * 字符串转换为list
	 */
	private List<Object> stringToList(OrderListQueryParams queryParams) {
		String states = queryParams.getStates();
		if(!StringUtil.isBlank(states)){
			String[] stateArray = states.split(",");
			List<Object> stateList = new LinkedList<Object>();
			for(String state : stateArray){
				stateList.add(state);
			}
			return stateList;
		}
		return null;
	}
}
