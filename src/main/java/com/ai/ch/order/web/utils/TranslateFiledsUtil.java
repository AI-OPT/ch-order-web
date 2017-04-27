package com.ai.ch.order.web.utils;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.OrderDetail;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.platform.common.api.cache.param.SysParamSingleCond;

public class TranslateFiledsUtil {
	
		/**
	     * 翻译订单字段
	     * @param orderDetail
	     * @param iCacheSV
	     * @author caofz
	     * @ApiDocMethod
	     * @ApiCode 
	     * @RestRelativeURL
	     */
	    public static void translateFileds(OrderDetail orderDetail,ICacheSV iCacheSV) {
	    	//翻译配送方式
			SysParam logisticsParam = translateInfo(Constants.TENANT_ID, 
					Constants.ORD_LOGISTICS_TYPE,Constants.LOGISTICS_TYPE
					,orderDetail.getLogisticstype(), iCacheSV);
			orderDetail.setLogisticstype(logisticsParam == null ? "" : logisticsParam.getColumnDesc());
			//翻译订单来源
			SysParam chldParam = translateInfo(Constants.TENANT_ID, 
					Constants.TYPE_CODE,Constants.ORD_CHL_ID
					,orderDetail.getChlid(), iCacheSV);
			orderDetail.setChlidname(chldParam == null ? "" : chldParam.getColumnDesc());
			//翻译订单类型
			SysParam sysParamOrderType = translateInfo(Constants.TENANT_ID, 
					"ORD_ORDER", "ORDER_TYPE",orderDetail.getOrdertype(), iCacheSV);
			orderDetail.setOrdertypename(sysParamOrderType == null ? "" : sysParamOrderType.getColumnDesc());
			// 翻译业务类型
			SysParam sysParamBusiCode = translateInfo(Constants.TENANT_ID, 
					"ORD_ORDER", "BUSI_CODE",orderDetail.getBusicode(), iCacheSV);
			orderDetail.setBusicodename(sysParamBusiCode == null ? "" : sysParamBusiCode.getColumnDesc());
			//翻译物流公司
			SysParam sysParam = translateInfo(Constants.TENANT_ID, 
					Constants.TYPE_CODE,Constants.ORD_EXPRESS,orderDetail.getExpressid(), iCacheSV);
			orderDetail.setExpressName(sysParam == null ? "" : sysParam.getColumnDesc());
			//翻译发票类型
			SysParam sysInvoiceParam = translateInfo(Constants.TENANT_ID, 
					Constants.ORD_OD_INVOICE,Constants.INVOICE_TYPE,orderDetail.getInvoicetype(), iCacheSV);
			orderDetail.setInvoicetypename(sysInvoiceParam == null ? "" : sysInvoiceParam.getColumnDesc());
			//翻译支付方式
			SysParam sysPayStyleParam = translateInfo(Constants.TENANT_ID, 
					Constants.ORD_OD_FEE_TOTAL,Constants.PAY_STYLE,orderDetail.getPaystyle(), iCacheSV);
			orderDetail.setPaystylename(sysPayStyleParam == null ? "" : sysPayStyleParam.getColumnDesc());
		}
	    
	    /**
	     * 信息翻译
	     */
	    public static SysParam translateInfo(String tenantId, String typeCode, 
	    		String paramCode, String columnValue,ICacheSV iCacheSV) {
	    	SysParamSingleCond sysParamSingleCond = new SysParamSingleCond(
	    			tenantId, typeCode,paramCode, columnValue);
	    	SysParam sysParamInfo = iCacheSV.getSysParamSingle(sysParamSingleCond);
	    	return sysParamInfo;
	    }
}
