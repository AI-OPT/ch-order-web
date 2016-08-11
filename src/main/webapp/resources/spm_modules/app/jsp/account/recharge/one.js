define('app/jsp/account/recharge/one', function (require, exports, module) {
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
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var OnePager = Widget.extend({
    	
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
    		OnePager.superclass.setup.call(this);
    		activeUserLeftMenu(OnePager.USER_LEFT_MNU_ID);
    	},
    	
    	_formSubmit:function(){
    		var payAmount = $("#payAmount").val();
    		var checkNum =  /^[0-9]*[1-9][0-9]*$/;
      		if(checkNum.test(payAmount)&&payAmount>=10){
          		$("#oneForm").submit();
      		}else{
      			var df = Dialog({
					content:"请填写不少于10元的整数金额",
					okValue:"确定",
					mask: true,  
					ok:function () {
						this.close();
					}
				});
				df.show();
      		}

      	}
      	
    	
    });
    
    module.exports = OnePager
});

