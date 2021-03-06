package com.ai.ch.order.web.controller.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.BehindQueryOrderLisReqVo;
import com.ai.ch.order.web.model.order.OrdProdVo;
import com.ai.ch.order.web.model.order.OrderDetail;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.ch.order.web.utils.TranslateFiledsUtil;
import com.ai.ch.order.web.vo.Key;
import com.ai.ch.order.web.vo.KeyType;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
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
import com.ai.slp.order.api.ordermodify.interfaces.IOrderModifySV;
import com.ai.slp.order.api.ordermodify.param.OrdRequest;
import com.ai.slp.order.api.orderrefund.interfaces.IOrderRefundSV;
import com.ai.slp.order.api.orderrefund.param.OrderRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefuseRefundRequest;
import com.ai.slp.order.api.orderstate.interfaces.IOrderStateServiceSV;
import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumBackReq;
import com.ai.slp.product.api.storageserver.param.StorageNumUserReq;
import com.ai.slp.route.api.routemanage.interfaces.IRouteManageSV;
import com.ai.slp.route.api.routemanage.param.RouteIdParamRequest;
import com.ai.slp.route.api.routemanage.param.RouteResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.changhong.upp.business.entity.upp_599_001_01.RespInfo;
import com.changhong.upp.business.entity.upp_801_001_01.GrpBody;
import com.changhong.upp.business.entity.upp_801_001_01.GrpHdr;
import com.changhong.upp.business.entity.upp_801_001_01.ReqsInfo;
import com.changhong.upp.business.handler.BusinessHandler;
import com.changhong.upp.business.handler.factory.BusinessHandlerFactory;
import com.changhong.upp.business.type.TranType;

@Controller
public class PaidOrderController {
	private static final Logger LOG = LoggerFactory.getLogger(PaidOrderController.class);
	@Autowired
	private BusinessHandlerFactory businessHandlerFactory;
	@Resource(name = "key")
	private Key key;
	
	//跳转售后列表
	@RequestMapping("/toPaidOrder")
	public ModelAndView toPaidOrder(HttpServletRequest request) {

		return new ModelAndView("jsp/order/paidOrderList");
	}
	
	//跳转换货第一次审核页面
	@RequestMapping("/toChangeOrderFirst")
	public ModelAndView toChangeOrderFirst(HttpServletRequest request) {

		return new ModelAndView("jsp/order/changeGoodsFirst");
	}

	//跳转换货第二次审核页面
	@RequestMapping("/toChangeOrderSecond")
	public ModelAndView toChangeOrderSecond(HttpServletRequest request) {

		return new ModelAndView("jsp/order/changeGoodsSecond");
	}
	
	//跳转退货第一次审核页面
	@RequestMapping("/toBackOrderFirst")
	public ModelAndView toBackOrderFirst(HttpServletRequest request) {

		return new ModelAndView("jsp/order/backGoodsFirst");
	}
	//跳转退货第二次审核页面
	@RequestMapping("/toBackOrderSecond")
	public ModelAndView toBackOrderSecond(HttpServletRequest request) {

		return new ModelAndView("jsp/order/backGoodsSecond");
	}

