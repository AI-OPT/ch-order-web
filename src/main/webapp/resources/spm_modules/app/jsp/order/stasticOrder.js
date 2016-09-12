define('app/jsp/order/stasticOrder', function (require, exports, module) {
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
    
    require("jquery-validation/1.15.1/jquery.validate");
	require("app/util/aiopt-validate-ext");
	
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var stasticOrderPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		//查询
    		"click #showQuery":"_showQueryInfo",
            "click #search":"_highSearch"
        },
    	//重写父类
    	setup: function () {
    		stasticOrderPager.superclass.setup.call(this);
    		// 初始化执行搜索
			this._highSearch();
    	},
    	_showQueryInfo: function(){
			//展示查询条件
			var info= $("#selectDiv").is(":hidden"); //是否隐藏
		    if(info==true){
		    	$("#selectDiv").show();
		    }else{
		    	$("#selectDiv").hide();
		    }
		},
		
		//获取高级查询参数
		_getHignQueryParams:function(){
			var _this = this;
			var isShow = $("#selectDiv").is(":visible");
			if(!isShow){
				return{
				    "startTime":function () {
				    	_this.startTime = jQuery.trim($("#orderTimeBegin").val());
				        return _this.startTime;
				    },
				    "endTime":function () {
				    	_this.endTime = jQuery.trim($("#orderTimeEnd").val());
				        return _this.endTime;
				    },
				    "productName":function () {
				    	_this.productName = jQuery.trim($("#productName").val());
				        return _this.productName;
				    }
				}
			}else{
				return{
				    "startTime":function () {
				    	_this.startTime = jQuery.trim($("#orderTimeBegin").val());
				        return _this.startTime;
				    },
				    "endTime":function () {
				    	_this.endTime = jQuery.trim($("#orderTimeEnd").val());
				        return _this.endTime;
				    },
				    "ordParenOrderId":function () {
				    	_this.ordParenOrderId = jQuery.trim($("#parentOrderId").val());
				        return _this.ordParenOrderId;
				    },
				      "productName":function () {
				    	_this.productName = jQuery.trim($("#productName").val());
				        return _this.productName;
				    },
				    "state":jQuery.trim($("#state option:selected").val())
				}
			}
			
		},
		//高级搜索
		_highSearch:function(){
			var _this = this;
			var queryParams = this._getHignQueryParams();
			$("#pagination").runnerPagination({
				url: _base+"/getStasticOrderData",
				method: "POST",
				dataType: "json",
				data : queryParams,
				pageSize: stasticOrderPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				messageId:"showMessageDiv",
				renderId:"stasticData",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#stasticTemple");
						var htmlOut = template.render(data);
						$("#stasticData").html(htmlOut);
					}
				},
			});
		},
		_detail:function(orderid,state){
				window.location.href = _base+"/order/orderListDetail?orderId="
	            + orderid+"&state="+state;
		},
		
    });
    
    module.exports = stasticOrderPager
});

