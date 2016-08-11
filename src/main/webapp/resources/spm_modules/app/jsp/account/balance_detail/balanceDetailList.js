define('app/jsp/account/balance_detail/balanceDetailList', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    Calendar = require('arale-calendar/1.1.2/index'),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
    require("opt-paging/aiopt.pagination");

    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var BalanceDetailListPager = Widget.extend({
    	
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
    		
    		//"click #search_button_id":"_queryParam",
    		//"click #pay_id":"_queryParam",
    		//"click #charge_id":"_queryParam",
    		//"click #all_id":"_queryParam"
        },
    	//重写父类
    	setup: function () {
    		BalanceDetailListPager.superclass.setup.call(this);
    		activeUserLeftMenu(BalanceDetailListPager.USER_LEFT_MNU_ID);
    		//加载时间查询下拉框 
    		this._searchDateList();
    		//
    		this._queryAccountBalanceDetailList();
    		this._queryParam();
    		this._bindCalendar();
    		
    	},
    	//加载时间查询下拉框
    	_searchDateList:function(){
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/account/searchDateList",
						data:"",
						success: function(data){
							//alert(data.length);
							var option = "<option value=''>--请选择--</option>";//"<option value=''>--请选择--</option>";
							for(var i=0; i<data.length; i++){
								option+= "<option value='"+data[i].columnValue+"'>"+data[i].columnDesc+"</option>";
							}
							//alert(option);
							$('#select_date_id').html(option);
						}
					}
      		);
      	},
    	//日期
    	_bindCalendar: function(){
    		new Calendar({
    			trigger: '#startDate',
    			output: '#startDate_be',
    			align: {
			      selfXY: [0, 0],
			      baseElement: '#startDate_be',
			      baseXY: [0, '100%']
        		}
    		});
    		new Calendar({
    			trigger: '#endDate',
    			output: '#endDate_be',
    			align: {
			      selfXY: [0, 0],
			      baseElement: '#endDate_be',
			      baseXY: [0, '100%']
        		}
    		});
    	},
    	_queryParam:function(){
    		var _this = this;
    		
    		
    		
    		$(".order-list-table ul li").click(function(){
    			   $(".order-list-table ul li").each(function(){
    				   $(this).find('a').attr('class','');;
				   });
    			 
				   var id = $(this).find('a').attr('id');
				   //alert("-----"+id);
			       $(this).find('a').attr('class','current');
			       //
			       if(id == 'pay_id'){
			    	   $('#busiType_id').val("1");
			       }
			       if(id == 'charge_id'){
			    	   $('#busiType_id').val("2");
			       }
			       if(id == 'all_id'){
			    	   $('#busiType_id').val("");
			       }
			       //
			       _this._queryAccountBalanceDetailList(id);
			 
			});
    		
    		//
    		$("#search_button_id").click(function () {
				
				var busiTypeValue = $('#busiType_id').val();
				if(busiTypeValue == '1'){
					$("#pay_id").click();
				}
				if(busiTypeValue == '2'){
					$("#charge_id").click();
				}
				if(busiTypeValue == ''){
					$("#all_id").click();
				}
				
			});
    		
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
					
					var template = $.templates("#balanceDetailListTmpl");
					var htmlOut = template.render(data);
					//alert(data.result);
					$("#table_info_id_pay_id").html(htmlOut);
					
				}
			});
      	}
      	
    	
    });
    
    module.exports = BalanceDetailListPager
});

