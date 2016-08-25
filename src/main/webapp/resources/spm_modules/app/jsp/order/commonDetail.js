define('app/jsp/order/commonDetail', function (require, exports, module) {
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
    var commonPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #showQuery":"_showQueryInfo"
        },
    	//重写父类
    	setup: function () {
    		commonPager.superclass.setup.call(this);
    	},
		
		_showQueryInfo: function(){
			//展示查询条件
			var info= $("#queryInfo").is(":hidden"); //是否隐藏
		    if(info==true){
		    	$("#queryInfo").show();
		    }else{
		    	$("#queryInfo").hide();
		    }
		}
    });
    
    module.exports = commonPager
});

