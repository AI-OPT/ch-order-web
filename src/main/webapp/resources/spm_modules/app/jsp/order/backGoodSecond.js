define('app/jsp/order/backGoodSecond', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
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
    var backSecondPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #refuseBackMoney":"_refuseBackMoney",
            "click #updateMoney":"_updateMoney"
            	
        },
    	//重写父类
    	setup: function () {
    		backSecondPager.superclass.setup.call(this);
    	},
    	_refuseBackMoney:function(){
    		 var isRefuse = true;
    	    var url=_base+"/firstBack";
    	    var refuseInfo = $("#refuseMoneyInfo").val();
    	    if(refuseInfo=="" || refuseInfo==null){
    	    	var d = Dialog({
					title: '提示',
					content:"拒绝理由不能为空",
					icon:'prompt',
					okValue: '确 定',
					ok:function(){
						this.close();
					}
				});
				d.show();
				return false;
    	    }
    	},
    	_updateMoney:function(){
	   		var isRefuse = true;
	   	    var url=_base+"/firstBack";
	   	    var updateMoneyInfo = $("#updateMoneyInfo").val();
	   	    var money = $("#updateMoneyData").val();
	   	    if(money=="" || money==null){
		    	var d = Dialog({
						title: '提示',
						content:"修改金额不能为空",
						icon:'prompt',
						okValue: '确 定',
						ok:function(){
							this.close();
						}
					});
					d.show();
					return false;
		    }else if(!/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/.test(money)){
		    	var d = Dialog({
					title: '提示',
					content:"金额格式错误",
					icon:'prompt',
					okValue: '确 定',
					ok:function(){
						this.close();
					}
		    	});
				d.show();
				return false;
		    }
	   	    if(updateMoneyInfo=="" || updateMoneyInfo==null){
	   	    	var d = Dialog({
						title: '提示',
						content:"修改理由不能为空",
						icon:'prompt',
						okValue: '确 定',
						ok:function(){
							this.close();
						}
					});
					d.show();
					return false;
	   	    }
    	}
    });
    
    module.exports = backSecondPager
});

