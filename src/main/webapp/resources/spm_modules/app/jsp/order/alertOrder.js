define('app/jsp/order/alertOrder', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
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
    var alertOrderPager = Widget.extend({
    	
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
            "click #search":"_searchList"
        },
    	//重写父类
    	setup: function () {
    		alertOrderPager.superclass.setup.call(this);
    		// 初始化执行搜索
			this._searchList();
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
		 _detailPage:function(orderid){
		    window.location.href = _base+"/alertDetail?orderId="
		            + orderid;
		},
		_closeOrder:function(orderId){
    	    var url=_base+"/closeOrder";
    	    ajaxController.ajax({
    	    	type: "post",
				dataType: "json",
				processing: false,
				message: "查询中，请等待...",
				url: url,
				data:{"orderId":orderId},
    	        success: function (data) {
    	        	if(data.statusCode == "1"){
    	        		window.location.href=_base+"/toAlertOrder";
    	        	}else{
    	        		var d = Dialog({
							title: '消息',
							content:"关闭订单失败:"+data.statusInfo,
							icon:'prompt',
							okValue: '确 定',
							ok:function(){
								this.close();
							}
						});
						d.show();
    	        	}
    	        },
                
    	    }); 
    	},
		_searchList:function(){
			var _this=this;
			var queryParams = this._getQueryParams();
			$("#pagination").runnerPagination({
				url: _base+"/getAlertOrderData",
				method: "POST",
				dataType: "json",
				processing: true,
				data : queryParams,
				pageSize: alertOrderPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#alertTemple");
						var htmlOut = template.render(data);
						$("#alertData").html(htmlOut);
					}else{
						$("#alertData").html("未搜索到信息");
					}
				},
			});
		}
		
    });
    
    module.exports = alertOrderPager
});

