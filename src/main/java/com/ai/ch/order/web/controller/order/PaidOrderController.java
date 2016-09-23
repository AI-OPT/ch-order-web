package com.ai.ch.order.web.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.controller.common.ChUserController;
import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.BehindQueryOrderLisReqVo;
import com.ai.ch.order.web.model.order.OrdProdVo;
import com.ai.ch.order.web.model.order.OrderDetail;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
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
import com.ai.slp.order.api.aftersaleorder.interfaces.IOrderAfterSaleJudgeSV;
import com.ai.slp.order.api.aftersaleorder.param.OrderAfterVo;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageResponse;
import com.ai.slp.order.api.ordercheck.interfaces.IOrderCheckSV;
import com.ai.slp.order.api.ordercheck.param.OrderCheckRequest;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;

@Controller
public class PaidOrderController {
	private static final Logger LOG = Logger.getLogger(PaidOrderController.class);
	@RequestMapping("/toPaidOrder")
	public ModelAndView toPaidOrder(HttpServletRequest request) {

		return new ModelAndView("jsp/order/paidOrderList");
	}
	@RequestMapping("/toChangeOrderFirst")
	public ModelAndView toChangeOrderFirst(HttpServletRequest request) {

		return new ModelAndView("jsp/order/changeGoodsFirst");
	}
	@RequestMapping("/toChangeOrderSecond")
	public ModelAndView toChangeOrderSecond(HttpServletRequest request) {

		return new ModelAndView("jsp/order/changeGoodsSecond");
	}
	@RequestMapping("/toBackOrderFirst")
	public ModelAndView toBackOrderFirst(HttpServletRequest request) {

		return new ModelAndView("jsp/order/backGoodsFirst");
	}
	@RequestMapping("/toBackOrderSecond")
	public ModelAndView toBackOrderSecond(HttpServletRequest request) {

		return new ModelAndView("jsp/order/backGoodsSecond");
	}
	
	
	
	/**
     * 数据查询
     * @param request
     * @return
     */
    @RequestMapping("/getPaidOrderData")
    @ResponseBody
    public ResponseData<PageInfo<BehindParentOrdOrderVo>> getList(HttpServletRequest request,BehindQueryOrderLisReqVo reqVo){
    	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
        ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        BehindQueryOrderListRequest req = new BehindQueryOrderListRequest();
        ResponseData<PageInfo<BehindParentOrdOrderVo>> responseData = null;
        String states="21,212,22,23,31,92,93,94";
        String[] stateArray = states.split(",");
		List<String> stateList = new LinkedList<String>();
		for(String state : stateArray){
			stateList.add(state);
		}
		req.setStateList(stateList);
        String startT =  reqVo.getStartTime();
        String endT = reqVo.getEndTime();
	    if(!StringUtil.isBlank(startT)){
	    	startT = startT+" 00:00:00" ;
	    	req.setOrderTimeBegin(startT);
	    }
	    if(!StringUtil.isBlank(endT)){
	    	endT = endT+" 23:59:59";
	    	req.setOrderTimeEnd(endT);
	    }
        req.setChlId(reqVo.getChlId());
        req.setDeliveryFlag(reqVo.getDeliveryFlag());
        req.setContactTel(reqVo.getContactTel());
        req.setRouteId(reqVo.getRouteId());
        if(!StringUtil.isBlank(reqVo.getInputOrderId())){
        	 Long Id = Long.parseLong(reqVo.getInputOrderId());
        	 req.setOrderId(Id);
        }
        req.setTenantId(user.getTenantId());
        //获取用户ID
        if(!StringUtil.isBlank(reqVo.getUserName())){
        	String id = ChUserController.getUserId(reqVo.getUserName());
        	if(!StringUtil.isBlank(id)){
        		req.setUserId(id);
        	}else{
        		req.setUserId("-1");
        	}
        }
        String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
        try {
            req.setPageNo(Integer.parseInt(strPageNo));
            req.setPageSize(Integer.parseInt(strPageSize));
            BehindQueryOrderListResponse resultInfo = iOrderListSV.behindQueryOrderList(req);
            PageInfo<BehindParentOrdOrderVo> result= resultInfo.getPageInfo();
            List<BehindParentOrdOrderVo> list = result.getResult();
            if(!CollectionUtil.isEmpty(list)){
            	for(BehindParentOrdOrderVo vo:list){
            		vo.setUserTel(user.getMobile());
            		//翻译订单来源
					SysParamSingleCond	param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(vo.getChlId());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_CHL_ID);
            		SysParam chldParam = iCacheSV.getSysParamSingle(param);
            		if(chldParam!=null){
            			vo.setChlId(chldParam.getColumnDesc());
            		}
            	}
            }
            responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", result);
        } catch (Exception e) {
            responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }
	
	
	
