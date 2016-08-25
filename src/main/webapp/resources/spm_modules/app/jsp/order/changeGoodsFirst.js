define('app/jsp/order/changeGoodsFirst', function (require, exports, module) {
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
    var changePager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #agrren":"_agrrenChangeGoods",
            "click #refuse":"_refuseChangeGoods"
        },
    	//重写父类
    	setup: function () {
    		changePager.superclass.setup.call(this);
    	},
    	_refuseChangeGoods:function(){
    	    var orderid = 1223123;
    	    var refuseInfo = $("#refuseInfo").val();
    	   if(refuseInfo==""||refuseInfo==null){
    		   alert();
    	   }
    	    window.location.href = _base+"/refuseChange?orderId="
    	            + orderid+"&refuseInfo="+refuseInfo;
    	},
    	_agrrenChangeGoods:function(){
    		alert("huhuhu");
    	    var orderid = 1223123;
    	    window.location.href = _base+"/aggreeChange?orderId="
    	            + orderid;
    	}
		
    });
    
    module.exports = changePager
});

