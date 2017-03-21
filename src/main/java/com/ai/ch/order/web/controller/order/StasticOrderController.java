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

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.LogisticsDetail;
import com.ai.ch.order.web.model.order.OrdProdVo;
import com.ai.ch.order.web.model.order.OrderDetail;
import com.ai.ch.order.web.model.order.StasticOrderReqVo;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.ch.user.api.shopinfo.interfaces.IShopInfoSV;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class StasticOrderController {
	private static final Logger LOG = LoggerFactory.getLogger(StasticOrderController.class);
	
	//跳转订单统计
	@RequestMapping("/toStasticOrder")
	public ModelAndView toAlertOrder(HttpServletRequest request) {
		return new ModelAndView("jsp/order/stasticOrder");
	}
	/**
     * 数据查询
     * @param request
     * @return
     */
    @RequestMapping("/getStasticOrderData")
    @ResponseBody
    public ResponseData<PageInfo<BehindParentOrdOrderVo>> getList(HttpServletRequest request,StasticOrderReqVo reqVo){
    	BehindQueryOrderListRequest queryRequest = new BehindQueryOrderListRequest();
    	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        IShopInfoSV iShopInfoSV = DubboConsumerFactory.getService(IShopInfoSV.class);
        ResponseData<PageInfo<BehindParentOrdOrderVo>> responseData = null;
        if(!StringUtil.isBlank(reqVo.getSupplierName())){
        	//根据店铺名称获取销售商ID
            QueryShopInfoRequest shopReq = new QueryShopInfoRequest();
            shopReq.setTenantId(user.getTenantId());
            shopReq.setShopName(reqVo.getSupplierName());
            QueryShopInfoResponse base = iShopInfoSV.queryShopInfo(shopReq);
            if(base.getResponseHeader().getIsSuccess()==true){
            	queryRequest.setSupplierId(base.getUserId()==null?reqVo.getSupplierName():base.getUserId());
            }
        }
        if(!StringUtil.isBlank(reqVo.getUserName())){
        	queryRequest.setUserName(reqVo.getUserName());
        }
        if(!StringUtil.isBlank( reqVo.getStartTime())){
        	queryRequest.setOrderTimeBegin( reqVo.getStartTime()+ " 00:00:00");
	    }
	    if(!StringUtil.isBlank(reqVo.getEndTime())){
	    	queryRequest.setOrderTimeEnd(reqVo.getEndTime()+ " 23:59:59");
	    }
        if(!StringUtil.isBlank(reqVo.getOrdParenOrderId())){
        	 boolean isNum = reqVo.getOrdParenOrderId().matches("[0-9]+");
        	 if(isNum) {
        		 queryRequest.setOrderId(Long.parseLong(reqVo.getOrdParenOrderId()));
 			 }else {
 				queryRequest.setOrderId(0l);
 			 }
		}else {
			queryRequest.setOrderId(null);
		}
        if(!StringUtil.isBlank(reqVo.getProdName())){
        	queryRequest.setProdName(reqVo.getProdName());
       }
        queryRequest.setTenantId(user.getTenantId());
        queryRequest.setParentOrderState(reqVo.getState());
        String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
        try {
        	queryRequest.setPageNo(Integer.parseInt(strPageNo));
        	queryRequest.setPageSize(Integer.parseInt(strPageSize));
        	IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
			BehindQueryOrderListResponse orderListResponse = iOrderListSV.behindQueryOrderList(queryRequest);
			if (orderListResponse != null && orderListResponse.getResponseHeader().isSuccess()) {
				PageInfo<BehindParentOrdOrderVo> pageInfo = orderListResponse.getPageInfo();
				List<BehindParentOrdOrderVo> orderListVos = pageInfo.getResult();
				if(!CollectionUtil.isEmpty(orderListVos)) {
					for (BehindParentOrdOrderVo ordOrderListVo : orderListVos) {
						QueryShopInfoRequest shopReq = new QueryShopInfoRequest();
                        shopReq.setTenantId(user.getTenantId());
                        shopReq.setUserId(ordOrderListVo.getSupplierid());
                        QueryShopInfoResponse base = iShopInfoSV.queryShopInfo(shopReq);
                        if(base.getResponseHeader().getIsSuccess()==true){
                        	ordOrderListVo.setSuppliername(base.getShopName());
                        }
					}
				}
				responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",pageInfo);
			} else {
				responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    
    //查看统计页面详情
    @RequestMapping("/orderDetail")
	public ModelAndView orderListDetail(HttpServletRequest request, 
			String orderId,String state,String pOrderId,String busiCode) {
    	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
    	Map<String, OrderDetail> model = new HashMap<String, OrderDetail>();
    	try {
				QueryOrderRequest queryRequest=new QueryOrderRequest();
				if(Constants.OrdOrder.State.WAIT_PAY.equals(state)||
                		Constants.OrdOrder.State.CANCEL.equals(state)||
                		Constants.OrdOrder.State.COMPLETED.equals(state)&&StringUtil.isBlank(orderId)){
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
					userReq.setNo(orderDetail.getOperId());
					SysUserQueryResponse  response = iSysUserQuerySV.queryUserInfo(userReq);
					if(response!=null){
						orderDetail.setUsername(response.getName());
					}
					//翻译订单来源
					SysParamSingleCond	param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(orderDetail.getChlId());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_CHL_ID);
            		SysParam chldParam = iCacheSV.getSysParamSingle(param);
            		if(chldParam!=null){
            			orderDetail.setChlId(chldParam.getColumnDesc());
            		}
            		//翻译物流公司
					SysParamSingleCond	expressParam = new SysParamSingleCond();
					expressParam.setTenantId(Constants.TENANT_ID);
					expressParam.setColumnValue(orderDetail.getExpressId());
					expressParam.setTypeCode(Constants.TYPE_CODE);
					expressParam.setParamCode(Constants.ORD_EXPRESS);
            		SysParam sysParam = iCacheSV.getSysParamSingle(expressParam);
            		if(sysParam!=null){
            			orderDetail.setExpressName(sysParam.getColumnDesc());
            		}
            		//翻译订单应收/优惠金额、运费
					orderDetail.setOrdAdjustFee(AmountUtil.LiToYuan(ordOrderVo.getAdjustFee()));
					orderDetail.setOrdDiscountFee(AmountUtil.LiToYuan(ordOrderVo.getDiscountFee()));
					orderDetail.setOrdFreight(AmountUtil.LiToYuan(ordOrderVo.getFreight()));
					//翻译配送方式
					SysParamSingleCond	paramLogistics = new SysParamSingleCond();
					paramLogistics.setTenantId(Constants.TENANT_ID);
					paramLogistics.setColumnValue(orderDetail.getLogisticsType());
					paramLogistics.setTypeCode(Constants.ORD_LOGISTICS_TYPE);
					paramLogistics.setParamCode(Constants.LOGISTICS_TYPE);
            		SysParam LogisticsParam = iCacheSV.getSysParamSingle(paramLogistics);
            		if(LogisticsParam!=null){
            			orderDetail.setLogisticsType(LogisticsParam.getColumnDesc());
            		}
					List<OrdProductVo> productList = ordOrderVo.getProductList();
					if(!CollectionUtil.isEmpty(productList)) {
						for (OrdProductVo ordProductVo : productList) {
							OrdProdVo product = new OrdProdVo();
							//翻译金额
							product.setProdSalePrice(AmountUtil.LiToYuan(ordProductVo.getSalePrice()));
							product.setProdAdjustFee(AmountUtil.LiToYuan(ordProductVo.getAdjustFee()));
						//	product.setImageUrl(ImageUtil.getImage(ordProductVo.getProductImage().getVfsId(), ordProductVo.getProductImage().getPicType()));
							product.setProdState(ordProductVo.getState());
							product.setProdName(ordProductVo.getProdName());
							product.setBuySum(ordProductVo.getBuySum());
							product.setProdCouponFee(AmountUtil.LiToYuan(ordProductVo.getCouponFee()));
							product.setJfFee(ordProductVo.getJfFee());
							product.setGiveJF(ordProductVo.getGiveJF());
							/*product.setAfterSaleImageUrl(ImageUtil.getImage(ordProductVo.getImageUrl(),
							ordProductVo.getProdExtendInfo())); */  // 售后图片  
							product.setCusServiceFlag(ordProductVo.getCusServiceFlag());
							product.setProdDetalId(ordProductVo.getProdDetalId());
							product.setSkuId(ordProductVo.getSkuId());
							prodList.add(product);
						}
					}
					orderDetail.setProdList(prodList);
					// 翻译物流信息
					if((!StringUtil.isBlank(orderDetail.getExpressId())) && (!StringUtil.isBlank(orderDetail.getExpressOddNumber()))){
						orderDetail.setLogisticsDetail(getLogisticsDetails(orderDetail.getExpressId(),orderDetail.getExpressOddNumber()));
					}
				}
			}
			model.put("orderDetail", orderDetail);
			if(Constants.OrdOrder.State.WAIT_PAY.equals(state)) { //待付款
				return new ModelAndView("jsp/order/staticUnpaidDetail", model);
			}
			if(Constants.OrdOrder.State.WAIT_DISTRIBUTION.equals(state) ||Constants.OrdOrder.State.PAID.equals(state)) { //已付款(待配货)
				return new ModelAndView("jsp/order/staticPaidOrder", model);
			}
			if(Constants.OrdOrder.State.WAIT_DELIVERY.equals(state)||
					Constants.OrdOrder.State.WAIT_SEND.equals(state)) { //待发货
				return new ModelAndView("jsp/order/staticWaitInvoiceDetails", model);
			}
			if(Constants.OrdOrder.State.WAIT_CONFIRM.equals(state)) { //已发货
				return new ModelAndView("jsp/order/staticAlreadySendsOrder", model);
			}
			if(Constants.OrdOrder.State.COMPLETED.equals(state)) { //已完成
				return new ModelAndView("jsp/order/staticDoneOrder", model);
			}
			if(Constants.OrdOrder.State.CANCEL.equals(state)) { //已关闭
				return new ModelAndView("jsp/order/staticClose", model);
			}
			if(Constants.OrdOrder.State.RETURN_COMPLETE.equals(state)||         //退货完成
					Constants.OrdOrder.State.EXCHANGE_COMPLETE.equals(state)||  //换货完成 
					Constants.OrdOrder.State.REFUND_COMPLETE.equals(state)) {   //退款完成
				return new ModelAndView("jsp/order/staticAfterComplete", model);
			}
			if((Constants.OrdOrder.State.WAIT_CHECK.equals(state)||         //待审核
					Constants.OrdOrder.State.WAIT_BACK.equals(state)||  //待买家退货
					Constants.OrdOrder.State.WAIT_GET_GOODS.equals(state) ||    //待卖家收货确认
					Constants.OrdOrder.State.NO_CHECK.equals(state))&& Constants.OrdOrder.BusiCode.EXCHANGE_ORDER.equals(busiCode)
					) {   
				return new ModelAndView("jsp/order/staticChangeGoodsFirst", model);
			}
			if((Constants.OrdOrder.State.WAIT_CHECK.equals(state)||         //待审核
					Constants.OrdOrder.State.WAIT_BACK.equals(state)||  //待买家退货
					Constants.OrdOrder.State.WAIT_GET_GOODS.equals(state) || //待卖家收货确认
					Constants.OrdOrder.State.WAIT_BACK_FEE.equals(state) ||    //待退费
					Constants.OrdOrder.State.REFUND_ING.equals(state) ||   //处理中
					Constants.OrdOrder.State.NO_AGAIN_CHECK.equals(state)|| 
					Constants.OrdOrder.State.NO_CHECK.equals(state))&& Constants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(busiCode)
					) {   
				return new ModelAndView("jsp/order/staticBackGoodsFirst", model);
			}
			if((Constants.OrdOrder.State.WAIT_CHECK.equals(state)||         //待审核
					Constants.OrdOrder.State.WAIT_BACK_FEE.equals(state) || //待退费
					Constants.OrdOrder.State.REFUND_ING.equals(state) ||   //处理中
					Constants.OrdOrder.State.NO_AGAIN_CHECK.equals(state)||
					Constants.OrdOrder.State.NO_CHECK.equals(state))&& Constants.OrdOrder.BusiCode.CANCEL_ORDER.equals(busiCode)     //审核失败
					) {   
				return new ModelAndView("jsp/order/staticBackGoodsFirst", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("订单详情查询报错：", e);
		}
		return null;
	}
    /**
	 * 获取物流信息
	 * @param com
	 * @param oderNo
	 * @return  List<LogisticsDetail>
	 */
	private List<LogisticsDetail> getLogisticsDetails(String com,String oderNo) {
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
				LOG.error("物流信息请求失败,请求错误码："+ reqResultCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("物流信息请求失败,请求错误码：", e);
		}
		return null;
	}
   }
