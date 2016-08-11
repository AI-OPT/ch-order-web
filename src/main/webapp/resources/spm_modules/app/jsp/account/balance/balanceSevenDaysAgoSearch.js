define('app/jsp/account/balance/balanceSevenDaysAgoSearch', function (require, exports, module) {
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
    var BalanceSevenDaysAgoSearchPager = Widget.extend({
    	
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
            //"click #BTN_SEARCH":"_search",
            //"click #moreId":"_more"
        },
    	//重写父类
    	setup: function () {
    		BalanceSevenDaysAgoSearchPager.superclass.setup.call(this);
    		activeUserLeftMenu(BalanceSevenDaysAgoSearchPager.USER_LEFT_MNU_ID);
    		//
    		this._balanceQueryUsableFund();//查询账户余额
    		this._payPasswordIsSetting();//支付密码设置与否
    		this._queryChargeBaseInfoByAcctId();
    	},
    	_updatePayPasswordJump:function(){
    		location.href=_base+"/user/payPassword/updatePayPassword";
    	},
    	//查询账户余额
    	_balanceQueryUsableFund:function(){
    		ajaxController.ajax({
					type: "post",
					dataType: "text",
					processing: true,
					message: "查询中，请等待...",
					url: _base+"/account/queryUsableFund",
					data:"",
					success: function(data){
						$('#balanceQueryUsableFundId').text(data);
					}
				}
			);
    	},
    	//支付密码设置与否
    	_payPasswordIsSetting:function(){
    		ajaxController.ajax({
					type: "post",
					dataType: "text",
					processing: true,
					message: "查询中，请等待...",
					url: _base+"/account/payPasswordIsSetting",
					data:"",
					success: function(data){
						if(data == '0'){
	       					$('#pay_password_setting_div').show();
	       				}
	       				if(data == '1'){
	       					$('#pay_password_setting_div').hide();
	       				}
					}
				}
			);
    	},
    	_queryChargeBaseInfoByAcctId:function(){
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/account/queryChargeBaseInfoByAcctId",
						data:"",
						success: function(data){
							if(data.result){
								var template = $.templates("#balanceSevenDaysAgoTmpl");
								var htmlOut = template.render(data.result);
								//alert(data.result);
								$("#table_title_id").append(htmlOut);
							}
							if(data.result.length == 0){
								var template = $.templates("#balanceSevenDaysAgoNullTmpl");
								var htmlOut = template.render("");
								$("#table_title_id").append(htmlOut);
							}
						}
					}
      		);
      	}
      	
    	
    });
    
    module.exports = BalanceSevenDaysAgoSearchPager
});