	/**
	 * 数据查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPaidOrderData")
	@ResponseBody
	public ResponseData<PageInfo<BehindParentOrdOrderVo>> getList(HttpServletRequest request,
			BehindQueryOrderLisReqVo reqVo,String afterSaleState) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		
		IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
		BehindQueryOrderListRequest req = new BehindQueryOrderListRequest();
		ResponseData<PageInfo<BehindParentOrdOrderVo>> responseData = null;
		List<Object> stateList = new LinkedList<Object>();
		if(!StringUtil.isBlank(afterSaleState)) {
			stateList.add(afterSaleState);
		}else {
			String[] stateArray = Constants.OrdOrder.State.PAIED_STATES.split(",");
			for (String state : stateArray) {
				stateList.add(state);
			}
		}
		req.setStateList(stateList);
		if (!StringUtil.isBlank( reqVo.getStartTime())) {
			req.setOrderTimeBegin( reqVo.getStartTime()+" 00:00:00");
		}
		if (!StringUtil.isBlank(reqVo.getEndTime())) {
			req.setOrderTimeEnd(reqVo.getEndTime()+" 23:59:59");
		}
		req.setChlId(reqVo.getChlId());
		req.setDeliveryFlag(reqVo.getDeliveryFlag());
		req.setContactTel(reqVo.getContactTel());
		req.setRouteId(reqVo.getRouteId());
		if (!StringUtil.isBlank(reqVo.getInputOrderId())) {
			Long Id = Long.parseLong(reqVo.getInputOrderId());
			req.setOrderId(Id);
		}
		req.setTenantId(user.getTenantId());
		if(!StringUtil.isBlank(reqVo.getUserName())){
			req.setUserName(reqVo.getUserName());
	    }
		String strPageNo = (null == request.getParameter("pageNo")) ? "1" : request.getParameter("pageNo");
		String strPageSize = (null == request.getParameter("pageSize")) ? "10" : request.getParameter("pageSize");
		try {
			req.setPageNo(Integer.parseInt(strPageNo));
			req.setPageSize(Integer.parseInt(strPageSize));
			BehindQueryOrderListResponse resultInfo = iOrderListSV.behindQueryOrderList(req);
			if(resultInfo!=null && resultInfo.getResponseHeader().isSuccess()) {
				PageInfo<BehindParentOrdOrderVo> result = resultInfo.getPageInfo();
				List<BehindParentOrdOrderVo> vo = result.getResult();
				for (BehindParentOrdOrderVo behindParentOrdOrderVo : vo) {
					SysParam chldParam = TranslateFiledsUtil.translateInfo(Constants.TENANT_ID, 
							Constants.TYPE_CODE,Constants.ORD_CHL_ID,behindParentOrdOrderVo.getChlid(), iCacheSV);
					behindParentOrdOrderVo.setChlidname(chldParam == null ? "" : chldParam.getColumnDesc());
				}
				responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
						result);
			}
		} catch (Exception e) {
			responseData = new ResponseData<PageInfo<BehindParentOrdOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
			LOG.error("获取信息出错：", e);
		}
		return responseData;
	}
	
	//换货详情
	@RequestMapping("/changeDetail")
	public ModelAndView changeFirstDetail(HttpServletRequest request, String orderId, String flag,String sourceFlag) {
		request.setAttribute("sourceFlag", sourceFlag);
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		Map<String, OrderDetail> model = new HashMap<String, OrderDetail>();
		try {
			QueryOrderRequest queryRequest = new QueryOrderRequest();
			queryRequest.setTenantId(user.getTenantId());
			queryRequest.setOrderId(Long.parseLong(orderId));
			OrderDetail orderDetail = new OrderDetail();
			List<OrdProdVo> prodList = new ArrayList<OrdProdVo>();
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
			QueryOrderResponse orderResponse = iOrderListSV.queryOrder(queryRequest);
			OrdOrderVo ordOrderVo = null;
			if (orderResponse != null && orderResponse.getResponseHeader().isSuccess()) {
				ordOrderVo = orderResponse.getOrdOrderVo();
				if (ordOrderVo != null) {
					BeanUtils.copyProperties(orderDetail, ordOrderVo);
					// 总退款金额
					orderDetail.setOrdTotalFee(AmountUtil.LiToYuan(ordOrderVo.getTotalfee()));
					orderDetail.setOrdDiscountFee(AmountUtil.LiToYuan(ordOrderVo.getDiscountfee()));
					orderDetail.setOrdFreight(AmountUtil.LiToYuan(ordOrderVo.getFreight()));
					orderDetail.setOrdAdjustFee(AmountUtil.LiToYuan(ordOrderVo.getAdjustfee()));
					orderDetail.setUpdateFee(AmountUtil.LiToYuan(ordOrderVo.getPaidfee()));
					// 获取售后操作人
					ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
					SysUserQueryRequest userReq = new SysUserQueryRequest();
					userReq.setTenantId(user.getTenantId());
					userReq.setNo(orderDetail.getOperid());
					SysUserQueryResponse response = iSysUserQuerySV.queryUserInfo(userReq);
					if (response != null && response.getResponseHeader().isSuccess()) {
						orderDetail.setAfterSalesOperator(response.getName());
					}
					//翻译
					TranslateFiledsUtil.translateFileds(orderDetail, iCacheSV);
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
					
					List<OrdProductVo> productList = ordOrderVo.getProductList();
					if (!CollectionUtil.isEmpty(productList)) {
						for (OrdProductVo ordProductVo : productList) {
							OrdProdVo product = new OrdProdVo();
							// 翻译金额
							product.setProdSalePrice(AmountUtil.LiToYuan(ordProductVo.getSaleprice()));
							product.setProdAdjustFee(AmountUtil.LiToYuan(ordProductVo.getAdjustfee()));
							product.setImageUrl(ImageUtil.getImage(ordProductVo.getProductimage().getVfsId(),
									ordProductVo.getProductimage().getPicType()));
							product.setProdState(ordProductVo.getState());
							product.setProdName(ordProductVo.getProdname());
							product.setBuySum(ordProductVo.getBuysum());
							product.setProdCouponFee(AmountUtil.LiToYuan(ordProductVo.getCouponfee()));
							product.setJfFee(ordProductVo.getJffee());
							product.setAfterSaleImageUrl(ImageUtil.getImage(ordProductVo.getImageurl(),
									ordProductVo.getProdextendinfo()));   // 售后图片  
							product.setProdTotalFee(AmountUtil.LiToYuan(ordProductVo.getTotalfee()));
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
		if (!StringUtil.isBlank(flag)) {
			return new ModelAndView("jsp/order/changeGoodsSecond", model);
		} else {
			return new ModelAndView("jsp/order/changeGoodsFirst", model);
		}

	}

	// 第一次换货点击同意调用换货服务
	@RequestMapping("/firstChange")
	@ResponseBody
	public ResponseData<String> Change(HttpServletRequest request, String orderId, String refuseInfo,
			boolean isRefuse) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<String> responseData = null;
		BaseResponse base = null;
		OrderCheckRequest req = new OrderCheckRequest();
		try {
			IOrderCheckSV iOrderCheckSV = DubboConsumerFactory.getService(IOrderCheckSV.class);
			ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
			SysUserQueryRequest userReq = new SysUserQueryRequest();
			userReq.setTenantId(user.getTenantId());
			userReq.setId(user.getUserId());
			SysUserQueryResponse response = iSysUserQuerySV.queryUserInfo(userReq);
			if (response != null) {
				String no = response.getNo();
				req.setOperId(no);
			}
			Long Id = Long.parseLong(orderId);
			req.setOrderId(Id);
			req.setTenantId(user.getTenantId());
			// 判断是拒绝还是同意换货
			if (isRefuse == false) {
				// 改变状态
				req.setState("211");
				base = iOrderCheckSV.check(req);
			} else {
				req.setState("212");
				req.setRemark(refuseInfo);
				base = iOrderCheckSV.check(req);
			}
			if (base.getResponseHeader().getIsSuccess() == true) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "换货成功", null);
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "换货失败", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("同意换货审核查询报错：", e);
		}
		return responseData;
	}
	
	//退货详情
	@RequestMapping("/backDetail")
	public ModelAndView backFirstDetail(HttpServletRequest request, String orderId, String flag,String sourceFlag) {
		request.setAttribute("sourceFlag", sourceFlag);
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		Map<String, OrderDetail> model = new HashMap<String, OrderDetail>();
		try {
			QueryOrderRequest queryRequest = new QueryOrderRequest();
			queryRequest.setTenantId(user.getTenantId());
			queryRequest.setOrderId(Long.parseLong(orderId));
			OrderDetail orderDetail = new OrderDetail();
			List<OrdProdVo> prodList = new ArrayList<OrdProdVo>();
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
			QueryOrderResponse orderResponse = iOrderListSV.queryOrder(queryRequest);
			OrdOrderVo ordOrderVo = null;
			if (orderResponse != null && orderResponse.getResponseHeader().isSuccess()) {
				ordOrderVo = orderResponse.getOrdOrderVo();
				if (ordOrderVo != null) {
					BeanUtils.copyProperties(orderDetail, ordOrderVo);
					// 总退款金额
					orderDetail.setOrdTotalFee(AmountUtil.LiToYuan(ordOrderVo.getTotalfee()));
					orderDetail.setOrdDiscountFee(AmountUtil.LiToYuan(ordOrderVo.getDiscountfee()));
					orderDetail.setOrdFreight(AmountUtil.LiToYuan(ordOrderVo.getFreight()));
					orderDetail.setOrdAdjustFee(AmountUtil.LiToYuan(ordOrderVo.getAdjustfee()));
					orderDetail.setUpdateFee(AmountUtil.LiToYuan(ordOrderVo.getPaidfee()));
					// 获取售后操作人
					ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
					SysUserQueryRequest userReq = new SysUserQueryRequest();
					userReq.setTenantId(user.getTenantId());
					userReq.setNo(orderDetail.getOperid());
					SysUserQueryResponse response = iSysUserQuerySV.queryUserInfo(userReq);
					if (response != null &&response.getResponseHeader().isSuccess()) {
						orderDetail.setAfterSalesOperator(response.getName());
					}
					//翻译
					TranslateFiledsUtil.translateFileds(orderDetail, iCacheSV);
					
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
					
					List<OrdProductVo> productList = ordOrderVo.getProductList();
					if (!CollectionUtil.isEmpty(productList)) {
						for (OrdProductVo ordProductVo : productList) {
							OrdProdVo product = new OrdProdVo();
							// 翻译金额
							product.setProdSalePrice(AmountUtil.LiToYuan(ordProductVo.getSaleprice()));
							product.setProdAdjustFee(AmountUtil.LiToYuan(ordProductVo.getAdjustfee()));
							product.setImageUrl(ImageUtil.getImage(ordProductVo.getProductimage().getVfsId(),
									ordProductVo.getProductimage().getPicType()));
							product.setProdState(ordProductVo.getState());
							product.setProdName(ordProductVo.getProdname());
							product.setBuySum(ordProductVo.getBuysum());
							product.setProdCouponFee(AmountUtil.LiToYuan(ordProductVo.getCouponfee()));
							product.setJfFee(ordProductVo.getJffee());
							product.setAfterSaleImageUrl(ImageUtil.getImage(ordProductVo.getImageurl(),
									ordProductVo.getProdextendinfo()));   // 售后图片  
							product.setGiveJF(ordProductVo.getGivejf());
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
		if (!StringUtil.isBlank(flag)) {
			return new ModelAndView("jsp/order/backGoodsSecond", model);
		} else {
			return new ModelAndView("jsp/order/backGoodsFirst", model);
		}

	}

	// 第一次换货点击同意调用换货服务
	@RequestMapping("/firstBack")
	@ResponseBody
	public ResponseData<String> Back(HttpServletRequest request, String orderId, String refuseInfo, boolean isRefuse) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<String> responseData = null;
		BaseResponse base = null;
		OrderCheckRequest req = new OrderCheckRequest();
		try {
			IOrderCheckSV iOrderCheckSV = DubboConsumerFactory.getService(IOrderCheckSV.class);
			ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
			req.setOrderId(Long.parseLong(orderId));
			SysUserQueryRequest userReq = new SysUserQueryRequest();
			userReq.setTenantId(user.getTenantId());
			userReq.setId(user.getUserId());
			SysUserQueryResponse response = iSysUserQuerySV.queryUserInfo(userReq);
			if (response != null) {
				String no = response.getNo();
				req.setOperId(no);
			}
			req.setTenantId(user.getTenantId());
			// 判断是拒绝还是同意换货
			if (isRefuse == false) {
				// 改变状态
				req.setState("211");
				base = iOrderCheckSV.check(req);
			} else {
				req.setState("212");
				req.setRemark(refuseInfo);
				base = iOrderCheckSV.check(req);
			}

			if (base.getResponseHeader().getIsSuccess() == true) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "同意退货成功", null);
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "同意退货失败", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("同意换货审核查询报错：", e);
		}
		return responseData;
	}
	
	//判断该商品对应的订单所属业务类型
	@RequestMapping("/judge")
	public String paidQuery(HttpServletRequest request, String orderId, String skuId,String sourceFlag) {
		try {
			 request.setAttribute("sourceFlag", sourceFlag);
			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
					.getAttribute(SSOClientConstants.USER_SESSION_KEY);
			OrderJuageRequest req = new OrderJuageRequest();
			req.setOrderId(Long.parseLong(orderId));
			req.setSkuId(skuId);
			req.setTenantId(user.getTenantId());
			IOrderAfterSaleJudgeSV iOrderAfterSaleJudgeSV = DubboConsumerFactory
					.getService(IOrderAfterSaleJudgeSV.class);
			OrderJuageResponse response = iOrderAfterSaleJudgeSV.judge(req);
			if (response != null && response.getResponseHeader().isSuccess()) {
				OrderAfterVo orderAfterVo = response.getAfterVo();
				String busiCode = orderAfterVo.getBusiCode();
				String state = orderAfterVo.getState();
				long afterOrderId = orderAfterVo.getOrderId();
				String flag = "1";
				// busiCode=2
				if (Constants.OrdOrder.BusiCode.EXCHANGE_ORDER.equals(busiCode)
						&& (Constants.OrdOrder.State.WAIT_CHECK.equals(state)
								|| Constants.OrdOrder.State.NO_CHECK.equals(state))) {
					return "redirect:/changeDetail?orderId=" + afterOrderId+"&sourceFlag="+"00";
				} else if (Constants.OrdOrder.BusiCode.EXCHANGE_ORDER.equals(busiCode)
						&& (Constants.OrdOrder.State.WAIT_BACK.equals(state)
								|| Constants.OrdOrder.State.WAIT_GET_GOODS.equals(state))) {
					// 判断跳转的页面是第2次审核
					return "redirect:/changeDetail?orderId=" + afterOrderId + "&flag=" + flag+"&sourceFlag="+"00";
					// busiCode=3
				} else if (Constants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(busiCode)
						&& (Constants.OrdOrder.State.WAIT_CHECK.equals(state)
								|| Constants.OrdOrder.State.NO_CHECK.equals(state))) {
					return "redirect:/backDetail?orderId=" + afterOrderId+"&sourceFlag="+"00";
				} else if (Constants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(busiCode)
						&& (Constants.OrdOrder.State.WAIT_BACK.equals(state)
								|| Constants.OrdOrder.State.WAIT_GET_GOODS.equals(state)
								|| Constants.OrdOrder.State.WAIT_BACK_FEE.equals(state)
								|| Constants.OrdOrder.State.NO_AGAIN_CHECK.equals(state))) {
					// 调到第二个审核页面页面
					return "redirect:/backDetail?orderId=" + afterOrderId + "&flag=" + flag+"&sourceFlag="+"00";
					// busiCode=4
				} else if (Constants.OrdOrder.BusiCode.CANCEL_ORDER.equals(busiCode)
						&& (Constants.OrdOrder.State.WAIT_BACK_FEE.equals(state))) {
					// 调到第二个审核页面页面
					return "redirect:/backDetail?orderId=" + afterOrderId + "&flag=" + flag+"&sourceFlag="+"00";
				} else if (Constants.OrdOrder.State.REFUND_FAILD.equals(state)
						|| Constants.OrdOrder.State.REFUND_ING.equals(state)) {
					// 如果为退费失败那么直接掉到退货审核的第二个页面重新发起退款申请
					// 调到第二个审核页面页面
					return "redirect:/backDetail?orderId=" + afterOrderId + "&flag=" + flag+"&sourceFlag="+"00";
				} else {
					return "redirect:/order/orderListDetail?orderId=" + afterOrderId + "&state=" + state+"&sourceFlag="+"00";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("售后详情查询报错：", e);
		}
		return null;
	}

	/**
	 * 同意退货
	 * 
	 * @param request
	 * @param orderId
	 * @param accountId
	 * @param openId
	 * @param appId
	 * @param oid
	 * @param bisId
	 * @param backCash
	 * @return
	 * @author zhouxh
	 */
	@RequestMapping("/refund")
	@ResponseBody
	public ResponseData<String> refund(HttpServletRequest request, String updateInfo, String updateMoney,
			String orderId, String accountId, String openId, String downOrdId, String giveJF, String saleJF,
			String banlanceIfId, String parentOrderId,String token) {
		ResponseData<String> responseData = null;
		if (!StringUtil.isBlank(downOrdId)) {
			// 查询用户积分 判断是否允许退货
			String appId = "30a10e21";
			// TODO
			String bisId = orderId;//订单id
			int surplusCash = integralCashQry(accountId, openId, appId,token);
			int giveCash = 0;
			if (!StringUtil.isBlank(giveJF)) {
				try {
					giveCash = Integer.parseInt(giveJF);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (surplusCash >= giveCash) {// 当前用户积分余额大于商品赠送积分
				// 用户消费积分撤销
				ResponseData<String> shopbackData = shopback(accountId, openId, appId, downOrdId, bisId, saleJF,token);
				
				//0元退款
				if(0==Double.parseDouble(updateMoney)) {
					if("1".equals(shopbackData.getStatusCode())) {
						// 修改退款金额
						LOG.info("修改金额服务>>>>>>,修改金额:"+updateMoney);
						boolean flag = updateOrderMoney(request, orderId, updateInfo, updateMoney);
						if (!flag) {
							responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "修改金额失败", null);
							return responseData;
						}
						BaseResponse base = this.modifyOrd(orderId);
						if(base.getResponseHeader().getIsSuccess()) {
							//退款成功
							LOG.info("退款修改订单服务>>>>>>"+base.getResponseHeader().getResultMessage());
							responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款成功", null);
						}else {
							responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款失败", "9999");
						}
					}else {
						responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款失败", "9999");
					}
				}else { 
					//大于0的情况下
					// 退款
					ResponseData<String> resposne = agreedRefund(request, orderId, updateInfo, parentOrderId, updateMoney,
							banlanceIfId);
					if ("1".equals(resposne.getStatusCode())&&"1".equals(shopbackData.getStatusCode())) {
						// 修改退款金额
						boolean flag = updateOrderMoney(request, orderId, updateInfo, updateMoney);
						if (!flag) {
							responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "修改金额失败", null);
							return responseData;
						}
						responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款申请成功", null);
					} else {
						responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款申请失败", "9999");
					}
				}
				
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "当前用户积分余额小于商品赠送积分", null);
			}
		} else {	
			//0元退款
			if(0==Double.parseDouble(updateMoney)) {
				// 修改退款金额
				boolean flag = updateOrderMoney(request, orderId, updateInfo, updateMoney);
				if (!flag) {
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "修改金额失败", null);
					return responseData;
				}
				BaseResponse base = this.modifyOrd(orderId);
				if(base.getResponseHeader().getIsSuccess()) {
					//退款成功
					LOG.info("退款修改订单服务>>>>>>"+base.getResponseHeader().getResultMessage());
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款成功", null);
				}else {
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款失败", "9999");
				}
			}else {
				// 退款
				ResponseData<String> resposne = agreedRefund(request, orderId, updateInfo, parentOrderId, updateMoney,
						banlanceIfId);
				if ("1".equals(resposne.getStatusCode())) {
					// 修改退款金额
					boolean flag = updateOrderMoney(request, orderId, updateInfo, updateMoney);
					if (!flag) {
						responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "修改金额失败", null);
						return responseData;
					}
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款申请成功", null);
				} else {
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "退款申请失败", "9999");
				}
			}
		}
		return responseData;
	}

	/**
	 * 查询用户积分
	 * 
	 * @param accountId
	 * @param openId
	 * @param appId
	 * @return cash
	 */
	private int integralCashQry(String accountId, String openId, String appId,String token) {
		LOG.info("用户积分查询开始>>>>");
		Map<String, String> params = new HashMap<String, String>();
		params.put("accountId", accountId);
		params.put("openId", openId);
		params.put("appId", appId);
		params.put("token", token);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appkey", Constants.INTEGRAL_SEARCH_APPKEY);
		String param = JSON.toJSONString(params);
		LOG.info("用户积分查询参数>>>>" + param);
		try {
			String result = HttpClientUtil.sendPost(Constants.INTEGRAL_SEARCH_URL, param, headers);
			JSONObject jsonObject = ParseO2pDataUtil.getData(result);
			String dataStr =jsonObject.getString("code");
			if ("200".equals(dataStr)) {
				return Integer.parseInt(jsonObject.getString("cash"));
			} else {
				LOG.error("查询用户积分请求失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 用户消费积分撤销
	 * 
	 * @param accountId
	 * @param openId
	 * @param appId
	 * @param oid
	 * @param bisId
	 * @param backCash
	 * @return ResponseData<String>
	 * @author zhouxh
	 */
	private ResponseData<String> shopback(String accountId, String openId, String appId, String oid, String bisId,
			String backCash,String token) {
		LOG.info("用户积分撤销开始>>>>");
		ResponseData<String> responseData = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("accountId", accountId);
		params.put("openId", openId);
		params.put("appId", appId);
		params.put("oid", oid);
		params.put("bisId", bisId);
		params.put("token", token);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appkey", Constants.INTEGRAL_SHOPBACK_APPKEY);
		String param = JSON.toJSONString(params);
		try {
			LOG.info("撤销积分参数>>>>" + param);
			String result = HttpClientUtil.sendPost(Constants.INTEGRAL_SHOPBACK_URL, param, headers);
			JSONObject jsonObject = ParseO2pDataUtil.getData(result);
			String dataStr =jsonObject.getString("code");
			if ("200".equals(dataStr)) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "用户消费积分撤销成功", null);
			} else {
				LOG.info(">>>>>>>撤销积分请求过程失败");
				// 请求过程失败
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "用户消费积分撤销失败", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseData;
	}

	// 拒绝退款
	@RequestMapping("/refuseRefund")
	@ResponseBody
	public ResponseData<String> Back(HttpServletRequest request, String orderId, String info) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<String> responseData = null;
		OrderRefuseRefundRequest query = new OrderRefuseRefundRequest();
		try {
			IOrderRefundSV iOrderRefundSV = DubboConsumerFactory.getService(IOrderRefundSV.class);
			Long Id = Long.parseLong(orderId);
			query.setOrderId(Id);
			query.setTenantId(user.getTenantId());
			query.setRefuseReason(info);
			ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
			SysUserQueryRequest userReq = new SysUserQueryRequest();
			userReq.setTenantId(user.getTenantId());
			userReq.setId(user.getUserId());
			SysUserQueryResponse response = iSysUserQuerySV.queryUserInfo(userReq);
			if (response != null) {
				String no = response.getNo();
				query.setOperId(no);
			}
			BaseResponse base = iOrderRefundSV.refuseRefund(query);
			if (base.getResponseHeader().getIsSuccess() == true) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "拒绝退款成功", null);
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "拒绝退款失败", null);
			}
		} catch (Exception e) {
			LOG.error("拒绝退款报错：", e);
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "拒绝退款失败", null);
		}
		return responseData;
	}

	/**
	 * 退款金额修改
	 * 
	 * @param request
	 * @param orderId
	 * @param info
	 * @param updateMoney
	 * @return
	 * @author zhouxh
	 */
	private boolean updateOrderMoney(HttpServletRequest request, String orderId, String info, String updateMoney) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		OrderRefundRequest query = new OrderRefundRequest();
		try {
			IOrderRefundSV iOrderRefundSV = DubboConsumerFactory.getService(IOrderRefundSV.class);
			Long Id = Long.parseLong(orderId);
			query.setOrderId(Id);
			query.setTenantId(user.getTenantId());
			query.setUpdateReason(info);
			// 转换金额单位
			Long money = AmountUtil.YToLi(updateMoney);
			query.setUpdateMoney(money);
			ISysUserQuerySV iSysUserQuerySV = DubboConsumerFactory.getService(ISysUserQuerySV.class);
			SysUserQueryRequest userReq = new SysUserQueryRequest();
			userReq.setTenantId(user.getTenantId());
			userReq.setId(user.getUserId());
			SysUserQueryResponse response = iSysUserQuerySV.queryUserInfo(userReq);
			if (response != null) {
				String no = response.getNo();
				query.setOperId(no);
			}
			BaseResponse base = iOrderRefundSV.partRefund(query);
			if (base.getResponseHeader().getIsSuccess() == true) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("修改金额报错：", e);
		}
		return true;
	}

	// 同意退款
	public ResponseData<String> agreedRefund(HttpServletRequest request, String orderId, String updateInfo,
			String parentOrderId, String money, String banlanceIfId) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<String> responseData = null;
		try {
			LOG.info("退款申请开始>>>>>>");
			// 将元转换为分
			String updateMoney = AmountUtil.YToSFen(money);
			GrpHdr hdr = new GrpHdr();
			hdr.setMerNo(Constants.ch_pay_first_merchant);
			hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			hdr.setTranType(TranType.REFUND_APPLY.getValue());
			GrpBody body = new GrpBody();
			body.setPayTranSn(banlanceIfId);
			body.setMerSeqId(parentOrderId);
			body.setRefundAmt(updateMoney);
			body.setMerRefundSn(orderId);
			body.setSonMerNo(Constants.ch_pay_two_merchant);
			body.setRefundDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			body.setNotifyUrl(Constants.CH_REFUND_URL);
			body.setResv(orderId);
			ReqsInfo reqInfo = new ReqsInfo();
			reqInfo.setGrpHdr(hdr);
			reqInfo.setGrpBody(body);
			LOG.info("发起参数流水号>>>>"+reqInfo.getGrpBody().getPayTranSn());
			LOG.info("发起参数主订单号>>>>"+reqInfo.getGrpBody().getMerRefundSn());
			LOG.info("发起参数子订单号>>>>"+reqInfo.getGrpBody().getMerSeqId());
			LOG.info("发起参数金额>>>>"+reqInfo.getGrpBody().getRefundAmt());
			LOG.info("退款时间>>>>"+reqInfo.getGrpBody().getRefundDate());
			LOG.info("退款地址url>>>>"+Constants.CH_PAY_URL);
			BusinessHandler handler = businessHandlerFactory.getInstance(TranType.REFUND_APPLY);
			RespInfo rp = (RespInfo) handler.process(Constants.CH_PAY_URL, reqInfo, key.getKey(KeyType.PRIVATE_KEY),
					key.getKey(KeyType.PUBLIC_KEY));
			if (!"90000".equals(rp.getGrpBody().getStsRsn().getRespCode())) {
				LOG.info("退款申请>>>>>>>>>>"+rp.getGrpBody().getStsRsn().getRespDesc());
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "申请退款失败", null);
			} else {
				LOG.info("退款申请成功>>>>>>");
				//申请失败修改订单状态为处理中
				IOrderModifySV iOrderModifySV = DubboConsumerFactory.getService(IOrderModifySV.class);
				OrdRequest ordRequest = new OrdRequest();
				ordRequest.setOrderId(Long.parseLong(orderId));
				ordRequest.setTenantId(user.getTenantId());
				ordRequest.setState(Constants.OrdOrder.State.REFUND_ING);
				iOrderModifySV.modify(ordRequest);
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "申请退款成功", null);
			}
		} catch (Exception e) {
			LOG.info("退款出现异常>>>>>>"+e.getMessage());
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "申请退款失败", null);
		}
		return responseData;
	}

	// 收到换货
	@RequestMapping("/confirmChange")
	@ResponseBody
	public ResponseData<String> confirmChange(HttpServletRequest request,String orderId) {
		ResponseData<String> responseData = null;
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		try {
			// 调用确认换货服务
			IOrderStateServiceSV iOrderStateServiceSV = DubboConsumerFactory.getService(IOrderStateServiceSV.class);
			WaitRebateRequest req = new WaitRebateRequest();
			req.setOrderId(Long.valueOf(orderId));
			req.setTenantId(user.getTenantId());
			WaitRebateResponse response = iOrderStateServiceSV.updateWaitRebateState(req);
			if (response.getResponseHeader().getIsSuccess() == true) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "收到换货成功", null);
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "收到换货失败", null);
				return responseData;
			}
		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "收到换货失败", null);
		}
		return responseData;
	}
		// 收到退货
		@RequestMapping("/confirmBack")
		@ResponseBody
		public ResponseData<String> confirmBack(HttpServletRequest request, String expressOddNumber, String expressId,
				String orderId) {
			ResponseData<String> responseData = null;
			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
					.getAttribute(SSOClientConstants.USER_SESSION_KEY);
			try {
				// 调用确认退货服务
				IOrderStateServiceSV iOrderStateServiceSV = DubboConsumerFactory.getService(IOrderStateServiceSV.class);
				WaitRebateRequest req = new WaitRebateRequest();
				req.setOrderId(Long.valueOf(orderId));
				req.setTenantId(user.getTenantId());
				WaitRebateResponse response = iOrderStateServiceSV.updateWaitRebateState(req);
				if (response.getResponseHeader().getIsSuccess() == true) {
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "收到退货成功", null);
				} else {
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "收到退货失败", null);
					return responseData;
				}
				// 获取商品信息
				QueryOrderRequest queryRequest = new QueryOrderRequest();
				queryRequest.setTenantId(Constants.TENANT_ID);
				queryRequest.setOrderId(Long.parseLong(orderId));
				IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
				QueryOrderResponse orderResponse = iOrderListSV.queryOrder(queryRequest);
				if (orderResponse != null && orderResponse.getResponseHeader().isSuccess()) {
					OrdOrderVo order = orderResponse.getOrdOrderVo();
					if (order != null) {
						// 获取商品信息
						List<OrdProductVo> prodList = order.getProductList();
						if (!CollectionUtil.isEmpty(prodList)) {
							// 减少商品销售量
							IStorageNumSV iStorageNumSV = DubboConsumerFactory.getService(IStorageNumSV.class);
							for (OrdProductVo prod : prodList) {
								StorageNumUserReq storageReq = new StorageNumUserReq();
								storageReq.setSkuId(prod.getSkuid());
								storageReq.setSkuNum(prod.getBuysum().intValue());
								storageReq.setTenantId(user.getTenantId());
								BaseResponse baseResponse = iStorageNumSV.backSaleNumOfProduct(storageReq);
								//增加库存量
								StorageNumBackReq backReq = new StorageNumBackReq();
								backReq.setSkuId(prod.getSkuid());
								 Map<String, Integer> storageNum = JSON.parseObject(prod.getSkustorageid(),
						                    new com.alibaba.fastjson.TypeReference<Map<String, Integer>>(){});
								backReq.setTenantId(user.getTenantId());
								backReq.setStorageNum(storageNum);
								BaseResponse backResponse = iStorageNumSV.backStorageNum(backReq);
								if (baseResponse.getResponseHeader().getIsSuccess() == false) {
									responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "减少商品销量失败",
											null);
									return responseData;
								}
								if (backResponse.getResponseHeader().getIsSuccess() == false) {
									responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "增加库存失败",
											null);
									return responseData;
								}
								
							}
						}
					}
				} else {
					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "收到退货失败", null);
					return responseData;
				}
			} catch (Exception e) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "收到退货失败", null);
			}
			return responseData;
		}
		
	/**
	 * 修改订单状态
	 */
	private BaseResponse modifyOrd(String orderId) {
		IOrderModifySV iOrderModifySV = DubboConsumerFactory.getService(IOrderModifySV.class);
		//修改售后订单为退款完成
		OrdRequest req = new OrdRequest();
		req.setTenantId(Constants.TENANT_ID);
		req.setOrderId(Long.parseLong(orderId));
		req.setState(Constants.OrdOrder.State.REFUND_COMPLETE);
		LOG.info("修改售后订单为退款完成>>>>>>,修改订单id:"+orderId);
		return iOrderModifySV.modify(req);
	}
}
