define('app/jsp/order/invoiceList', function (require, exports, module) {
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
    var ListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		"click #queryButtonId":"_queryPageSearch"
        },
    	//重写父类
    	setup: function () {
    		ListPager.superclass.setup.call(this);
    		this._queryPageSearch();
    	},
        _queryPageSearch:function(){
			var formData = $("#queryForm").serialize();
			//
			$("#pagination").runnerPagination({
				url:_base+"/invoiceController/queryList?"+formData,
				method:"POST",
				dataType:"json",
				processing: true,
				renderId:"table_info_id_pay_id",
				messageId:"showMessageDiv",
				data : {},
				pageSize: 10,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					//alert(data);
					var template = $.templates("#pageSearchTmpl");
					var htmlOut = template.render(data);
					//alert(data.result);
					$("#table_info_id_pay_id").html(htmlOut);
				}
			});
		},
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
						_this._queryPageSearch();
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
		_downloadInvoice:function(invoiceCode,invoiceNumber){
			var url = _base+"/invoice/downloadInvoice";
			ajaxController.ajax({
    	    	type: "post",
				dataType: "text",
				processing: false,
				message: "下载中，请等待...",
				url: url,
				data:{"invoiceCode":invoiceCode,"invoiceNumber":invoiceNumber},
    	        success: function (data) {
//    	        	alert(data);
//    	        	$('#url_id').text(data)
    	        	location.href=data;
    	        }
                
    	    });
		}
		
    });
    
    module.exports = ListPager
});

