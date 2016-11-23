define('app/jsp/order/paidOrderDetails', function (require, exports, module) {
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
    	
      	_queryDeliveryOrder: function(orderId,parentOrderId,state,busiCode,flag){
      		var _this = this;
			var _orderId = $('#orderId').val();
			var _orderUserId = $('#orderUserId').val();
			ajaxController.ajax({
				type : "POST",
				url :_base+"/order/query",
				data: {
					orderId:  _orderId,
					orderUserId:_orderUserId
				},
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					if(data.data.mark=="1"){
						$("#mergeQueryModal").modal('show');
					}else if(data.data.mark=="2"){
						_this._noMergeDisplayDeliveryOrder(orderId,parentOrderId,state,busiCode,flag);
					}else {
						var d = Dialog({
							title: '提示',
							content:"订单存在售后商品,不可打印!",
							icon:'false',
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
		
		_displayDeliveryOrder: function(orderId,parentOrderId,state,busiCode,flag){
			var _orderId = $('#orderId').val();
			$('#printType').val('1');
			var _orderUserId = $('#orderUserId').val();
			ajaxController.ajax({
				type : "POST",
				url :_base+"/order/display",
				data: {
					orderId:  _orderId,
					orderUserId:_orderUserId
				},
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var template = $.templates("#deliveryOrderTempalte");
					var htmlOutput = template.render(data.data);
					$("#deliveryModal").html(htmlOutput);
					$("#deliveryModal_orderId").val(orderId);
					$("#deliveryModal_parentOrderId").val(parentOrderId);
					$("#deliveryModal_state").val(state);
					$("#deliveryModal_busiCode").val(busiCode);
					$("#deliveryModal_flag").val(flag);
					
					$("#myModaltakeGoods").modal('show');
				}
			});
		},
		
		
		_noMergeDisplayDeliveryOrder: function(orderId,parentOrderId,state,busiCode,flag){
			var _orderId = $('#orderId').val();
			$('#printType').val('0');
			var _orderUserId = $('#orderUserId').val();
			ajaxController.ajax({
				type : "POST",
				url :_base+"/order/noMergeQuery",
				data: {
					orderId:  _orderId,
					orderUserId:_orderUserId
				},
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var template = $.templates("#deliveryOrderTempalte");
					var htmlOutput = template.render(data.data);
					$("#deliveryModal").html(htmlOutput);
					$("#deliveryModal_orderId").val(orderId);
					$("#deliveryModal_parentOrderId").val(parentOrderId);
					$("#deliveryModal_state").val(state);
					$("#deliveryModal_busiCode").val(busiCode);
					$("#deliveryModal_flag").val(flag);
					
					$("#myModaltakeGoods").modal('show');
				}
			});
		},
		
		 _printDeliveryOrder:function() {
			 if($("#whetherPrint").val()!=1) {
				 var d = Dialog({
						title: '提示',
						content:"请确认提货单是否已经打印",
						icon:'false',
						okValue: '确 定',
						ok:function(){
							this.close();
						}
					});
					d.show();
					return;
			 }
			 var _this = this;
			 var orderInfo = new Array();
			 $("#orderDisPlay").find("tr").each(function(i) {
				var skuId=$("#"+i+"_skuId").text();
				var prodName=$("#"+i+"_prodName").text();
				var extendInfo=$("#"+i+"_extendInfo").text();
				var buySum=$("#"+i+"_buySum").text();
				var horOrderId=$("#"+i+"_horOrderId").text();
				var salePrice=$("#"+i+"_salePrice").text();
				var prodObj = {"skuId":skuId,"prodName":prodName,"extendInfo":extendInfo,
						"buySum":buySum,"mergeOrderId":horOrderId,"salePrice":salePrice};
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
						_this._returnUrl();
					}
				}
			});
		 },
		 //跳转url
		 _returnUrl:function(){
			 var orderId = $("#deliveryModal_orderId").val();
			 var pOrderId = $("#deliveryModal_parentOrderId").val();
			 var state = $("#deliveryModal_state").val();
			 if(state == '13'){
				 state = '14';
			 }
			 var busiCode =	$("#deliveryModal_busiCode").val();
			 var Flag = $("#deliveryModal_flag").val();
			 var url = _base+"/order/orderListDetail?orderId="+ orderId +"&pOrderId="+pOrderId+"&state="+state+"&busiCode="+busiCode+"&Flag="+Flag;
			 //alert(url);
			 location.href = url;
			//
			 
		 },
		 
		 _backOrder:function(orderObject,backSum) {
			 var _orderId = $('#orderId').val();
			 var _pOrderId = $('#pOrderId').val();
			 var _state = $('#state').val();
			 var _prodDetalId=orderObject;
			 ajaxController.ajax({
					type : "POST",
					url :_base+"/aftersaleorder/back",
					data: {
						orderId:  _orderId,
						prodDetalId:_prodDetalId,
						prodSum:backSum
					},
					processing: true,
					message : "正在处理中，请稍候...",
					success : function(data) {
						if(data.statusCode == "1"){
							var d = Dialog({
								title: '提示',
								content:"退货申请成功",
								icon:'success',
								okValue: '确 定',
								ok:function(){
									this.close();
									 window.location.href = _base+"/order/orderListDetail?orderId="
							            + _orderId+"&state="+_state+"&pOrderId="+_pOrderId
								}
							});
							d.show();
	    	        	}else{
	    	        		var d = Dialog({
								title: '提示',
								content:"退货申请失败:"+data.statusInfo,
								icon:'false',
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
		 
		 _refundOrder:function(orderObject,refundSum) {
			 var _orderId = $('#orderId').val();
			 var _prodDetalId=orderObject;
			 var _pOrderId = $('#pOrderId').val();
			 var _state = $('#state').val();
			 ajaxController.ajax({
					type : "POST",
					url :_base+"/aftersaleorder/refund",
					data: {
						orderId:  _orderId,
						prodDetalId:_prodDetalId,
						prodSum:refundSum
					},
					processing: true,
					message : "正在处理中，请稍候...",
					success : function(data) {
						if(data.statusCode == "1"){
							var d = Dialog({
								title: '提示',
								content:"退款申请成功",
								icon:'success',
								okValue: '确 定',
								ok:function(){
									this.close();
									 window.location.href = _base+"/order/orderListDetail?orderId="
							            + _orderId+"&state="+_state+"&pOrderId="+_pOrderId
								}
							});
							d.show();
	    	        	}else{
	    	        		var d = Dialog({
								title: '提示',
								content:"退款申请失败:"+data.statusInfo,
								icon:'false',
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
		 
		 _afterorderdetail:function(orderId,skuId){
			//用于判断来源是订单处理还是售后列表，00代表订单处理，11代表售后列表
			 var sourceFlag  ="00";
				window.location.href = _base+"/judge?orderId="
	            + orderId+"&skuId="+skuId+"&sourceFlag="+sourceFlag;
			},
			
			_truePrint:function(orderId,parentOrderId,state,busiCode,flag){
				$("#whetherPrint").val("1");
				var head="<html><head><title></title></head><body>";//先生成头部
				var foot="</body></html>";//生成尾部
				var newstr=document.all.item('realPrint').innerHTML;//获取指定打印区域
				var oldstr=document.body.innerHTML;//获得原本页面的代码
				document.body.innerHTML=head+newstr+foot;//购建新的网页
				window.print();//打印刚才新建的网页
				document.body.innerHTML=oldstr;//将网页还原
				if($('#printType').val()==1) {
					this._displayDeliveryOrder(orderId,parentOrderId,state,busiCode,flag);
				}else {
					this._noMergeDisplayDeliveryOrder(orderId,parentOrderId,state,busiCode,flag);
				}
				return false;
		    }
    }); 
    
    module.exports = demopagePager
});

