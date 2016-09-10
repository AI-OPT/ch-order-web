define('app/jsp/order/paidOrderList', function (require, exports, module) {
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
    		"click #showQuery":"_showQueryInfo",
            "click #search":"_highSearch"
        },
    	//重写父类
    	setup: function () {
    		paidOrderPager.superclass.setup.call(this);
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    		// 初始化执行搜索
			this._bindSelect();
    		this._bindChlIdSelect();
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
		
		// 下拉
		_bindSelect : function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getSelect",
				dataType : "json",
				data : {
					paramCode:"ORD_DELIVERY_FLAG",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#deliveryFlag").append('<option value="'+paramCode+'">'+paramName+'</option>');
					})
				}
			});
		},
		
		// 下拉 订单来源
		_bindChlIdSelect : function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getChlIdSelect",
				dataType : "json",
				data : {
					paramCode:"CHL_ID",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#orderSource").append('<option value="'+paramCode+'">'+paramName+'</option>');
					})
				}
			});
		},
		
    	_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
    				orderId: {
    					digits:true
    				}
    			},
    			messages: {
    				orderId: {
    					digits: "只能输入数字"
    				}
    			}
    		});
    		return formValidator;
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
				    "inputOrderId":function () {
				    	_this.inputOrderId = jQuery.trim($("#orderId").val());
				        return _this.inputOrderId;
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
			}
			
		},
		//高级搜索
		_highSearch:function(){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
			var queryParams = this._getHignQueryParams();
			$("#pagination").runnerPagination({
				url: _base+"/getPaidOrderData",
				method: "POST",
				dataType: "json",
				data : queryParams,
				pageSize: paidOrderPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				messageId:"showMessageDiv",
				renderId:"paidData",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#paidTemple");
						var htmlOut = template.render(data);
						$("#paidData").html(htmlOut);
					}
				},
			});
		},
		_detail:function(orderid,busiCode,state){
			if(busiCode==2 &&(state=='21' || state=='212' ||state=='22' ||state=='23' || state=='31')){
				window.location.href = _base+"/changeDetail?orderId="
	            + orderid;
			}else if(busiCode==3 &&(state=='21' || state=='212' ||state=='22' ||state=='23' || state=='31')){
				window.location.href = _base+"/backDetail?orderId="
	            + orderid;
			}else{
				window.location.href = _base+"/order/orderListDetail?orderId="
	            + orderid+"&state="+state;
			}
		    
		},
		
    });
    
    module.exports = paidOrderPager
});

