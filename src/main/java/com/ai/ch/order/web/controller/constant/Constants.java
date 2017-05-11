package com.ai.ch.order.web.controller.constant;

import com.ai.ch.order.web.utils.PropertiesLoader;

public final class Constants {
	private Constants() {

	}
	/** 订单租户id */
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
	/** 配送paramCode*/
	public static final String LOGISTICS_TYPE = "LOGISTICS_TYPE";
	/** 订单来源*/
	public static final String ORD_CHL_ID = "CHL_ID";
	/** 物流信息*/
	public static final String ORD_EXPRESS = "ORD_EXPRESS";
	/** 业务类型*/
	public static final String BUSI_CODE = "BUSI_CODE";
	/** 发票类型*/
	public static final String ORD_OD_INVOICE = "ORD_OD_INVOICE";
	public static final String INVOICE_TYPE = "INVOICE_TYPE";
	/**支付方式类型*/
	public static final String ORD_OD_FEE_TOTAL = "ORD_OD_FEE_TOTAL";
	public static final String PAY_STYLE = "PAY_STYLE";
	
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("ch-order-web.properties");
	
	public static final class OrdOrder {
		  /** 订单业务标识 */
		  public static final class Flag {
      			/**
      			 * 0:OFC(定时)
      			 */
      			public static final String OFC_DTIME = "0";

      			/**
      			 * 1：up平台
      			 */
      			public static final String UPPLATFORM = "1";
      			
      			/**
      			 * 1：积分平台(同步)
      			 */
      			public static final String JFSYNCH = "2";
      			
      			/**
      			 * 3:OFC(实时)
      			 */
      			public static final String OFC_ACTUAL_TIME  = "3";
          }
		/** 订单状态 */
		public static class State {
			/**
			 * 售后列表查询的状态
			 */
			 public static final String PAIED_STATES = "21,212,213,312,22,23,31,92,93,94,95";
 
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
             * 退款中
             */
            public static final String REFUND_ING="312";
            /**
             * 退款失败
             */
            public static final String REFUND_FAILD="95";
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
            
            /**
             * 审核失败(第二次审核失败)
             */
            public static final String NO_AGAIN_CHECK="213";

        }
		/** 订单支付方式 */
		public static class PayStyle {
			
			//积分支付
			public static final String JF = "5";
			// 1：虹付通
            public static final String CHANG_HONG_STYLE = "13";

            // 2.支付宝
            public static final String ZHIFUBAO_STYLE = "21";

            // 3.银联
            public static final String YINLIAN_STYLE = "22";

            // 4：微信支付
            public static final String WEIXIN_STYLE = "23";
            
            //5:在线支付
            public static final String ZAIXIAN_STYLE = "27";
            
            //6:财付通
            public static final String CAIFUTONG_STYLE = "28";
            
            //7:线下转账
            public static final String XIANXIA_STYLE = "29";
            
            //8:货到付款
            public static final String HUODAOFUKUAN_STYLE = "7";
            
            //9:邮局付款
            public static final String YOUJU_STYLE = "30";
            
            //10:分期付款
            public static final String FENQI_STYLE = "31";
            
            //11:易付宝
            public static final String YIFUBAO_STYLE = "32";
            
            //微支付
            public static final String WEIZHIFU_STYLE = "33";
            
            //其它
            public static final String QITA_STYLE = "34";
            
            //网付通
            public static final String WANGFUTONG_STYLE = "20";
            
            //鸿支付
            public static final String HONGZHIFU_STYLE = "24";
            
            //优酷支付
            public static final String YOUKU_STYLE = "25";
            
            //畅捷支付
            public static final String CHANGJIE_STYLE = "26";
            //余额支付
            public static final String YUE_STYLE = "1";
            //授信支付
            public static final String SHOUXIN_STYLE = "2";
            //充值卡
            public static final String CHONGZHIKA_STYLE = "3";
            //赠送预存
            public static final String ZENGSONG_STYLE = "4";
            //积分
            public static final String JIFEN_STYLE = "5";
            //银行卡(POS)
            public static final String POS_STYLE = "6";
            //优惠券
            public static final String YOUHUI_STYLE = "8";
            //支票支付
            public static final String ZHIPIAO_STYLE = "9";
            //转账汇款
            public static final String ZHUANZHANG_STYLE = "9";
            //银行卡代扣
            public static final String DAIKOU_STYLE = "11";
            //银行卡托收
			public static final String TUOSHOU_STYLE = "12";
            
		}
		/** 订单业务类型 */
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
    /**发票下载url*/
    private static final String INVOICE_DOWNLOAD_URL_KEY = "invoice.download.url";  
    public static final String INVOICE_DOWNLOAD_URL = loader.getProperty(INVOICE_DOWNLOAD_URL_KEY);
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
    
    /**支付中心地址**/
    private static final String CH_PAY_KEY = "ch.pay.url";  
    public static final String CH_PAY_URL = loader.getProperty(CH_PAY_KEY);  
    /**退款通知地址**/
    private static final String CH_REFUND_KEY = "ch.refundnotice.url";  
    public static final String CH_REFUND_URL = loader.getProperty(CH_REFUND_KEY); 
    
    /**一级商户**/
    private static final String CH_PAY_FIRST_KEY = "ch.pay.first.merchant";  
    public static final String ch_pay_first_merchant = loader.getProperty(CH_PAY_FIRST_KEY); 
    /**二级商户**/
    private static final String CH_PAY_TWO_KEY = "ch.pay.two.merchant";  
    public static final String ch_pay_two_merchant = loader.getProperty(CH_PAY_TWO_KEY); 
}
