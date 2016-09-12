package com.ai.ch.order.web.controller.constant;

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
	
	
	public static final class OrdOrder {

		public static class State {
 
            /**
             * 11 待付款
             */
            public static final String WAIT_PAY = "11";

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

}
