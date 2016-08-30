define('app/jsp/order/paidOrderDetails', function (require, exports, module) {
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
    var demopagePager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10,
    		USER_LEFT_MNU_ID: "left_mnu_account_balance"
    	},
    	//事件代理
    	events: {
    		//查询
            //"click #BTN_SEARCH":"_search",
            //"click #moreId":"_more"
        },
    	//重写父类
    	setup: function () {
    		demopagePager.superclass.setup.call(this);
    	//	this._demopage();
    	},
    	_demopage:function(){
    		alert('deliveryTemplate');
    	},
    	
      	_queryDeliveryOrder: function(){
      		var _this = this;
			var _orderId = $('#orderId').val();
			ajaxController.ajax({
				type : "POST",
				url :_base+"/order/query",
				data: {
					orderId:  _orderId
				},
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					if(!data.data.flag){
						_this._noMergeDisplayDeliveryOrder();
					}else{
						$("#mergeQueryModal").modal('show');
					}
				}
			});
		},
		
		_displayDeliveryOrder: function(){
			var _orderId = $('#orderId').val();
			ajaxController.ajax({
				type : "POST",
				url :_base+"/order/display",
				data: {
					orderId:  _orderId
				},
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var template = $.templates("#deliveryOrderTempalte");
					var htmlOutput = template.render(data.data);
					$("#deliveryModal").html(htmlOutput);
					$("#myModal").modal('show');
				}
			});
		},
		
		
		_noMergeDisplayDeliveryOrder: function(){
			var _orderId = $('#orderId').val();
			ajaxController.ajax({
				type : "POST",
				url :_base+"/order/noMergeQuery",
				data: {
					orderId:  _orderId
				},
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var template = $.templates("#deliveryOrderTempalte");
					var htmlOutput = template.render(data.data);
					$("#deliveryModal").html(htmlOutput);
					$("#myModal").modal('show');
				}
			});
		},
		
		 _printDeliveryOrder:function() {
			 var orderInfo = new Array();
			 $("#orderDisPlay").find("tr").each(function(i) {
				var skuId=$("#"+i+"_skuId").text();
				var prodName=$("#"+i+"_prodName").text();
				var extendInfo=$("#"+i+"_extendInfo").text();
				var buySum=$("#"+i+"_buySum").text();
				var horOrderId=$("#"+i+"_horOrderId").text();
				var salePrice=$("#"+i+"_salePrice").text();
				var prodObj = {"skuId":skuId,"prodName":prodName,"extendInfo":extendInfo,
						"buySum":buySum,"horOrderId":horOrderId,"salePrice":salePrice};
				orderInfo.push(prodObj);
			 })
			var _orderInfos=JSON.stringify(orderInfo);
			var _orderId = $('#orignOrderId').html();
			var _contactName = $('#contactId').html();
			ajaxController.ajax({
				type : "POST",
				url :_base+"/order/print",
				data: {
					orderId:  _orderId,
					contactName:_contactName,
					orderInfos:_orderInfos
				},
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					if(data) {
						$("#but").attr("disabled","disabled");
					}
				}
			});
		 },
		 
		 _backOrder:function(orderObject) {
			 var _orderId = $('#orderId').val();
			 var _prodDetalId=orderObject;
			 ajaxController.ajax({
					type : "POST",
					url :_base+"/aftersaleorder/back",
					data: {
						orderId:  _orderId,
						prodDetalId:_prodDetalId
					},
					processing: true,
					message : "正在处理中，请稍候...",
					success : function(data) {
						if(data.statusCode == "1"){
							var d = Dialog({
								title: '消息',
								content:"退货申请成功",
								icon:'prompt',
								okValue: '确 定',
								ok:function(){
									this.close();
								}
							});
							d.show();
	    	        	}else{
	    	        		var d = Dialog({
								title: '消息',
								content:"退货失败:"+data.statusInfo,
								icon:'prompt',
								okValue: '确 定',
								ok:function(){
									this.close();
								}
							});
							d.show();
	    	        	}
					}
				});
		 }
    	
    });
    
    module.exports = demopagePager
});

