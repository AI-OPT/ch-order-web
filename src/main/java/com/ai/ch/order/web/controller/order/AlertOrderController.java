package com.ai.ch.order.web.controller.order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.OrderWarmDetail;
import com.ai.ch.order.web.model.ProductVo;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.ch.order.web.utils.TranslateFiledsUtil;
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
import com.ai.slp.order.api.ordercancel.interfaces.IOrderCancelSV;
import com.ai.slp.order.api.ordercancel.param.OrderCancelRequest;
import com.ai.slp.order.api.warmorder.interfaces.IOrderWarmSV;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailResponse;
import com.ai.slp.order.api.warmorder.param.OrderWarmListVo;
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmResponse;
import com.ai.slp.order.api.warmorder.param.OrderWarmVo;
import com.ai.slp.order.api.warmorder.param.ProductInfo;

@Controller
public class AlertOrderController {
	private static final Logger LOG = Logger.getLogger(AlertOrderController.class);
	@RequestMapping("/toAlertOrder")
	public ModelAndView toAlertOrder(HttpServletRequest request) {
		
		return new ModelAndView("jsp/order/alertOrder");
	}
	
	/**
     * 数据查询
     * @param request
     * @return
     */
    @RequestMapping("/getAlertOrderData")
    @ResponseBody
    public ResponseData<PageInfo<OrderWarmListVo>> getList(HttpServletRequest request,String orderTimeBegin,String orderTimeEnd){
    	IOrderWarmSV iOrderWarmSV = DubboConsumerFactory.getService(IOrderWarmSV.class);
    	OrderWarmRequest req = new OrderWarmRequest();
    	ResponseData<PageInfo<OrderWarmListVo>> responseData = null;
        if (!StringUtil.isBlank(orderTimeBegin)) {
        	orderTimeBegin = orderTimeBegin + " 00:00:00";
			Timestamp ts  = Timestamp.valueOf(orderTimeBegin);
			req.setOrderTimeStart(ts);
		}
        if (!StringUtil.isBlank(orderTimeEnd)) {
        	orderTimeEnd = orderTimeEnd + " 23:59:59";
			Timestamp ts  = Timestamp.valueOf(orderTimeEnd);
			req.setOrderTimeEnd(ts);
		}
        String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"5":request.getParameter("pageSize");
        try {
            req.setPageNo(Integer.parseInt(strPageNo));
            req.setPageSize(Integer.parseInt(strPageSize));
            req.setTenantId(Constants.TENANT_ID);
            OrderWarmResponse resp = iOrderWarmSV.serchWarmOrder(req);
            if(resp!=null && resp.getResponseHeader().isSuccess()) {
            	PageInfo<OrderWarmListVo> result= resp.getPageInfo();
            	responseData = new ResponseData<PageInfo<OrderWarmListVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", result);
            }else {
            	responseData = new ResponseData<PageInfo<OrderWarmListVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            }
        } catch (Exception e) {
        	responseData = new ResponseData<PageInfo<OrderWarmListVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }
    
    //预警订单详情查询
    @RequestMapping("/alertDetail")
	public ModelAndView alertDetail(HttpServletRequest request, String orderId) {
    	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
    	Map<String, OrderWarmVo> model = new HashMap<String, OrderWarmVo>();
		IOrderWarmSV iOrderWarmSV = DubboConsumerFactory.getService(IOrderWarmSV.class);
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		OrderWarmDetailRequest  query =new OrderWarmDetailRequest ();
		OrderWarmDetail orderDetail = new OrderWarmDetail();
		List<ProductVo> productList = new ArrayList<ProductVo>();
		try {
			Long Id = Long.parseLong(orderId);
			query.setOrderId(Id);
			query.setTenantId(user.getTenantId());
			OrderWarmDetailResponse response = iOrderWarmSV.searchWarmorderDetail(query);
			if(response!=null && response.getResponseHeader().isSuccess()){
				OrderWarmVo  orderWarm =  response.getOrderWarmVo();
				if(orderWarm!=null){
					BeanUtils.copyProperties(orderDetail, orderWarm);
					orderDetail.setUserTel(user.getMobile());
					//翻译
					this.translateFileds(orderDetail, iCacheSV);
					//
					List<ProductInfo> proList = orderWarm.getProdInfo();
					if (!CollectionUtil.isEmpty(proList)) {
						for (ProductInfo vo:proList) {
							ProductVo product = new ProductVo();
							//翻译金额
							product.setProdDiscountFee(AmountUtil.LiToYuan(vo.getDiscountFee()));
							product.setProdSalePrice(AmountUtil.LiToYuan(vo.getSalePrice()));
							product.setProdAdjustFee(AmountUtil.LiToYuan(vo.getAdjustFee()));
							product.setImageUrl(ImageUtil.getImage(vo.getProductImage().getVfsId(), vo.getProductImage().getPicType()));
							product.setProdName(vo.getProdName());
							product.setBuySum(vo.getBuySum());
							product.setJf(vo.getJf());
							productList.add(product);
						}
					}
					orderDetail.setProductList(productList);
				}
			}
			 model.put("order", orderDetail);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("预警订单详情查询报错：", e);
		}
		return new ModelAndView("jsp/order/alertOrderDetail", model);
	}
    	//关闭订单服务
  		@RequestMapping("/closeOrder")
  		@ResponseBody
  		public ResponseData<String> Back(HttpServletRequest request, String orderId) {
  			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
  			ResponseData<String> responseData = null;
  			OrderCancelRequest query = new OrderCancelRequest();
  			try {
  				IOrderCancelSV iOrderCancelSV = DubboConsumerFactory.getService(IOrderCancelSV.class);
  				Long Id = Long.parseLong(orderId);
  				query.setOrderId(Id);
  				query.setTenantId(user.getTenantId());
  				BaseResponse base = iOrderCancelSV.handCancelNoPayOrder(query);
  				if(base.getResponseHeader().getIsSuccess()==true){
  					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "关闭订单成功", null);
  				}else{
  					responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "关闭订单失败", null);
  				}
  			} catch (Exception e) {
  				e.printStackTrace();
  				LOG.error("关闭订单报错：", e);
  			}
  			return responseData;
  		}
  		
  		
  		/**
	     * 翻译订单字段
	     * @param orderDetail
	     * @param iCacheSV
	     * @author caofz
	     * @ApiDocMethod
	     * @ApiCode 
	     * @RestRelativeURL
	     */
	    private void translateFileds(OrderWarmDetail orderDetail,ICacheSV iCacheSV) {
	    	//翻译预警类型
			SysParam sysParam = TranslateFiledsUtil.translateInfo(Constants.TENANT_ID, 
					Constants.TYPE_CODE, Constants.ORD_WARNING_TYPE,orderDetail.getWarningType(), iCacheSV);
     		if(sysParam!=null){
     			orderDetail.setWarningType(sysParam.getColumnDesc());
     		}
     		//翻译订单状态
     		SysParam stateOrder = TranslateFiledsUtil.translateInfo(Constants.TENANT_ID, 
					Constants.TYPE_CODE, Constants.ORD_STATE,orderDetail.getState(), iCacheSV);
    		if(stateOrder!=null){
    			orderDetail.setState(stateOrder.getColumnDesc());
    		}
    		//翻译订单类型
     		SysParam typeOrder = TranslateFiledsUtil.translateInfo(Constants.TENANT_ID, 
					Constants.TYPE_CODE, Constants.ORDER_TYPE,orderDetail.getOrderType(), iCacheSV);
    		if(typeOrder!=null){
    			orderDetail.setOrderType(typeOrder.getColumnDesc());
    		}
    		//翻译订单来源
    		SysParam chldParam = TranslateFiledsUtil.translateInfo(Constants.TENANT_ID, 
					Constants.TYPE_CODE, Constants.ORD_CHL_ID,orderDetail.getChlId(), iCacheSV);
    		if(chldParam!=null){
    			orderDetail.setChlId(chldParam.getColumnDesc());
    		}
    		//翻译配货方式
    		SysParam logicOrder = TranslateFiledsUtil.translateInfo(Constants.TENANT_ID,Constants.TYPE_CODE,
    				Constants.ORD_LOGISTICS_TYPE,orderDetail.getLogisticsType(),iCacheSV);
    		if(logicOrder!=null){
    			orderDetail.setLogisticsType(logicOrder.getColumnDesc());
    		}
		}
}
