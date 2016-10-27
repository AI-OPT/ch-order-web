define('app/jsp/order_rule/orderRuleSetting', function (require, exports, module) {
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
    require("bootstrap/js/modal");
    require("jquery-validation/1.15.1/jquery.validate");
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
    		var formValidator=this._orderRuleSettingFormValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	//form校验
    	_orderRuleSettingFormValidate:function(){
    		//
    		var orderRuleSettingFormValidator=$("#orderRuleSettingForm").validate({
    			rules: {
    				"command.timeMonitorTime": {
    					required: true,
    					digits:true,
    					min:1,
    					max:999999999
    				},
    				"command.timeMonitorOrderSum": {
    					required: true,
    					digits:true,
    					min:1,
    					max:999999999
    				},
    				"command.buyEmployeeMonitorTime": {
    					required: true,
    					digits:true,
    					min:1,
    					max:999999999
    				},
    				"command.buyEmployeeMonitorOrderSum": {
    					required: true,
    					digits:true,
    					min:1,
    					max:999999999
    				},
    				"command.buyIpMonitorTime": {
    					required: true,
    					digits:true,
    					min:1,
    					max:999999999
    				},
    				"command.buyIpMonitorOrderSum": {
    					required: true,
    					digits:true,
    					min:1,
    					max:999999999
    				},
    				"command.mergeOrderSettingTime": {
    					required: true,
    					digits:true,
    					min:1,
    					max:999999999
    				}
    				
    			},
    			messages: {
    				"command.timeMonitorTime": {
    					required: "请输入",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}"
    				},
    				"command.timeMonitorOrderSum": {
    					required: "请输入订单数量",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}"
    				},
    				"command.buyEmployeeMonitorTime": {
    					required: "请输入",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}"
    				},
    				"command.buyEmployeeMonitorOrderSum": {
    					required: "请输入订单数量",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}"
    				},
    				"command.buyIpMonitorTime": {
    					required: "请输入",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}"
    				},
    				"command.buyIpMonitorOrderSum": {
    					required: "请输入订单数量",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}"
    				},
    				"command.mergeOrderSettingTime": {
    					required: "请输入",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}"
    				}
    				
    			}
    		});
    		
    		return orderRuleSettingFormValidator;
    	},
    	_orderRuleSetting:function(){
    		var _this = this;
    		var formValidator=_this._orderRuleSettingFormValidate();
			formValidator.form();
			if(!$("#orderRuleSettingForm").valid()){
				
				return;
			}
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
							//alert('操作成功');
							$('#saveModal').modal('hide');
							var d = Dialog({
								content:"保存成功",
								icon:'success',
								okValue: '确 定',
								ok:function(){
									this.close();
									location.href=_base+"/jsp/order_rule/findOrderRuleDetail.jsp";
								}
							});
							d.show();
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
						$('#mergeOrderSettingTimeType').val('MIN');//(data.orderRuleDetailVo.mergeOrderSettingTimeType);
						
					}
				}
			);
    	},
    	_saveModalSure:function(){
    		var _this = this;
    		var formValidator=_this._orderRuleSettingFormValidate();
			formValidator.form();
			if(!$("#orderRuleSettingForm").valid()){
				
				return;
			}
			//
    		$('#saveModal').modal('show');
    	}
      	
    	
    });
    
    module.exports = AddPager
});

