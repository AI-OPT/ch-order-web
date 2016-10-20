define('app/jsp/order/backGoodSecond', function (require, exports, module) {
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
            "click #updateMoney":"_updateMoney",
            "click #backPage":"_back"
        },
    	//重写父类
    	setup: function () {
    		backSecondPager.superclass.setup.call(this);
    		var formValidator=this._initValidate();
    		var refuseformValidator=this._refuseInitValidate();
			$("#dataForm :input").bind("focusout",function(){
				formValidator.element(this);
			});
			$("#refuseDataForm :input").bind("focusout",function(){
				refuseformValidator.element(this);
			});
    	},
    	_back:function(){
    		window.location.href=_base+"/toPaidOrder";
    	},
    	_initValidate:function(){
    		var currentMoney = $("#currentMoney").text();
    		var currentM = parseFloat(currentMoney);
    		var formValidator=$("#dataForm").validate({
    			rules: {
    				updateMoneyData: {
    					required: true,
    					moneyNumber: true,
    					max:currentM,
    					min:0.1
    					},
	                 updateMoneyInfo:{
	                	 required: true
	                 }
    			},
    			messages: {
    				updateMoneyData: {
    					required:"请输入退款金额!",
    					max:"退款金额不能大于{0}!",
    					min:"退款金额不能为小于{0}！"
    				},
    				updateMoneyInfo:{
    					required:"请输入修改理由!"
    				}
    			}
    		});
    		
    		return formValidator;
    	},
    	_refuseInitValidate:function(){
    		var refuseformValidator=$("#refuseDataForm").validate({
    			rules: {
    				refuseMoneyInfo:{
	                	 required: true,
	                	 maxlength:200
	                 }
    			},
    			messages: {
    				refuseMoneyInfo:{
    					required:"请输入拒绝理由!",
    				    maxlength:"最大长度不能超过{0}"
    				}
    			}
    		});
    		
    		return refuseformValidator;
    	},
    	_refuseBackMoney:function(){
    		var _this= this;
    		var refuseformValidator=_this._refuseInitValidate();
    		refuseformValidator.form();
 			if(!$("#refuseDataForm").valid()){
 				return false;
 			}
    		var orderId = $("#orderId").text();
 			var info = $("#refuseMoneyInfo").val();
 			var url  = _base+"/refuseRefund";
 			 ajaxController.ajax({
     	    	type: "post",
 				dataType: "json",
 				processing: false,
 				message: "查询中，请等待...",
 				url: url,
 				data:{"orderId":orderId,"info":info},
     	        success: function (data) {
     	        	if(data){
     	        		window.location.href=_base+"/toPaidOrder";
     	        	}else{
     	        		var d = Dialog({
							title: '消息',
							content:"拒绝退款失败",
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
     	},
    	//同意退款，修改金额
    	_updateMoney:function(){
    		var _this= this;
    		var formValidator=_this._initValidate();
 			formValidator.form();
 			if(!$("#dataForm").valid()){
 				return false;
 			}
 			 var url=_base+"/refund";
 			//获取数据
 			var parentId = $("#parentId").text();
 			var orderId = $("#orderId").text();
 			var banlanceIfId = $("#balanceId").text();
 			var money = $("#updateMoneyData").val();
 			var info = $("#updateMoneyInfo").val();
 			var xf = $("#saleJF").text();
 			var zs = $("#giveJF").text();
 			var downOrdId = $("#downOrdId").val();
 			var accountId = $("#accountId").val();
 			var operId = $("#userId").val();
	   		//退款
 		    ajaxController.ajax({
    	    	type: "post",
				dataType: "json",
				processing: false,
				message: "查询中，请等待...",
				url: url,
				data:{"orderId":orderId,"updateMoney":money,"parentOrderId":parentId,"updateInfo":info,"giveJF":zs,"saleJF":xf,
					"accountId":accountId,"downOrdId":downOrdId,"banlanceIfId":banlanceIfId,"openId":operId
				},
    	        success: function (data) {
    	        	if(data){
     	        		window.location.href=_base+"/toPaidOrder";
     	        	}else{
     	        		var d = Dialog({
							title: '消息',
							content:"退款失败",
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
    
    module.exports = backSecondPager
});


