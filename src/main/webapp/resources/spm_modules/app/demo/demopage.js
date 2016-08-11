define('app/demo/demopage', function (require, exports, module) {
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
    var demopagePager = Widget.extend({
    	
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
    		demopagePager.superclass.setup.call(this);
    		this._demopage();
    	},
    	_demopage:function(){
    		alert('demopage');
    	},
    	_queryAccountBalanceDetailList:function(objId){
    		//alert('objId'+objId);
    		$("#pagination").runnerPagination({
				url: _base+"/account/queryAccountBalanceDetailList",
				method: "POST",
				dataType: "json",
				processing: true,
				renderId:"table_info_id_pay_id",
				messageId:"showMessageDiv",
				data : {"startTime":$('#startDate_be').val(),
					"endTime":$('#endDate_be').val(),
					"busiType":$('#busiType_id').val(),
					"selectDateId":$("#select_date_id option:selected").val()
				},
				pageSize: 10,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					
				}
			});
      	}
    	
    });
    
    module.exports = demopagePager
});

