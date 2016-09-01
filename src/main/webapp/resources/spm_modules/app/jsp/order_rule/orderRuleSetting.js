define('app/jsp/order_rule/orderRuleSetting', function (require, exports, module) {
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
    var AddPager = Widget.extend({
    	
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

    		"click #orderRuleSettingButton":"_orderRuleSetting"
        },
    	//重写父类
    	setup: function () {
    		AddPager.superclass.setup.call(this);
    		this._findOrderRuleDetail();
    	},
    	_orderRuleSetting:function(){
    		var data = $("#orderRuleSettingForm").serialize();
    		//alert(data);
    		ajaxController.ajax({
					type: "POST",
					dataType: "text",
					processing: true,
					message: "请等待...",
					contentType:"application/x-www-form-urlencoded:charset=UTF-8",
					url: _base+"/orderrule/orderRuleSetting?"+data,
					data:"",
					success: function(data){
						if(data == 'true'){
							alert('操作成功');
						}
						
					}
				}
			);
    	},
    	_findOrderRuleDetail:function(){
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
						$('#timeMonitorTime').val(data.orderRuleDetailVo.timeMonitorTime);
						$('#timeMonitorTimeType').val(data.orderRuleDetailVo.timeMonitorTimeType);
						$('#timeMonitorOrderSum').val(data.orderRuleDetailVo.timeMonitorOrderSum);
						$('#buyEmployeeMonitorTime').val(data.orderRuleDetailVo.buyEmployeeMonitorTime);
						$('#buyEmployeeMonitorTimeType').val(data.orderRuleDetailVo.buyEmployeeMonitorTimeType);
						$('#buyEmployeeMonitorOrderSum').val(data.orderRuleDetailVo.buyEmployeeMonitorOrderSum);
						$('#buyIpMonitorTime').val(data.orderRuleDetailVo.buyIpMonitorTime);
						$('#buyIpMonitorTimeType').val(data.orderRuleDetailVo.buyIpMonitorTimeType);
						$('#buyIpMonitorOrderSum').val(data.orderRuleDetailVo.buyIpMonitorOrderSum);
						$('#mergeOrderSettingTime').val(data.orderRuleDetailVo.mergeOrderSettingTime);
						$('#mergeOrderSettingTimeType').val(data.orderRuleDetailVo.mergeOrderSettingTimeType);
						
					}
				}
			);
    	}
      	
    	
    });
    
    module.exports = AddPager
});

