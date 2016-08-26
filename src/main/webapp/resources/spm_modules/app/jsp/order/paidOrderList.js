define('app/jsp/order/paidOrderList', function (require, exports, module) {
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
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var paidOrderPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		//查询
            //"click #agrren":"_agrrenChangeGoods",
            "click #search":"_searchList"
        },
    	//重写父类
    	setup: function () {
    		paidOrderPager.superclass.setup.call(this);
    		// 初始化执行搜索
			//this._searchList();
			//this._bindSelect();
    	},
    	
    	//获取查询参数
		_getQueryParams:function(){
			var _this = this;
			return{
			    "orderTimeBegin":function () {
			    	_this.orderTimeBegin = jQuery.trim($("#orderTimeBegin").val());
			        return _this.orderTimeBegin;
			    },
			    "orderTimeEnd":function () {
			    	_this.orderTimeEnd = jQuery.trim($("#orderTimeEnd").val());
			        return _this.orderTimeEnd;
			    },
			    "orderId":function () {
			    	_this.orderId = jQuery.trim($("#orderId").val());
			        return _this.orderId;
			    }
			}
		},
		_searchList:function(){
			var _this=this;
			var orderid = $("#orderId").val();
			if(!/^[0-9]*$/.test(orderid)){
		    	var d = Dialog({
					title: '提示',
					content:"关键字应该为数字",
					icon:'prompt',
					okValue: '确 定',
					ok:function(){
						this.close();
					}
		    	});
				d.show();
				return false;
		    }
			var queryParams = this._getQueryParams();
			$("#pagination").runnerPagination({
				url: _base+"/getPaidOrderData",
				method: "POST",
				dataType: "json",
				processing: true,
				data : queryParams,
				pageSize: paidOrderPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#paidTemple");
						var htmlOut = template.render(data);
						$("#paidData").html(htmlOut);
					}else{
						$("#paidData").html("未搜索到信息");
					}
				},
			});
		}
		
    });
    
    module.exports = paidOrderPager
});

