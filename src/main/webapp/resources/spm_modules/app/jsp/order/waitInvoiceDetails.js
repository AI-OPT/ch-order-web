define('app/jsp/order/waitInvoiceDetails', function (require, exports, module) {
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
    	
    	_backOrder:function(orderObject,backSum) {
			 var _prodDetalId=orderObject;
			 var _orderId = $('#orderId').val();
			 var _pOrderId = $('#pOrderId').val();
			 var _state = $('#state').val();
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
						"price":salePrice,"buySum":buySum,"mergeOrderId":horOrderId};
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
					if(data.statusCode == "1") {
						var d = Dialog({
    	    				title : '提示',
    	    				content : '打印成功',
    	    				icon:'success',
    	    				closeIconShow:false,
    	    				okValue : "确定",
    	    				ok : function() {
    	    					this.close;
    	    				}
    	    			});
    	    			d.show();
					}else {
						var d = Dialog({
    	    				title : '提示',
    	    				content : '打印失败'+data.statusInfo,
    	    				icon:'success',
    	    				closeIconShow:false,
    	    				okValue : "确定",
    	    				ok : function() {
    	    					this.close;
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
            + orderId+"&skuId="+skuId+"&sourceFlag"+sourceFlag;
		},
		_sendGoods:function(obj,pOrderId,state,busiCode,flag){
			var orderId=obj;
			window.location.href = _base+"/deliveryPrint/deliverGoods?orderId="+orderId +"&pOrderId="+pOrderId+"&state="+state+"&busiCode="+busiCode+"&Flag="+flag;
		},
		/**
		 * 发票打印 zhangzhongde 2016-10-20 10:27
		 */
		_invoicePrint:function(tenantId,orderId){
			var _this = this;
			//
			var url = _base+"/invoice/invoicePrint";
			ajaxController.ajax({
    	    	type: "post",
				dataType: "json",
				processing: false,
				message: "打印中，请等待...",
				url: url,
				data:{"tenantId":tenantId,"orderId":orderId},
    	        success: function (data) {
    	        	
	    	        if(data.IsSuccessful == false){
    	        		//alert(data.MessageKey);
    	        		var d = Dialog({
							content:data.MessageKey,
							icon:'fail',
							okValue: '确 定',
							ok:function(){
								this.close();
							}
						});
						d.show();
    	        	}else if(data.IsSuccessful == true){
    	        		//
    	        		_this._modifyInvoiceState(tenantId,orderId,"1");
    	        		//
    	        	}else{
    	        		var d = Dialog({
							content:"发票报送失败",
							icon:'fail',
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
		_modifyInvoiceState:function(tenantId,orderId,invoiceStatus){
			var _this = this;
			var url = _base+"/invoice/modifyInvoiceState";
			ajaxController.ajax({
    	    	type: "post",
				dataType: "json",
				processing: false,
				message: "打印中，请等待...",
				url: url,
				data:{"tenantId":tenantId,"orderId":orderId,"invoiceStatus":invoiceStatus},
    	        success: function (data) {
    	        	if(data.responseHeader.resultCode == '000000'){
    	        		//
    	        		var d = Dialog({
							content:"发票报送成功",
							icon:'success',
							okValue: '确 定',
							ok:function(){
								this.close();
							}
						});
						d.show();
						//
						
    	        	}else{
    	        		//
    	        		var d = Dialog({
							content:"发票报送状态修改失败",
							icon:'fail',
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
		
		_truePrint:function(){
	        /*$("#realPrint").printArea(
	        		 {
	        		     debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
	        		     importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
	        		     printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
	        		     operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
	        		}	
	        );*/
			var head="<html><head><title></title></head><body>";//先生成头部
			var foot="</body></html>";//生成尾部
			var newstr=document.all.item('realPrint').innerHTML;//获取指定打印区域
			var oldstr=document.body.innerHTML;//获得原本页面的代码
			document.body.innerHTML=head+newstr+foot;//购建新的网页
			window.print();//打印刚才新建的网页
			document.body.innerHTML=oldstr;//将网页还原
			this._displayInvoiceOrder();
			return false;
	    }
    });
    
    module.exports = demopagePager
});

