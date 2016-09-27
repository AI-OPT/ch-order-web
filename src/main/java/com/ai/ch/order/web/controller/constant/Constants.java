package com.ai.ch.order.web.controller.constant;

import com.ai.ch.order.web.utils.PropertiesLoader;

public final class Constants {
	private Constants() {

	}
	
	public static final String TENANT_ID = "changhong";
	/** 订单typeCode */
	public static final String TYPE_CODE = "ORD_ORDER";
	/** 是否需要物流paramCode */
	public static final String ORD_DELIVERY_FLAG = "ORD_DELIVERY_FLAG";
	/** 预警类型paramCode */
	public static final String ORD_WARNING_TYPE = "ORD_WARNING_TYPE";
	/** 是否预警paramCode */
	public static final String ORD_IF_WARNING = "ORD_IF_WARNING";
	/** 订单状态paramCode */
	public static final String ORD_STATE = "ORD_STATE";
	/** 订单类型*/
	public static final String ORDER_TYPE = "ORD_TYPE";
	/** 配送方式*/
	public static final String ORD_LOGISTICS_TYPE = "ORD_LOGISTICS_TYPE";
	/** 订单来源*/
	public static final String ORD_CHL_ID = "CHL_ID";
	/** 物流信息*/
	public static final String ORD_EXPRESS = "ORD_EXPRESS";
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("ch-order-web.properties");
	
	public static final class OrdOrder {

		public static class State {
 
            /**
             * 11 待付款
             */
            public static final String WAIT_PAY = "11";
            /**
             * 111已付款
             */
            public static final String PAID = "111";
            

            /**
             * 13 待配货
             */
            public static final String WAIT_DISTRIBUTION="13";
            
            /**
             * 14 待出库
             */
            public static final String WAIT_DELIVERY = "14";
            
            /**
             * 15 待发货
             */
            public static final String WAIT_SEND="15";
 
            /**
             * 16 待确认
             */
            public static final String WAIT_CONFIRM = "16";

            /**
             * 90 完成
             */
            public static final String COMPLETED = "90";

            /**
             * 91取消
             */
            public static final String CANCEL = "91";
            
            /**
             * 92 退货完成
             */
            public static final String RETURN_COMPLETE="92";
            
            /**
             * 93 换货完成
             */
            public static final String EXCHANGE_COMPLETE="93";
            
            /**
             * 94 退款完成
             */
            public static final String REFUND_COMPLETE="94";
            /**
             * 待审核
             */
            public static final String WAIT_CHECK="21";
            /**
             * 待买家退货
             */
            public static final String WAIT_BACK="22";
            /**
             * 待卖家收货确认
             */
            public static final String WAIT_GET_GOODS="23";
            /**
             * 待退费
             */
            public static final String WAIT_BACK_FEE="31";
            /**
             * 审核失败
             */
            public static final String NO_CHECK="212";

        }
		
		public static class PayStyle {
			
			/**
			 * 积分支付
			 */
			public static final String JF = "5";
		}
		
		public static class BusiCode {
			    // 1：正常单
	            public static final String NORMAL_ORDER = "1";
	
	            // 2.换货单
	            public static final String EXCHANGE_ORDER = "2";
	
	            // 3.退货单
	            public static final String UNSUBSCRIBE_ORDER = "3";
	
	            // 4：退费单
	            public static final String CANCEL_ORDER = "4";
		}
	}
	
	
	
	/**物流APPKEY**/
    private static final String LOGISTICS_APPKEY_KEY = "logistics.appkey";  
    public static final String LOGISTICS_APPKEY = loader.getProperty(LOGISTICS_APPKEY_KEY);  
    /**物流URL**/
    private static final String LOGISTICS_URL_KEY = "logistics.url";  
    public static final String LOGISTICS_URL = loader.getProperty(LOGISTICS_URL_KEY);  
    /**发票打印URL**/
    private static final String INVOICE_PRINT_URL_KEY = "invoice.print.url";  
    public static final String INVOICE_PRINT_URL = loader.getProperty(INVOICE_PRINT_URL_KEY);  
    /**发票打印授权用户名**/
    private static final String INVOICE_PRINT_USERNAME_KEY = "invoice.print.loginName";  
    public static final String INVOICE_PRINT_USERNAME = loader.getProperty(INVOICE_PRINT_USERNAME_KEY);  
    /**发票打印授权密码**/
    private static final String INVOICE_PRINT_PASSWORD_KEY = "invoice.print.password";  
    public static final String INVOICE_PRINT_PASSWORD = loader.getProperty(INVOICE_PRINT_PASSWORD_KEY);  
  
    
    /**用户结分查询APPKEY**/
    private static final String INTEGRAL_SEARCH_APPKEY_KEY = "integral.search.appkey";  
    public static final String INTEGRAL_SEARCH_APPKEY = loader.getProperty(INTEGRAL_SEARCH_APPKEY_KEY);  
    /**用户积分查询URL**/
    private static final String INTEGRAL_SEARCH_URL_KEY = "integral.search.url";  
    public static final String INTEGRAL_SEARCH_URL = loader.getProperty(INTEGRAL_SEARCH_URL_KEY); 
    /**用户消费积分撤销APPKEY**/
    private static final String INTEGRAL_SHOPBACK_APPKEY_KEY = "integral.shopback.appkey";  
    public static final String INTEGRAL_SHOPBACK_APPKEY = loader.getProperty(INTEGRAL_SHOPBACK_APPKEY_KEY); 
    /**用户消费积分撤销**/
    private static final String INTEGRAL_SHOPBACK_URL_KEY = "integral.shopback.url";  
    public static final String INTEGRAL_SHOPBACK_URL = loader.getProperty(INTEGRAL_SHOPBACK_URL_KEY); 
    
    /**用户名称查看长虹用户信息APPKEY*/
    private static final String CH_USERNAME_APPKEY_KEY  = "ch.username.appkey";  
    public static final String CH_USERNAME_APPKEY = loader.getProperty(CH_USERNAME_APPKEY_KEY); 
    /**用户名称查看长虹用户信息**/
    private static final String CH_USERNAME_URL_KEY = "ch.username.url";  
    public static final String CH_USERNAME_URL = loader.getProperty(CH_USERNAME_URL_KEY); 
    
    /**http代理URL**/
    private static final String HTTP_PROXY_URL_KEY = "http.proxy.url";  
    public static final String HTTP_PROXY_URL = loader.getProperty(HTTP_PROXY_URL_KEY); 
    /**http代理端口**/
    private static final String HTTP_PROXY_PORT_KEY = "http.proxy.port";  
    public static final String HTTP_PROXY_PORT = loader.getProperty(HTTP_PROXY_PORT_KEY); 
    
    
    
   
}
