define('app/jsp/order_rule/findOrderRuleDetail', function (require, exports, module) {
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
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var OrderRuleDetailPager = Widget.extend({
    	
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
        },
    	//重写父类
    	setup: function () {
    		OrderRuleDetailPager.superclass.setup.call(this);
    		this._findOrderRuleDetail();
    	},
    	_findOrderRuleDetail:function(){
    		var _this = this;
    		//
    		ajaxController.ajax({
					type: "POST",
					dataType: "json",
					processing: true,
					message: "请等待...",
					contentType:"application/x-www-form-urlencoded:charset=UTF-8",
					url: _base+"/orderrule/findOrderRuleDetail",
					data:"",
					success: function(data){
						//alert();
						$('#timeMonitorTime').html(data.orderRuleDetailVo.timeMonitorTime);
						var timeMonitorTimeType = _this._monitorTimeType(data.orderRuleDetailVo.timeMonitorTimeType);
						$('#timeMonitorTimeType').html(timeMonitorTimeType);
						$('#timeMonitorOrderSum').html(data.orderRuleDetailVo.timeMonitorOrderSum);
						$('#buyEmployeeMonitorTime').html(data.orderRuleDetailVo.buyEmployeeMonitorTime);
						var buyEmployeeMonitorTimeType = _this._monitorTimeType(data.orderRuleDetailVo.buyEmployeeMonitorTimeType);
						$('#buyEmployeeMonitorTimeType').html(buyEmployeeMonitorTimeType);
						$('#buyEmployeeMonitorOrderSum').html(data.orderRuleDetailVo.buyEmployeeMonitorOrderSum);
						$('#buyIpMonitorTime').html(data.orderRuleDetailVo.buyIpMonitorTime);
						var buyIpMonitorTimeType = _this._monitorTimeType(buyIpMonitorTimeType);
						$('#buyIpMonitorTimeType').html(data.orderRuleDetailVo.buyIpMonitorTimeType);
						$('#buyIpMonitorOrderSum').html(data.orderRuleDetailVo.buyIpMonitorOrderSum);
						$('#mergeOrderSettingTime').html(data.orderRuleDetailVo.mergeOrderSettingTime);
						$('#mergeOrderSettingOrderSum').html(data.orderRuleDetailVo.mergeOrderSettingOrderSum);
					}
				}
			);
    	},
    	_monitorTimeType:function(key){
    		var monitorTimetype = "";
    		if(key == 'MIN'){
    			monitorTimetype = "分钟";
    		}
    		if(key == 'H'){
    			monitorTimetype = "小时";
    		}
    		if(key == 'D'){
    			monitorTimetype = "天";
    		}
    		//
    		return monitorTimetype;
    	}
      	
    	
    });
    
    module.exports = OrderRuleDetailPager
});

