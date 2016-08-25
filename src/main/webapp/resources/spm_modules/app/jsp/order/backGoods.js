define('app/jsp/order/backGoods', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var backPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #agrren":"_agrrenBackGoods",
            "click #refuse":"_refuseBackGoods"
        },
    	//重写父类
    	setup: function () {
    		backPager.superclass.setup.call(this);
    	},
    	_refuseBackGoods:function(){
    	    var orderid = 1223123;
    	    var refuseInfo = $("#refuseInfo").val();
    	    window.location.href = _base+"/refuseBack?orderId="
    	            + orderid+"&refuseInfo="+refuseInfo;
    	},
    	_agrrenBackGoods:function(){
    		alert("huhuhu");
    	    var orderid = 1223123;
    	    window.location.href = _base+"/aggreeBack?orderId="
    	            + orderid;
    	}
		
    });
    
    module.exports = backPager
});

