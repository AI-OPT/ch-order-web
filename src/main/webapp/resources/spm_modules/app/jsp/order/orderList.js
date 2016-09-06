define('app/jsp/order/orderList', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("optDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var OrderListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		//查询
            //"click #agrren":"_agrrenChangeGoods",
            "click #search":"_searchOrderList",
            "click #moreSearch":"_searchOrderList"
        },
    	//重写父类
    	setup: function () {
    		OrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchOrderList();
    	},
    	
    	//获取查询参数
		_getQueryParams:function(){
			var _this = this;
			return{
			    "orderTimeBegin":function () {
			    	_this.orderTimeBegin = jQuery.trim($("#orderTimeBegin").val());
			        return _this.orderTimeBegin;
			    },
			    "orderTimeEnd":function () {
			    	_this.orderTimeEnd = jQuery.trim($("#orderTimeEnd").val());
			        return _this.orderTimeEnd;
			    }
			}
		},
		 _detailPage:function(orderid,state,pOrderId){
		    window.location.href = _base+"/order/orderListDetail?orderId="
		            + orderid+"&state="+state+"&pOrderId="+pOrderId";
		},
		_changeOrderState:function(orderStateDiv,state){
			$(".order-list-table a").removeClass("current");
			orderStateDiv.className="current";
			$("#searchOrderState").val(state);
			this._searchOrderList();
		},
		
		_searchOrderList:function(){
			var _this=this;
			var url = _base+"/order/getOrderListData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"orderListData",
				data : queryData,
				pageSize: OrderListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#orderListTemple");
						var htmlOut = template.render(data);
						$("#orderListData").html(htmlOut);
					}else{
						$("#orderListData").html("未搜索到信息");
					}
				},
			});
		},
		_getSearchParams:function(){
    		return {
    			"orderTimeBegin":jQuery.trim($("#orderTimeBegin").val()),
    			"orderTimeEnd":jQuery.trim($("#orderTimeEnd").val()),
    			"orderId":jQuery.trim($("#orderId").val()),
    			"username":jQuery.trim($("#username").val()),
    			"chlId":jQuery.trim($("#orderSource option:selected").val()),
    			"routeId":jQuery.trim($("#routeSource option:selected").val()),
    			"contactTel":jQuery.trim($("#contactTelQ").val()),
    			"deliveryFlag":jQuery.trim($("#deliveryFlag option:selected").val()),
    			"states":jQuery.trim($("#searchOrderState").val())
    		}
    	},
		
    });
    
    module.exports = OrderListPager
});

