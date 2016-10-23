define('app/jsp/order/changeGoodsSecond', function (require, exports, module) {
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
    var changeSecondPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #backPage":"_back",
            "click #sendGoods":"_sendGoods",
            "click #confirmChange":"_confirmChange"
            
        },
    	//重写父类
    	setup: function () {
    		changeSecondPager.superclass.setup.call(this);
    	},
    	_back:function(){
    		var sorceFlag = $("#sourceFlag").val();
    		alert("....."+sorceFlag);
    		if(sorceFlag=="00"){
    			window.location.href=_base+"/order/toOrderList";
    		}else{
    			window.location.href=_base+"/toPaidOrder";
    		}
    	},
    	//收到换货
    	_confirmChange:function(){
    		var orderid = $("#orderId").text();
    		var url =_base+"/confirmChange";
    		   ajaxController.ajax({
       	    	type: "post",
   				dataType: "json",
   				processing: false,
   				message: "查询中，请等待...",
   				url: url,
   				data:{"orderId":orderid},
       	        success: function (data) {
       	        	if(data.statusCode == "1"){
       	        		window.location.href=_base+"/toPaidOrder";
       	        	}else{
       	        		var d = Dialog({
   							title: '消息',
   							content:"收到换货失败:"+data.statusInfo,
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
    	}
		
    });
    
    module.exports = changeSecondPager
});


