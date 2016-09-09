define('app/jsp/order/unpaidOrderDetail', function (require, exports, module) {
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
    
    require("jquery-validation/1.15.1/jquery.validate");
	require("app/util/aiopt-validate-ext");
	
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var unpaidOrderPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #update":"_updateMobey",
            "click #close":"_closeDialog",
            "click #closeOrder":"_closeOrder",
            "click #backPage":"_back"
            
        },
    	//重写父类
    	setup: function () {
    		unpaidOrderPager.superclass.setup.call(this);
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	_back:function(){
    		//调到订单列表页面
    		var state = "11";
    		window.location.href = _base+"/order/toOrderList?stateFlag="
            + state
    	},
    	_initValidate:function(){
    		var currentMoney = $("#currentMony").text();
    		var formValidator=$("#dataForm").validate({
    			 errorPlacement: function(error, element) {
                    $("#errorMessage").append( error );
                 },
    			rules: {
    				updateFee: {
    					required: true,
    					moneyNumber: true,
    					max:currentMoney
    					}
    			},
    			messages: {
    				updateFee: {
    					required:"请输入修改金额!",
    					max:"修改金额不能大于当前金额!",
    				}
    			}
    		});
    		
    		return formValidator;
    	},
    	_closeOrder:function(){
			var orderId = $("#orderid").text();
    	    var url=_base+"/closeOrder";
    	    ajaxController.ajax({
    	    	type: "post",
				dataType: "json",
				processing: false,
				message: "查询中，请等待...",
				url: url,
				data:{"orderId":orderId},
    	        success: function (data) {
    	        	if(data.statusCode == "1"){
    	        		//调到订单列表页面
    	        		var state = "11";
    	        		window.location.href = _base+"/order/toOrderList?stateFlag="
    		            + state
    	        	}else{
    	        		var d = Dialog({
							title: '消息',
							content:"关闭订单失败:"+data.statusInfo,
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
    	_updateMobey:function(){
    		var _this= this;
    	    var url=_base+"/changeMoney";
    	    var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
			//获取参数
			var orderId = $("#orderid").text();
			var changeinfo = $("#updateRemark").text();
			var money = $("#updateFee").val();
    	    ajaxController.ajax({
    	    	type: "post",
				dataType: "json",
				processing: false,
				message: "查询中，请等待...",
				url: url,
				data:{"orderId":orderId,"changeInfo":changeinfo,"money":money},
    	        success: function (data) {
    	        	if(data.statusCode == "1"){
    	        		//调到订单列表页面
    	        		var state = "11";
    	        		window.location.href = _base+"/order/toOrderList?stateFlag="
    		            + state
    	        	}else{
    	        		var d = Dialog({
							title: '消息',
							content:"金额修改失败:"+data.statusInfo,
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
    	_closeDialog:function(){
    		$("#errorMessage").html("");
    		$('#eject-mask').fadeOut(100);
    		$('#add-samll').slideUp(150);
    	}
		
    });
    
    module.exports = unpaidOrderPager
});

