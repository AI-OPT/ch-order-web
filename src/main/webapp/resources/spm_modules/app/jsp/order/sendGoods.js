define('app/jsp/order/sendGoods', function (require, exports, module) {
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
    var sendPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
        },
    	//重写父类
    	setup: function () {
    		sendPager.superclass.setup.call(this);
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
    				flowName1: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName2: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName3: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName4: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName5: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName6: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName7: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName8: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName9: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName10: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName11: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName12: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName13: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName14: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName15: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowName16: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				},
    				flowNameother: {
    					regexp: /^[0-9a-zA-Z]*$/g,
    					maxlength:20
    				}
    			},
    			messages: {
    				flowName1: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName2: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName3: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName4: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName5: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName6: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName7: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName8: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName9: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName10: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName11: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName12: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName13: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName14: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName15: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName13: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowName16: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				},
    				flowNameother: {
    					regexp: "只能输入字母或数字",
    					maxlength:"最大长度不能超过{0}"
    				}
    			}
    		});
    		return formValidator;
    	},
    	
    	_valideName(){
    		 var name = $("#otherName").val();
    		 $("#codeErr").hide();
    		 if(name.length>20){
    			 $("#codeErr").val("最大长度不能超过20");
    			 $("#codeErr").show();
    			 return false;
    		 }
    	},
    	_confirmSendGoods:function(obj,thisObj){
    		var count = 0;
    		/*$("input[name='flowName']").each(function(){
    	        if ($(this).val()!= ""){
    	            count++;
    	        }
    	    });*/
    		for(var i=1;i<18;i++){
    			if($("#flowName"+i).val()!=""){
    				count++;
    			}
    		}
    		if(count>1 || count == 0){
    			$('#eject-mask').fadeIn(100);
    			$('#p-operation').slideDown(200);
    			return false;
	        }
    		 var id = $("#flowName17").val();
    		 var name = $("#otherName").val();
    		 if((id!="" && name=="")){
    			$("#codeErr").val("快递公司名不能为空");
    			$("#codeErr").show();
     			return false; 
    		 }
    		 if(id!=""&&name!=""){
    			 if(name.length>20){
    				 $("#codeErr").val("最大长度为20");
    	    		 $("#codeErr").show();
    	    		 return false;
    			 }
    		 }
    		 var _this = this;
 			var formValidator=_this._initValidate();
 			formValidator.form();
 			if(!$("#dataForm").valid()){
 				return false;
 			}
    		  var _this=thisObj;
    		  var expressCompany=$(_this).parent().prev().prev().attr("value");
    		  var expressIdValue=$(_this).parent().prev().find("input").val();
    		  var url=_base+"/deliver";
			  ajaxController.ajax({
	  	    	type: "post",
				dataType: "json",
				processing: false,
				message: "查询中，请等待...",
				url: url,
				data:{"orderId":obj,"expressId":expressCompany,"expressOddNumber":expressIdValue},
	  	        success: function (data) {
	  	        	if(data.statusCode == "1"){
	  	        	//调到订单列表页面
	  	        		var orderId = $("#deliveryModal_orderId").val();
						 var pOrderId = $("#deliveryModal_parentOrderId").val();
						 var state = $("#deliveryModal_state").val();
						 var state = "16";
						 var busiCode =	$("#deliveryModal_busiCode").val();
						 var Flag = $("#deliveryModal_flag").val();
						 var url = _base+"/order/orderListDetail?orderId="+ orderId +"&pOrderId="+pOrderId+"&state="+state+"&busiCode="+busiCode+"&Flag="+Flag;
						 //alert(url);
						 location.href = url;
    	        	}else{
    	        		var d = Dialog({
							title: '消息',
							content:"发货失败:"+data.statusInfo,
							icon:'false',
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
    
    module.exports = sendPager
});

