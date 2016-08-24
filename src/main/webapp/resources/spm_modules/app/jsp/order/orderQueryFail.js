define('app/jsp/order/orderQueryFail', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index'),
    Calendar = require('arale-calendar/1.1.2/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    var t=5;
    var timeid;
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var OrderFailPager = Widget.extend({
    	
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	//	DEFAULT_PAGE_SIZE: 5,
    	
    	},
    	//事件代理
    	events: {
    		//查询
            "click #BACK_BTN":"_goback"
        },
    	//重写父类
    	setup: function () {
    		OrderFailPager.superclass.setup.call(this);
    		this._timeCount();
    	},
    	_goback:function(){
    		window.location.href=_base+"/index";
    	},
    	_timeCount:function(){
    		window.clearInterval(timeid); 
    		timeid = window.setInterval(function(){
    			    t=t-1;
    				$("#sec").text(t);
    				if(t==0){
    					window.location.href=_base+"/index";
    				}
    			},1000);	
    	}
    	
    	
    });
    
    module.exports = OrderFailPager
});

