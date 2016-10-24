define('app/jsp/order/afterComplete', function (require, exports, module) {
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
    var afterCompletePager = Widget.extend({
    	
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
    		afterCompletePager.superclass.setup.call(this);
    	},
    	
    	
    	_back:function() {
    		var sorceFlag = $("#sourceFlag").val();
    		if(sorceFlag=="00"){
    			window.location.href=_base+"/order/toOrderList";
    		}else{
    			window.location.href=_base+"/toPaidOrder";
    		}
    	}

    });
    
    module.exports = afterCompletePager
});

