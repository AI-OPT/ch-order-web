define('app/jsp/order/waitInvoiceDetails', function (require, exports, module) {
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
    		"click #backPage":"_back"
        },
    	//重写父类
    	setup: function () {
    		demopagePager.superclass.setup.call(this);
    	},
    	
    	_back:function() {
    		//调到订单列表页面
    		window.location.href = _base+"/order/toOrderList"
    	},
    	
    	/*_backModal:function(Obj,prodObj) {
    		$("backModalLabel"+Obj).text()=prodObj;
    	},*/
    	
    	_backOrder:function(orderObject) {
			 var _obj=$("#backNum"+orderObject).val();
			 var _orderId = $('#orderId').val();
			 var _prodDetalId=orderObject;
			 ajaxController.ajax({
					type : "POST",
					url :_base+"/aftersaleorder/back",
					data: {
						orderId:  _orderId,
						prodDetalId:_prodDetalId,
						prodSum:_obj
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
								content:"退货申请失败:"+data.statusInfo,
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
		 },
		 
		 _exchangeOrder:function(orderObject) {
			 var _orderId = $('#orderId').val();
			 var _prodDetalId=orderObject;
			 ajaxController.ajax({
					type : "POST",
					url :_base+"/aftersaleorder/exchange",
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
								content:"换货申请成功",
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
								content:"换货申请失败:"+data.statusInfo,
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
		 },
		 
		 _refundOrder:function(orderObject) {
			 var _orderId = $('#orderId').val();
			 var _prodDetalId=orderObject;
			 ajaxController.ajax({
					type : "POST",
					url :_base+"/aftersaleorder/refund",
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
								content:"退款申请成功",
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
								content:"退款申请失败:"+data.statusInfo,
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
		 },
		
		_displayInvoiceOrder: function(){
			var _orderId = $('#orderId').val();
			ajaxController.ajax({
				type : "POST",
				url :_base+"/deliveryPrint/query",
				data: {
					orderId:  _orderId
				},
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var template = $.templates("#deliverPrintTempalte");
					var htmlOutput = template.render(data.data);
					$("#deliverPrintModal").html(htmlOutput);
					$("#myDeliverModal").modal('show');
				}
			});
		},
		
		
		 _printInvoiceOrder:function() {
			 var orderInfo = new Array();
			 $("#invoiceDisPlay").find("tr").each(function(i) {
				var skuId=$("#"+i+"_skuId").text();
				var prodName=$("#"+i+"_prodName").text();
				var extendInfo=$("#"+i+"_extendInfo").text();
				var salePrice=$("#"+i+"_salePrice").text();
				var buySum=$("#"+i+"_buySum").text();
				var horOrderId=$("#"+i+"_horOrderId").text();
				var prodObj = {"skuId":skuId,"prodName":prodName,"extendInfo":extendInfo,
						"salePrice":salePrice,"buySum":buySum,"horOrderId":horOrderId};
				orderInfo.push(prodObj);
			 })
			var _orderInfos=JSON.stringify(orderInfo);
			var _orderId = $('#invoiceId').html();
			ajaxController.ajax({
				type : "POST",
				url :_base+"/deliveryPrint/print",
				data: {
					orderId:  _orderId,
					orderInfos:_orderInfos
				},
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					if(data) {
						alert("打印成功");
					}
				}
			});
		 },
		 
		 _afterorderdetail:function(orderId,skuId){
			window.location.href = _base+"/judge?orderId="
            + orderId+"&skuId="+skuId;
		},
		_sendGoods:function(obj){
			var orderId=obj;
			window.location.href = _base+"/deliveryPrint/deliverGoods?orderId="+orderId;
		},
    });
    
    module.exports = demopagePager
});

