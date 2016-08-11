define('app/jsp/balance/phonebook/phonebookmgr', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("optDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index'),
    Calendar = require('arale-calendar/1.1.2/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    require("valuevalidator/jquery.valuevalidator.js");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var PhoneBookMgrPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		//查询
            "click [id='HREF_ADD_TEL_GROUP']":"_showAddTelGroupWindow",
            "click [id='BTN_ADD_TEL_GROUP']": "_submitNewTelGroup"
        },
    	//重写父类
    	setup: function () {
    		PhoneBookMgrPager.superclass.setup.call(this);
    		this._init(); 
    	},
    	
    	_init: function(){
    		this._loadTelGroups();
    	},
    	
    	
    	/**
    	 * 显示对话框
    	 */
    	_showDialog:function(id){
    		$('.eject-mask').fadeIn(100);
    		$('#'+id).slideDown(200);
    	},
    	/**
    	 * 显示对话框(关闭时不关闭背景浮层)
    	 * type: 1 警告，2 正确， 3 错误
    	 */
    	_showMsgDialog:function(title,msg,type){
    		var icon = "";
    		if(type==1){
    			icon='warning';
    		}else if(type==2){
    			icon='success';
    		}else if(type==3){
    			icon='fail';
    		}
    		var msgDialog = Dialog({
				title: title,
				icon:icon,
				content: msg
			});
        	msgDialog.show();
    	},
    	/**
    	 * 隐藏对话框
    	 */
    	_hiddenDialog:function(id){
    		$('#'+id).parent().parent().find('div.eject-mask').fadeOut(100);
    		$('#'+id).slideUp(150);
    	},
    	_showAddTelGroupWindow: function(){
    		$("#TEL_GROUP_NAME").val("");
    		$("#LBL_ADD_TEL_GROUP").css('display','none');
    		this._showDialog("addTelGroupDiv");
    	},
    	
    	_submitNewTelGroup: function(){
    		var _this = this;
    		var validator = new $.ValueValidator();
    		validator.addRule({
				labelName: "通信录组",
				fieldName: "TEL_GROUP_NAME",
				getValue: function(){
					var v = $("#TEL_GROUP_NAME").val();
					return v;
				},
				fieldRules: {
					required: true,
					regexp:/^[\u4e00-\u9fa5\w-_]{2,20}$/,
				},
				ruleMessages: {
					required: "请输入通讯录组名称",
					regexp:"请输入长度为 2-20个字符的 汉字、字母、数字、“-”、“_”的组合"
				}
			});
			var res=validator.fireRulesAndReturnFirstError();
			if(res){
				$("#LBL_ADD_TEL_GROUP").show().find("#SPAN_ADD_TEL_GROUP_TIP").html(res);
				return;
			}else{
				$("#LBL_ADD_TEL_GROUP").hide().find("#SPAN_ADD_TEL_GROUP_TIP").html("");
			}
			ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "正在处理...",
				url: _base+"/account/phonebook/submitNewTelGroup",
				data: {
					userId: this.get("userId"),
					telGroupName: $.trim($("#TEL_GROUP_NAME").val())
				},
				success: function(data){
					$('.eject-mask').fadeOut(100);
					$('.eject-medium').slideUp(150);
					$("#TEL_GROUP_NAME").val("");
					_this._loadTelGroups();
				}
			});
    	},
    	//隐藏区域
    	_displayHideUI:function(id)  
    	{  
    	    var ui =document.getElementById(id);  
    	    ui.style.display="none";  
    	},
    	//显示区域
    	_displayShowUI:function(id)  
    	{  
    	    var ui =document.getElementById(id);  
    	    ui.style.display="";//display为空的话会好使，为block会使后边的空间换行  
    	},
    	_loadTelGroups: function(){
    		var _this = this;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "正在处理...",
				url: _base+"/account/phonebook/queryTelGroups",
				data: {
					userId: this.get("userId")
				},
				success: function(data){
					var d = data.data; 
					if(d!=null && d != undefined && d.length>0){
						var template = $.templates("#TelGroupImpl");
	                    var htmlOutput = template.render(d);
	                    $("#TBODY_TEL_GROUP").html(htmlOutput);
	                    _this.renderTelGroupList();
					}else{
						 $("#TBODY_TEL_GROUP").html("没有搜索到相关信息");
					}
					
				}
			});
    	},
    	renderTelGroupList: function(){
    		var _this = this;
    		$("[name='BTN_DEL_TEL_GROUP']").bind("click",function(){
    			var telGroupId =$(this).attr("telGroupId");
    			$("#deleteGroupId").val(telGroupId);
    			//_this._showDialog("deleteDialogDiv");
    			var msgDialog = Dialog({
    				title: "删除操作确认",
    				content: "确定要删除此通讯录组吗？",
    				okValue:"确定",
    				ok:function () {
    					_this._deleteTelGroup()
    				},
    				cancelValue: '取消',
    				cancel:function(){}
    			});
            	msgDialog.show();
    		});
    		
    		$("[name='BTN_MODIFY_TEL_GROUP']").bind("click",function(){
    			var telGroupId =$(this).attr("telGroupId");
    			$("#SPAN_TEL_GROUP_TEXT_"+telGroupId).hide();
    			$("#SPAN_TEL_GROUP_INPUT_"+telGroupId).show();
    			$(this).hide();
    		});
    		
    		$("[name='BTN_SAVE_TEL_GROUP']").bind("click",function(){
    			var telGroupId = $(this).attr("telGroupId");
    			var telGroupName = $.trim($("#INPUT_TEL_GROUP_"+telGroupId).val());
    			var regexp = /^[\u4e00-\u9fa5\w-_]{2,20}$/
    			if(telGroupName==""){
    				$("#name_error_"+telGroupId).css("display","block");
    				$("#name_error_"+telGroupId).html("<i class='icon-caret-up'></i>名称不能为空");
    				return ;
    			}else if(!regexp.test(telGroupName)){
    				$("#name_error_"+telGroupId).css("display","block");
    				$("#name_error_"+telGroupId).html("<i class='icon-caret-up'></i>请输入长度为 2-20个字符的 汉字、字母、数字、“-”、“_”的组合");
    				return ;
    			}else{
    				$("#name_error_"+telGroupId).css("display","none");
    				$("#name_error_"+telGroupId).html("");

    			}
    			_this.modifyTelGroup(telGroupId,telGroupName);
    		});
    	},
    	_searchPhoneBooksDetail(telGroupId,telGroupName){
    		var url = _base+"/account/phonebook/phonebookdetail?telGroupId="+telGroupId+"&telGroupName="+ encodeURIComponent(encodeURIComponent(telGroupName));
    		window.location.href = url;
    	},
    	_deleteTelGroup: function(){
    		var _this = this;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "正在处理...",
				url: _base+"/account/phonebook/deleteUcTelGroup",
				data: {
					userId: this.get("userId"),
					telGroupId: $("#deleteGroupId").val()
				},
				success: function(data){
					_this._hiddenDialog("deleteDialogDiv");
					_this._loadTelGroups();
				}
			});
    	},
    	
    	modifyTelGroup: function(telGroupId,telGroupName){
    		var _this = this;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "正在处理...",
				url: _base+"/account/phonebook/modifyUcTelGroup",
				data: {
					userId: this.get("userId"),
					telGroupId: telGroupId,
					telGroupName: telGroupName
				},
				success: function(data){
					_this._loadTelGroups();
				}
			});
    	}
    	
    	
    	
    });
    
    module.exports = PhoneBookMgrPager
});

