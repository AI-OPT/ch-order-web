define('app/jsp/order/backGoods', function (require, exports, module) {
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
    var backPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #agrren":"_agrrenBackGoods",
            "click #refuse":"_refuseBackGoods",
            "click #backPage":"_back"
        },
    	//重写父类
    	setup: function () {
    		backPager.superclass.setup.call(this);
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	_back:function(){
    		window.location.href=_base+"/toPaidOrder";
    	},
    	_refuseInitValidate:function(){
    		var formValidator=$("#refuseDataForm").validate({
    			 errorPlacement: function(error, element) {
                     $("#errorMessage").append( error );
                  },
    			rules: {
    				refuseInfo:{
	                	 required: true,
	                	 maxlength:200
	                 }
    			},
    			messages: {
    				refuseInfo:{
    					required:"请输入拒绝理由!",
    				    maxlength:"最大长度不能超过{0}"
    				}
    			}
    		});
    		
    		return formValidator;
    	},
    	_refuseBackGoods:function(){
    		var _this= this;
    		var formValidator=_this._refuseInitValidate();
 			formValidator.form();
 			if(!$("#refuseDataForm").valid()){
 				return false;
 			}
    		var isRefuse = true;
    	    var url=_base+"/firstBack";
    	    var refuseInfo = $("#refuseInfo").val();
    	    var orderid= $("#orderId").text();
    	    ajaxController.ajax({
    	    	type: "post",
				dataType: "json",
				processing: false,
				message: "查询中，请等待...",
				url: url,
				data:{"orderId":orderid,"refuseInfo":refuseInfo,"isRefuse":isRefuse},
    	        success: function (data) {
    	        	if(data.statusCode == "1"){
    	        		window.location.href=_base+"/toPaidOrder";
    	        	}else{
    	        		var d = Dialog({
							title: '消息',
							content:"退货审核失败:"+data.statusInfo,
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
    	_agrrenBackGoods:function(){
    		var orderid= $("#orderId").text();
    	    var url=_base+"/firstBack";
    	    var isRefuse = false;
    	    ajaxController.ajax({
    	    	type: "post",
				dataType: "json",
				processing: false,
				message: "查询中，请等待...",
				url: url,
				data:{"orderId":orderid,"isRefuse":isRefuse},
    	        success: function (data) {
    	        	if(data.statusCode == "1"){
    	        		//用于判断跳转到哪个审核页面
    	        		var flag="1";
    	        		window.location.href=_base+"/backDetail?orderId="+orderid+"&flag="+flag;
    	        	}else{
    	        		var d = Dialog({
							title: '消息',
							content:"退货审核失败:"+data.statusInfo,
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
    
    module.exports = backPager
});

