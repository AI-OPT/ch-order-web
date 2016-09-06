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

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.ProductVo;
import com.ai.ch.order.web.model.order.OrdOrderListVo;
import com.ai.ch.order.web.model.order.OrdProdVo;
import com.ai.ch.order.web.model.order.OrderDetail;
import com.ai.ch.order.web.model.order.OrderListQueryParams;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.net.xss.util.CollectionUtil;
import com.ai.net.xss.util.StringUtil;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.BehindOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindParentOrdOrderVo;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.OrderPayVo;
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
    public ResponseData<PageInfo<OrdOrderListVo>> getList(HttpServletRequest request,OrderListQueryParams queryParams){
    	ResponseData<PageInfo<OrdOrderListVo>> responseData = null;
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
			queryRequest.setTenantId(Constants.TENANT_ID);
			//用户信息  根据username获取用户id??
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
						orderListVo.setTotalAdjustFee(AmountUtil.LiToYuan(behindParentOrdOrderVo.getAdjustFee()));
						orderListVo.setTotalDiscountFee(AmountUtil.LiToYuan(behindParentOrdOrderVo.getDiscountFee()));
						orderListVo.setTotalJF(AmountUtil.LiToYuan(behindParentOrdOrderVo.getPoints()));
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
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<OrdOrderListVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
		}
	    return responseData;
    }


    @RequestMapping("/orderListDetail")
	public ModelAndView orderListDetail(HttpServletRequest request, String orderId,String state) {
    	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
    	Map<String, OrdOrderVo> model = new HashMap<String, OrdOrderVo>();
    	try {
			QueryOrderRequest queryRequest=new QueryOrderRequest();
			queryRequest.setOrderId(Long.parseLong(orderId));
			//queryRequest.setTenantId(user.getTenantId());
			queryRequest.setTenantId(Constants.TENANT_ID);
			OrderDetail orderDetail = new OrderDetail();
			List<OrdProdVo> prodList = new ArrayList<OrdProdVo>();
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
			QueryOrderResponse orderResponse = iOrderListSV.queryOrder(queryRequest);
			OrdOrderVo ordOrderVo=null;
			if(orderResponse!=null&&orderResponse.getResponseHeader().isSuccess()) {
				ordOrderVo = orderResponse.getOrdOrderVo();
				if(ordOrderVo!=null) {
					BeanUtils.copyProperties(orderDetail, ordOrderVo);
					List<OrdProductVo> productList = ordOrderVo.getProductList();
					if(!CollectionUtil.isEmpty(productList)) {
						for (OrdProductVo ordProductVo : productList) {
							OrdProdVo product = new OrdProdVo();
							//翻译金额
							product.setProdSalePrice(AmountUtil.LiToYuan(ordProductVo.getSalePrice()));
							product.setProdAdjustFee(AmountUtil.LiToYuan(ordProductVo.getAdjustFee()));
							product.setImageUrl(ImageUtil.getImage(ordProductVo.getProductImage().getVfsId(), ordProductVo.getProductImage().getPicType()));
							product.setProdState(ordProductVo.getState());
							product.setProdName(ordProductVo.getProdName());
							product.setBuySum(ordProductVo.getBuySum());
							product.setProdCouponFee(AmountUtil.LiToYuan(ordProductVo.getCouponFee()));
							product.setJfFee(ordProductVo.getJfFee());
							product.setProdDetalId(ordProductVo.getProdDetalId());
							prodList.add(product);
						}
					}
					orderDetail.setProdList(prodList);
				}
			}
			model.put("orderDetail", orderDetail);
			if(Constants.OrdOrder.State.WAIT_PAY.equals(state)) { //待付款
				return new ModelAndView("", model);
			}
			if(Constants.OrdOrder.State.WAIT_DISTRIBUTION.equals(state)) { //已付款(待配货)
				return new ModelAndView("jsp/order/paidOrderDetails", model);
			}
			if(Constants.OrdOrder.State.WAIT_DELIVERY.equals(state)||
					Constants.OrdOrder.State.WAIT_SEND.equals(state)) { //待发货
				return new ModelAndView("jsp/order/waitInvoiceDetails", model);
			}
			if(Constants.OrdOrder.State.WAIT_CONFIRM.equals(state)) { //已发货
				return new ModelAndView("", model);
			}
			if(Constants.OrdOrder.State.COMPLETED.equals(state)) { //已完成
				return new ModelAndView("", model);
			}
			if(Constants.OrdOrder.State.CANCEL.equals(state)) { //已关闭
				return new ModelAndView("", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单详情查询报错：", e);
		}
		return null;
	}
}
