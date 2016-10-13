define('app/jsp/order/alertOrderDetail', function (require, exports, module) {
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
    var alertOrderDetailPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #close":"_closeOrder",
            "click #back":"_back"
        },
    	//重写父类
    	setup: function () {
    		alertOrderDetailPager.superclass.setup.call(this);
			
    	},
    	_back:function(){
    		window.location.href = _base+"/toAlertOrder";
    	},
		_closeOrder:function(){
			var orderId = $("#orderId").text();
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
							icon:'false',
							okValue: '确 定',
							ok:function(){
								this.close();
							}
						});
						d.show();
    	        	}
    	        },
                
    	    }); 
    	}
		
		
    });
    
    module.exports = alertOrderDetailPager
});