	@RequestMapping("/changeDetail")
	public ModelAndView changeFirstDetail(HttpServletRequest request, String orderId,String flag) {
			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
	    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
	    	Map<String, OrderDetail> model = new HashMap<String, OrderDetail>();
	    	try {
				QueryOrderRequest queryRequest=new QueryOrderRequest();
				//queryRequest.setTenantId(user.getTenantId());
				queryRequest.setTenantId(Constants.TENANT_ID);
				queryRequest.setOrderId(Long.parseLong(orderId));
				OrderDetail orderDetail = new OrderDetail();
				List<OrdProdVo> prodList = new ArrayList<OrdProdVo>();
				IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
				QueryOrderResponse orderResponse = iOrderListSV.queryOrder(queryRequest);
				OrdOrderVo ordOrderVo=null;
				if(orderResponse!=null&&orderResponse.getResponseHeader().isSuccess()) {
					ordOrderVo = orderResponse.getOrdOrderVo();
					if(ordOrderVo!=null) {
						BeanUtils.copyProperties(orderDetail, ordOrderVo);
						orderDetail.setOrdTotalFee(AmountUtil.LiToYuan(ordOrderVo.getTotalFee()));
						//获取售后操作人
						ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
						SysUserQueryRequest  userReq = new SysUserQueryRequest ();
						userReq.setTenantId(user.getTenantId());
						userReq.setNo(orderDetail.getOperId());
						SysUserQueryResponse  response = iSysUserQuerySV.queryUserInfo(userReq);
						if(response!=null){
							orderDetail.setUsername(response.getName());
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
						List<OrdProductVo> productList = ordOrderVo.getProductList();
						if(!CollectionUtil.isEmpty(productList)) {
							for (OrdProductVo ordProductVo : productList) {
								OrdProdVo product = new OrdProdVo();
								//翻译金额
								product.setProdSalePrice(AmountUtil.LiToYuan(ordProductVo.getSalePrice()));
								product.setProdAdjustFee(AmountUtil.LiToYuan(ordProductVo.getAdjustFee()));
								//product.setImageUrl(ImageUtil.getImage(ordProductVo.getProductImage().getVfsId(), ordProductVo.getProductImage().getPicType()));
								product.setProdState(ordProductVo.getState());
								product.setProdName(ordProductVo.getProdName());
								product.setBuySum(ordProductVo.getBuySum());
								product.setProdCouponFee(AmountUtil.LiToYuan(ordProductVo.getCouponFee()));
								product.setJfFee(ordProductVo.getJfFee());
								product.setProdTotalFee(AmountUtil.LiToYuan(ordProductVo.getTotalFee()));
								prodList.add(product);
							}
						}
						orderDetail.setProdList(prodList);
					}
				}
			 model.put("order", orderDetail);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("订单详情查询报错：", e);
		}
    	if(!StringUtil.isBlank(flag)){
    		return new ModelAndView("jsp/order/changeGoodsSecond", model);
    	}else{
    		return new ModelAndView("jsp/order/changeGoodsFirst", model);
    	}
		
	}

	
	//第一次换货点击同意调用换货服务
	@RequestMapping("/firstChange")
	@ResponseBody
	public ResponseData<String> Change(HttpServletRequest request, String orderId,String refuseInfo,boolean isRefuse) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<String> responseData = null;
		BaseResponse base = null;
		OrderCheckRequest req = new OrderCheckRequest();
		try {
			IOrderCheckSV iOrderCheckSV = DubboConsumerFactory.getService(IOrderCheckSV.class);
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
			 req.setOrderId(Id);
			 req.setTenantId(user.getTenantId());
			//判断是拒绝还是同意换货
			if(isRefuse==false){
				//改变状态
				 req.setState("211");
				 base =  iOrderCheckSV.check(req);
			}else{
				 req.setState("212");
				 req.setRemark(refuseInfo);
				 base = iOrderCheckSV.check(req);
			}
			if(base.getResponseHeader().getIsSuccess()==true){
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "换货成功", null);
			}else{
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "换货失败", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("同意换货审核查询报错：", e);
		}
		return responseData;
	}
		
		
		@RequestMapping("/backDetail")
		public ModelAndView backFirstDetail(HttpServletRequest request, String orderId,String flag) {
			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
	    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
	    	Map<String, OrderDetail> model = new HashMap<String, OrderDetail>();
	    	try {
				QueryOrderRequest queryRequest=new QueryOrderRequest();
				queryRequest.setTenantId(user.getTenantId());
				queryRequest.setOrderId(Long.parseLong(orderId));
				OrderDetail orderDetail = new OrderDetail();
				List<OrdProdVo> prodList = new ArrayList<OrdProdVo>();
				IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
				QueryOrderResponse orderResponse = iOrderListSV.queryOrder(queryRequest);
				OrdOrderVo ordOrderVo=null;
				if(orderResponse!=null&&orderResponse.getResponseHeader().isSuccess()) {
					ordOrderVo = orderResponse.getOrdOrderVo();
					if(ordOrderVo!=null) {
						BeanUtils.copyProperties(orderDetail, ordOrderVo);
						if(ordOrderVo.getTotalFee()!=null){
							//总退款金额
							orderDetail.setOrdTotalFee(AmountUtil.LiToYuan(ordOrderVo.getTotalFee()));
						}
						//获取售后操作人
						ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
						SysUserQueryRequest  userReq = new SysUserQueryRequest ();
						userReq.setTenantId(user.getTenantId());
						userReq.setNo(orderDetail.getOperId());
						SysUserQueryResponse  response = iSysUserQuerySV.queryUserInfo(userReq);
						if(response!=null){
							orderDetail.setUsername(response.getName());
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
						List<OrdProductVo> productList = ordOrderVo.getProductList();
						if(!CollectionUtil.isEmpty(productList)) {
							for (OrdProductVo ordProductVo : productList) {
								OrdProdVo product = new OrdProdVo();
								//翻译金额
								product.setProdSalePrice(AmountUtil.LiToYuan(ordProductVo.getSalePrice()));
								product.setProdAdjustFee(AmountUtil.LiToYuan(ordProductVo.getAdjustFee()));
								//product.setImageUrl(ImageUtil.getImage(ordProductVo.getProductImage().getVfsId(), ordProductVo.getProductImage().getPicType()));
								product.setProdState(ordProductVo.getState());
								product.setProdName(ordProductVo.getProdName());
								product.setBuySum(ordProductVo.getBuySum());
								product.setProdCouponFee(AmountUtil.LiToYuan(ordProductVo.getCouponFee()));
								product.setJfFee(ordProductVo.getJfFee());
								prodList.add(product);
							}
						}
						orderDetail.setProdList(prodList);
					}
				}
				 model.put("order", orderDetail);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("订单详情查询报错：", e);
			}
	    	if(!StringUtil.isBlank(flag)){
	    		return new ModelAndView("jsp/order/backGoodsSecond", model);
	    	}else{
	    		return new ModelAndView("jsp/order/backGoodsFirst", model);
	    	}
			
		}
		
		
		//第一次换货点击同意调用换货服务
		@RequestMapping("/firstBack")
		@ResponseBody
		public ResponseData<String> Back(HttpServletRequest request, String orderId,String refuseInfo,boolean isRefuse) {
			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
			ResponseData<String> responseData = null;
			BaseResponse base = null;
			OrderCheckRequest req = new OrderCheckRequest();
			try {
				IOrderCheckSV iOrderCheckSV = DubboConsumerFactory.getService(IOrderCheckSV.class);
				ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
				req.setTenantId(Constants.TENANT_ID);
				req.setOrderId(Long.parseLong(orderId));
				SysUserQueryRequest  userReq = new SysUserQueryRequest ();
				userReq.setTenantId(user.getTenantId());
				userReq.setId(user.getUserId());
				SysUserQueryResponse  response = iSysUserQuerySV.queryUserInfo(userReq);
				if(response!=null){
					String no = response.getNo();
					req.setOperId(no);
				}
				 req.setTenantId(user.getTenantId());
				//判断是拒绝还是同意换货
				if(isRefuse==false){
					//改变状态
					 req.setState("211");
					 base =  iOrderCheckSV.check(req);
				}else{
					 req.setState("212");
					 req.setRemark(refuseInfo);
					 base = iOrderCheckSV.check(req);
				}
				
				if(base.getResponseHeader().getIsSuccess()==true){
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "同意退货成功", null);
				}else{
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "同意退货失败", null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("同意换货审核查询报错：", e);
			}
			return responseData;
		}
		
		@RequestMapping("/judge")
		public String paidQuery(HttpServletRequest request,String orderId,String skuId) {
			try {
				GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
				OrderJuageRequest req=new OrderJuageRequest(); 
				req.setOrderId(Long.parseLong(orderId));
				req.setSkuId(skuId);
				req.setTenantId(user.getTenantId());
				IOrderAfterSaleJudgeSV iOrderAfterSaleJudgeSV = DubboConsumerFactory.getService(IOrderAfterSaleJudgeSV.class);
				OrderJuageResponse response = iOrderAfterSaleJudgeSV.judge(req);
				if(response!=null&&response.getResponseHeader().isSuccess()) {
					OrderAfterVo orderAfterVo = response.getAfterVo();
					String busiCode = orderAfterVo.getBusiCode();
					if(Constants.OrdOrder.BusiCode.EXCHANGE_ORDER.equals(busiCode)) {
						return "redirect:/changeDetail?orderId="+orderAfterVo.getOrderId();
					}else if(Constants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(busiCode)) {
						return "redirect:/backDetail?orderId="+orderAfterVo.getOrderId();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("售后详情查询报错：", e);
			}
			return null;
		}
		
}
