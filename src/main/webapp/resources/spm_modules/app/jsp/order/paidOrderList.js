define('app/jsp/order/paidOrderList', function (require, exports, module) {
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
    
    require("jquery-validation/1.15.1/jquery.validate");
	require("app/util/aiopt-validate-ext");
	
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var paidOrderPager = Widget.extend({
    	
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
            //"click #agrren":"_agrrenChangeGoods",
            "click #search":"_searchList"
            //"click #moreSearch":"_highSearch"
        },
    	//重写父类
    	setup: function () {
    		paidOrderPager.superclass.setup.call(this);
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    		// 初始化执行搜索
			this._searchList();
    	},
    	_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
    				orderId: {
    					digits:true,
    					min:1,
    					max:20
    				}
    			},
    			messages: {
    				orderId: {
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}"
    				}
    			}
    		});
    		return formValidator;
    	},
    	//获取查询参数
		_getQueryParams:function(){
			var _this = this;
			return{
			    "startTime":function () {
			    	_this.startTime = jQuery.trim($("#orderTimeBegin").val());
			        return _this.startTime;
			    },
			    "endTime":function () {
			    	_this.endTime = jQuery.trim($("#orderTimeEnd").val());
			        return _this.endTime;
			    },
			    "inputOrderId":function () {
			    	_this.inputOrderId = jQuery.trim($("#orderId").val());
			        return _this.inputOrderId;
			    }
			}
		},
		/*//获取高级查询参数
		_getHignQueryParams:function(){
			var _this = this;
			return{
			    "startTime":function () {
			    	_this.startTime = jQuery.trim($("#orderTimeBegin").val());
			        return _this.startTime;
			    },
			    "endTime":function () {
			    	_this.endTime = jQuery.trim($("#orderTimeEnd").val());
			        return _this.endTime;
			    },
			    "inputOrderId":function () {
			    	_this.inputOrderId = jQuery.trim($("#orderId").val());
			        return _this.inputOrderId;
			    },
			      "chlId":function () {
			    	_this.chlId = jQuery.trim($("#orderSource").val());
			        return _this.chlId;
			    },
			    "deliveryFlag":function () {
			    	_this.deliveryFlag = jQuery.trim($("#deliveryFlag").val());
			        return _this.deliveryFlag;
			    },
			    "contactTel":function () {
			    	_this.contactTel = jQuery.trim($("#contactTelQ").val());
			        return _this.contactTel;
			    }
			}
		},
		//高级搜索
		_highSearch:function(){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				alert('验证不通过！！！！！');
				return;
			}
			var queryParams = this._getHignQueryParams();
			$("#pagination").runnerPagination({
				url: _base+"/getPaidOrderData",
				method: "POST",
				dataType: "json",
				processing: true,
				data : queryParams,
				pageSize: paidOrderPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#paidTemple");
						var htmlOut = template.render(data);
						$("#paidData").html(htmlOut);
					}else{
						$("#paidData").html("未搜索到信息");
					}
				},
			});
		},*/
		_searchList:function(){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				alert('验证不通过！！！！！');
				return;
			}
			var queryParams = this._getQueryParams();
			$("#pagination").runnerPagination({
				url: _base+"/getPaidOrderData",
				method: "POST",
				dataType: "json",
				processing: true,
				data : queryParams,
				pageSize: paidOrderPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#paidTemple");
						var htmlOut = template.render(data);
						$("#paidData").html(htmlOut);
					}else{
						$("#paidData").html("未搜索到信息");
					}
				},
			});
		},
		_detailPage:function(orderid){
		    window.location.href = _base+"/alertDetail?orderId="
		            + orderid;
		},
		
    });
    
    module.exports = paidOrderPager
});

