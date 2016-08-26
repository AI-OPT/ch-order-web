define('app/jsp/order/commonDetail', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var commonPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #showQuery":"_showQueryInfo"
        },
    	//重写父类
    	setup: function () {
    		commonPager.superclass.setup.call(this);
    		this._bindSelect();
    	},
		
		_showQueryInfo: function(){
			//展示查询条件
			var info= $("#queryInfo").is(":hidden"); //是否隐藏
		    if(info==true){
		    	$("#queryInfo").show();
		    }else{
		    	$("#queryInfo").hide();
		    }
		},
		
		// 下拉
		_bindSelect : function() {
			var this_=this;
				$.ajax({
					type : "post",
					processing : false,
					url : _base+ "/getSelect",
					dataType : "json",
					data : {
						paramCode:"ORD_LOGISTICS_FLAG",
						typeCode:"ORD_ORDER"
						},
					message : "正在加载数据..",
					success : function(data) {
						var d=data.data;
						$.each(d,function(index,item){
							var paramName = d[index].columnDesc;
							var paramCode = d[index].columnValue;
							$("#deliveryFlag").append('<option value="'+paramCode+'">'+paramName+'</option>');
						})
					}
				});
		}
    });
    
    module.exports = commonPager
});

