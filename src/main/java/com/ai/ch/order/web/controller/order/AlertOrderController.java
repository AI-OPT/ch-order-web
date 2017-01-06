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
import com.ai.slp.order.api.ordercancel.interfaces.IOrderCancelSV;
import com.ai.slp.order.api.ordercancel.param.OrderCancelRequest;
import com.ai.slp.order.api.warmorder.interfaces.IOrderWarmSV;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailRequest;
import com.ai.slp.order.api.warmorder.param.OrderWarmDetailResponse;
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
    public ResponseData<PageInfo<OrderWarmVo>> getList(HttpServletRequest request,String orderTimeBegin,String orderTimeEnd){
    	long start=System.currentTimeMillis();
    	LOG.info("开始执行预警订单列表查询getAlertOrderData，当前时间戳："+start);
    	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
    	long dubboStart=System.currentTimeMillis();
    	LOG.info("开始执行预警订单列表查询getAlertOrderData，获取后场IOrderWarmSV服务,当前时间戳："+dubboStart);
    	IOrderWarmSV iOrderWarmSV = DubboConsumerFactory.getService(IOrderWarmSV.class);
    	long cacheStart=System.currentTimeMillis();
    	LOG.info("开始执行预警订单列表查询getAlertOrderData，获取公共中心缓存服务,当前时间戳："+cacheStart);
    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
    	long cacheEnd=System.currentTimeMillis();
    	LOG.info("开始执行预警订单列表查询getAlertOrderData，获取公共中心缓存服务,当前时间戳："+cacheEnd+",用时:"+(cacheEnd-cacheStart)+"毫秒");
    	OrderWarmRequest req = new OrderWarmRequest();
    	ResponseData<PageInfo<OrderWarmVo>> responseData = null;
        req.setTenantId(user.getTenantId());
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
            OrderWarmResponse resultInfo = iOrderWarmSV.serchWarmOrder(req);
            long dubboEnd=System.currentTimeMillis();
        	LOG.info("开始执行预警订单列表查询getAlertOrderData，获取后场IOrderWarmSV服务,当前时间戳："+dubboEnd+"用时:"+(dubboEnd-dubboStart)+"毫秒");
            PageInfo<OrderWarmVo> result= resultInfo.getPageInfo();
            //翻译
            List<OrderWarmVo> list = result.getResult();
            SysParamSingleCond param = new SysParamSingleCond();
            if(!CollectionUtil.isEmpty(list)){
            	for(OrderWarmVo order:list){
            		//翻译预警类型
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(order.getWarningType());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_WARNING_TYPE);
            		long cacheOneStart=System.currentTimeMillis();
                	LOG.info("开始执行预警订单列表查询getAlertOrderData，第一次操作公共中心缓存服务,当前时间戳："+cacheOneStart);
            		SysParam sysParam = iCacheSV.getSysParamSingle(param);
            		long cacheOneEnd=System.currentTimeMillis();
                	LOG.info("开始执行预警订单列表查询getAlertOrderData，第一次操作公共中心缓存服务,当前时间戳："
                			+cacheOneEnd+",用时:"+(cacheOneEnd-cacheOneStart)+"毫秒");
            		if(sysParam!=null){
            			order.setWarningType(sysParam.getColumnDesc());
            		}
            		//翻译订单来源
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(order.getChlId());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_CHL_ID);
            		long cacheSecondStart=System.currentTimeMillis();
                	LOG.info("开始执行预警订单列表查询getAlertOrderData，第二次操作公共中心缓存服务,当前时间戳："+cacheSecondStart);
            		SysParam chldParam = iCacheSV.getSysParamSingle(param);
            		long cacheSecondEnd=System.currentTimeMillis();
                	LOG.info("开始执行预警订单列表查询getAlertOrderData，第二次操作公共中心缓存服务,当前时间戳："
                			+cacheOneEnd+",用时:"+(cacheSecondEnd-cacheSecondStart)+"毫秒");
            		if(chldParam!=null){
            			order.setChlId(chldParam.getColumnDesc());
            		}
            		//翻译是否需要物流
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(order.getDeliveryFlag());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_DELIVERY_FLAG);
            		long cache3Start=System.currentTimeMillis();
                	LOG.info("开始执行预警订单列表查询getAlertOrderData，第三次操作公共中心缓存服务,当前时间戳："+cache3Start);
                	SysParam ifDlive = iCacheSV.getSysParamSingle(param);
            		long cache3End=System.currentTimeMillis();
                	LOG.info("开始执行预警订单列表查询getAlertOrderData，第三次操作公共中心缓存服务,当前时间戳："
                			+cache3End+",用时:"+(cache3End-cache3Start)+"毫秒");
            		if(ifDlive!=null){
            			order.setDeliveryFlag(ifDlive.getColumnDesc());
            		}
            		//翻译是否是预警订单
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(order.getIfWarning());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_IF_WARNING);
            		long cache4Start=System.currentTimeMillis();
                	LOG.info("开始执行预警订单列表查询getAlertOrderData，第四次操作公共中心缓存服务,当前时间戳："+cache4Start);
            		SysParam ifWarmOrder = iCacheSV.getSysParamSingle(param);
            		long cache4End=System.currentTimeMillis();
                	LOG.info("开始执行预警订单列表查询getAlertOrderData，第四次操作公共中心缓存服务,当前时间戳："+cache4End+",用时:"+(cache4End-cache4Start)+"毫秒");
            		if(ifWarmOrder!=null){
            			order.setIfWarning(ifWarmOrder.getColumnDesc());
            		}
            		//翻译订单状态
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(order.getState());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_STATE);
            		SysParam stateOrder = iCacheSV.getSysParamSingle(param);
            		if(stateOrder!=null){
            			order.setState(stateOrder.getColumnDesc());
            		}
            	}
            }
            responseData = new ResponseData<PageInfo<OrderWarmVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", result);
        } catch (Exception e) {
            responseData = new ResponseData<PageInfo<OrderWarmVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
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
		SysParamSingleCond param = new SysParamSingleCond();
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
					//翻译预警类型
	         		param = new SysParamSingleCond();
	         		param.setTenantId(Constants.TENANT_ID);
	         		param.setColumnValue(orderDetail.getWarningType());
	         		param.setTypeCode(Constants.TYPE_CODE);
	         		param.setParamCode(Constants.ORD_WARNING_TYPE);
	         		SysParam sysParam = iCacheSV.getSysParamSingle(param);
	         		if(sysParam!=null){
	         			orderDetail.setWarningType(sysParam.getColumnDesc());
	         		}
	         		//翻译订单状态
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(orderDetail.getState());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_STATE);
            		SysParam stateOrder = iCacheSV.getSysParamSingle(param);
            		if(stateOrder!=null){
            			orderDetail.setState(stateOrder.getColumnDesc());
            		}
            		
            		//翻译订单类型
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(orderDetail.getOrderType());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORDER_TYPE);
            		SysParam typeOrder = iCacheSV.getSysParamSingle(param);
            		if(typeOrder!=null){
            			orderDetail.setOrderType(typeOrder.getColumnDesc());
            		}
            		//翻译订单来源
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(orderDetail.getChlId());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_CHL_ID);
            		SysParam chldParam = iCacheSV.getSysParamSingle(param);
            		if(chldParam!=null){
            			orderDetail.setChlId(chldParam.getColumnDesc());
            		}
            		//翻译配货方式
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(orderDetail.getLogisticsType());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_LOGISTICS_TYPE);
            		SysParam logicOrder = iCacheSV.getSysParamSingle(param);
            		if(logicOrder!=null){
            			orderDetail.setLogisticsType(logicOrder.getColumnDesc());
            		}
					List<ProductInfo> proList = orderWarm.getProdInfo();
					//获取图片
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
}
