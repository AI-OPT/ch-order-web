package com.ai.ch.order.web.controller.order;

import java.sql.Timestamp;
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
import com.ai.ch.order.web.utils.ImageUtil;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;
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
    	IOrderWarmSV iOrderWarmSV = DubboConsumerFactory.getService(IOrderWarmSV.class);
    	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
    	OrderWarmRequest req = new OrderWarmRequest();
    	ResponseData<PageInfo<OrderWarmVo>> responseData = null;
        req.setTenantId("SLP");
        if (!StringUtil.isBlank(orderTimeBegin)) {
        	orderTimeBegin = orderTimeBegin + " 00:00:00";
			Timestamp ts  = Timestamp.valueOf(orderTimeBegin);
			req.setOrderTimeStart(ts);
		}
        if (!StringUtil.isBlank(orderTimeEnd)) {
        	orderTimeEnd = orderTimeEnd + " 00:00:00";
			Timestamp ts  = Timestamp.valueOf(orderTimeEnd);
			req.setOrderTimeEnd(ts);
		}
        String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"5":request.getParameter("pageSize");
        try {
            req.setPageNo(Integer.parseInt(strPageNo));
            req.setPageSize(Integer.parseInt(strPageSize));
            OrderWarmResponse resultInfo = iOrderWarmSV.serchWarmOrder(req);
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
            		SysParam sysParam = iCacheSV.getSysParamSingle(param);
            		if(sysParam!=null){
            			order.setWarningType(sysParam.getColumnDesc());
            		}
            		//翻译是否需要物流
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(order.getDeliveryFlag());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_DELIVERY_FLAG);
            		SysParam ifDlive = iCacheSV.getSysParamSingle(param);
            		if(ifDlive!=null){
            			order.setDeliveryFlag(ifDlive.getColumnDesc());
            		}
            		//翻译是否是预警订单
            		param = new SysParamSingleCond();
            		param.setTenantId(Constants.TENANT_ID);
            		param.setColumnValue(order.getIfWarning());
            		param.setTypeCode(Constants.TYPE_CODE);
            		param.setParamCode(Constants.ORD_IF_WARNING);
            		SysParam ifWarmOrder = iCacheSV.getSysParamSingle(param);
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
            		if(ifWarmOrder!=null){
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
    @RequestMapping("/alertDetail")
	public ModelAndView changeFirstDetail(HttpServletRequest request, String orderId) {
		Map<String, OrderWarmVo> model = new HashMap<String, OrderWarmVo>();
		IOrderWarmSV iOrderWarmSV = DubboConsumerFactory.getService(IOrderWarmSV.class);
		OrderWarmDetailRequest  query =new OrderWarmDetailRequest ();
		OrderWarmVo orderWarm = new OrderWarmVo();
		try {
			Long Id = Long.parseLong(orderId);
			query.setOrderId(Id);
			query.setTenantId("SLP");
			OrderWarmDetailResponse response = iOrderWarmSV.searchWarmorderDetail(query);
			if(response!=null && response.getResponseHeader().isSuccess()){
				 orderWarm =  response.getOrderWarmVo();
				if(orderWarm!=null){
					List<ProductInfo> proList = orderWarm.getProdInfo();
					//获取图片
					if (!CollectionUtil.isEmpty(proList)) {
						for (ProductInfo vo:proList) {
							vo.setImageUrl(
									ImageUtil.getImage(vo.getProductImage().getVfsId(), vo.getProductImage().getPicType()));
						}
					}
				}
			}
			 model.put("order", orderWarm);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("预警订单详情查询报错：", e);
		}
		return new ModelAndView("jsp/order/alertOrderDetail", model);
	}
}
