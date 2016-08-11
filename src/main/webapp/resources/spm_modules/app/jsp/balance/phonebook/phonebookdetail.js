define('app/jsp/balance/phonebook/phonebookdetail', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("optDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    Uploader = require('arale-upload/1.2.0/index'),
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
    var PhoneBookDetailPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 50
    	},
    	//事件代理
    	events: {
    		//查询
            "click [id='BTN_QUERY']":"_queryPhoneBooks",
            "click [id='BTN_REFRESH']":"_queryPhoneBooks",
            "click [id='BTN_DELETE']":"_checkDeleteData",
            //"click [id='deletePhone']":"_deletePhoneBooks",
            "click [id='BTN_BATCH_IMPORT']":"_showBatchImportWindow",
            "click [id='BTN_ADD_PHONEBOOK']":"_showAddPhoneBookWindow",
            "click [id='CHECK_ALL']":"_checkAll",
            "click [id='HREF_ADD_ONE']":"_addOnePhoneBook",
            "click [id='HREF_ADD_BATCH']":"_showAddBatchPhoneBook",
            "click [id='BTN_INPUT_ROW']":"_confirmInputRow",
            "click [id='BTN_SAVE_BATCH_ADD']":"_submitBatchSaveEdit",
            "change [id='uploadFile']":"_setUploadFile",
            "click [id='uploadBtn']":"_uploadFile"
            	
        },
    	//重写父类
    	setup: function () {
    		PhoneBookDetailPager.superclass.setup.call(this);
    		this._initProvices();
    		this._initBasicOrgs();
    		this._queryPhoneBooks();
    	},
    	/**
    	 * 初始化归属地下拉框
    	 */
    	_initProvices: function(){
    		var _this = this;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "正在处理...",
				url: _base+"/account/phonebook/getProvices", 
				success: function(data){
					var arr = data.data?data.data:[];
					$.each(arr,function(i,d){
						$("#provinceCode").append("<option value='"+ d.areaCode+"'>"+d.areaName+"</option>");
					});
				}
			});
    	},
    	/**
    	 * 初始化运营商下拉框
    	 */
    	_initBasicOrgs: function(){
    		var _this = this;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "正在处理...",
				url: _base+"/account/phonebook/getBasicOrgs", 
				success: function(data){
					var arr = data.data?data.data:[];
					$.each(arr,function(i,d){
						$("#basicOrgId").append("<option value='"+ d.columnValue+"'>"+d.columnDesc+"</option>");
					});
				}
			});
    	},
    	
    	_setUploadFile: function(){
    		var fileName = $("#uploadFile").val();
    		$("#TEXT_FILE_NAME").val(fileName);
    	},
    	/**
    	 * 显示对话框
    	 */
    	_showDialog:function(id){
    		$('.eject-mask').fadeIn(100);
    		$('#'+id).slideDown(200);
    	},
    	/**
    	 * 显示对话框
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
				content: msg,
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
    	/**
    	 * 隐藏添加多行提示框
    	 */
    	_hiddenAddNumDialog: function(){
    		this._hiddenDialog('addMoreDialogDiv');
    		$("#INPUT_ROW").val("");
    	},
    	/**
    	 * 隐藏添加弹出框
    	 */
    	_hiddenAddDialog: function(){
    		this._hiddenAddNumDialog();
    		this._hiddenDialog('addDialogDiv');
    	},
    	/**
    	 * 检查上传文件
    	 */
    	_checkUploadFile: function(){
    		var fileName = $("#uploadFile").val();
    		if(fileName==""){
    			this._showMsgDialog("提示","请选择文件",1);
    			//alert("请选择文件");
    			return ;
    		}
    		var FileListType="xls,xlsx";
    		var destStr = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)
    		if(FileListType.indexOf(destStr) == -1){
    		  //alert("只允许上传EXCEL文件。格式支撑xls,xlsx");
    		  this._showMsgDialog("提示","只允许上传EXCEL文件。格式支撑xls,xlsx",1);
    		  return false;
    		} 
    		return true;
    	},
    	/**
    	 * 上传文件
    	 */
		_uploadFile:function(){
			var _this = this;
			
			var valid = _this._checkUploadFile();
			if(!valid){
				$("#uploadFile").val("");
				$("#TEXT_FILE_NAME").val("");
				return ;
			}
			//初始化进度条数据
			this._showDialog("uploadProgressDiv");
			
			var form = new FormData();
		    form.append("uploadFile", document.getElementById("uploadFile").files[0]); 
			
			// XMLHttpRequest 对象
		     var xhr = new XMLHttpRequest();
		     //xhr.upload.addEventListener("progress", function () { _this._uploadProgress(event) }, false);
		     xhr.addEventListener("load", function (result,loadingDialog) { _this._uploadSuccess(result,loadingDialog) }, false); 
		     xhr.addEventListener("error", function (loadingDialog) { _this._uploadFail(loadingDialog) }, false);
		     var uploadURL = _base+"/account/phonebook/uploadPhoneBooks?telGroupId="+this.get("telGroupId");
		     xhr.open("post", uploadURL, true);
		     
			 xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {// 4 = "loaded"
					if (xhr.status == 200) {
						var responseData = $.parseJSON(xhr.response);
						_this._hiddenDialog("uploadFileDiv");
						if(responseData.statusCode=="1"){
							//alert(responseData.data);
							_this._queryPhoneBooks();
						}else{
							_this._showMsgDialog("错误","上传失败",3);
						}
					}  
				}
			 };
			xhr.send(form);
		},
		
//		/**
//		 * 上传进度
//		 */
//		_uploadProgress:function(evt) {
//			if (evt.lengthComputable) {
//				var percentComplete = Math.round(evt.loaded * 100 / evt.total);
//				document.getElementById('uploadProgressShow').style.width = percentComplete+'%';
//				$("#uploadProgressMsg").html("已处理"+percentComplete+"%");
//			}else {
//				document.getElementById('uploadProgressShow').style.width = '100%';
//				$("#uploadProgressMsg").html("已处理100%");
//			}
//		},
		/**
		 * 上传成功
		 */
		_uploadSuccess:function(result){
			var responseStr = result.currentTarget.response;
			var value = JSON.parse(responseStr).data;
			this._hiddenDialog("uploadProgressDiv");
			this._showMsgDialog("批量导入通讯录","导入通讯录完成！<p>共<span>"+value.totalCount+"</span>条数据；成功导入<span>"+value.successCount+"</span>条；失败<span>"+value.failCount+"</span>条；</p>", 2);
		},
		/**
		 * 上传失败
		 */
		_uploadFail:function(){
			this._hiddenDialog("uploadFileDiv");
			this._hiddenDialog("uploadProgressDiv");
			this._showMsgDialog("批量导入通讯录","导入通讯录失败！", 3);
		},
    	/**
    	 * 删除编辑框中一条数据
    	 */
    	_delBatchEditRow: function(index){
    		var data = this.batcheditdata?this.batcheditdata:[];
    		if(data.length-1>=index){
    			data.splice(index,1);
    		}
    		this.renderBatchEditPhoneBooks(data);
    	},
    	/**
    	 * 添加多条数据
    	 */
    	_confirmInputRow: function(){
    		var row = $.trim($("#INPUT_ROW").val());
    		var rowRE = /^([2-9]|[1-9][0-9]|100)$/;
    		if(!rowRE.test(row)){
    			$("#addDialogDiv_error").html("<img src='"+_slpbase+"/images/icon-a.png'> 请输入数字[2~100]");
    			return;
    		}
    		
    		$("#addDialogDiv_error").html("");
    		
    		var data = this.batcheditdata?this.batcheditdata:[];
    		for(var i=0;i<row;i++){
    			data.push({
        			telName: "",
        			telMp: ""
        		});
    		}
    		$("#INPUT_ROW").val("");
    		this._hiddenDialog("addMoreDialogDiv");
    		this.renderBatchEditPhoneBooks(data);
    	},
    	/**
    	 * 添加一条数据
    	 */
    	_addOnePhoneBook: function(){
    		var data = this.batcheditdata?this.batcheditdata:[];
    		data.push({
    			telName: "",
    			telMp: ""
    		});
    		this.renderBatchEditPhoneBooks(data);
    	},
    	
    	renderBatchEditPhoneBooks: function(data){
    		var _this=this;
    		var template = $.templates("#PhoneBooksBatchEditImpl");
            var htmlOutput = template.render(data);
            $("#TBODY_PhoneBooksBatchEdit").html(htmlOutput);
            
            $("[name='DEL_BATCH_EDIT_ROW']").bind("click",function(){
    			var index = $(this).attr("index");
    			_this._delBatchEditRow(index);
    		});
            $("[name='BATCH_TEL_NAME']").bind("blur",function(){
            	var telName=$(this).val();
            	var index = $(this).attr("index");
            	_this._updateTelNameBatchEditData(index,telName);
            });
            
            $("[name='BATCH_TEL_MP']").bind("blur",function(){
            	var telMap=$(this).val();
            	var index = $(this).attr("index");
            	_this._updateTelMpBatchEditData(index,telMap);
            });
    	},
    	/**
    	 * 更新联系人姓名
    	 */
    	_updateTelNameBatchEditData: function(index,telName){
    		var _this = this;
    		var arr=$.grep(_this.batcheditdata,function(d,i){
    			return i==index;
    		});
    		if(!arr || arr.length==0){
    			return;
    		}
    		arr[0].telName = telName;
    	},
    	/**
    	 * 更新手机号信息
    	 */
    	_updateTelMpBatchEditData: function(index,telMp){
    		var _this = this;
    		var arr=$.grep(_this.batcheditdata,function(d,i){
    			return i==index;
    		});
    		if(!arr || arr.length==0){
    			return;
    		}
    		arr[0].telMp = telMp;
    	},
    	/**
    	 * 添加联系人-添加多条
    	 */
    	_showAddBatchPhoneBook: function(){
    		$("#addDialogDiv_error").html("");
    		this._showDialog("addMoreDialogDiv");
    	},
    	
    	_checkAll: function(){ 
    		if ($("#CHECK_ALL").prop("checked")) {
    			$("input[name='CHEK_TEL_NO']").prop("checked", true);  
    	    } else {  
    	    	$("input[name='CHEK_TEL_NO']").prop("checked", false);  
    	    }
    	},
    	/**
    	 * 显示导入窗口时处理事件
    	 */
    	_showBatchImportWindow: function(){
    		$("#uploadFile").val("");
			$("#TEXT_FILE_NAME").val("");
			
			this._showDialog("uploadFileDiv");
    	},
    	/**
    	 * 显示添加窗口时的处理事件
    	 */
    	_showAddPhoneBookWindow: function(){
    		this.batcheditdata = [];
    		this.batcheditdata.push({
    			telName: "",
    			telMp: ""
    		});
    		this.renderBatchEditPhoneBooks(this.batcheditdata);
    		$("#INPUT_ROW").val("");
    		
    		this._showDialog("addDialogDiv");
    	},
    	/**
    	 * 弹窗添加联系人的保存事件
    	 */
    	_submitBatchSaveEdit: function(){ 
    		var _this = this;
    		var arr = this.batcheditdata?this.batcheditdata:[];
    		if(arr.length==0){
    			this._showMsgDialog("提示","没有需要保存的通信录",1);
    			return ;
    		}
    		
    		var validPass= true;
    		$.each(arr,function(i,o){
    			var telName = $.trim(o.telName);
    			var telMp =$.trim(o.telMp);
    			var onepass=true;
    			var errormsg = "";
    			if(!_this.checkBlank(telName)){
    				var length = telName.length;
    				if(length > 24){
    					errormsg +=" 姓名长度为1-24个字符;\n";
    					onepass = false;
    				}
    			}
    			if(_this.checkBlank(telMp)){
    				errormsg +=" 请输入手机号码;\n";
    				onepass = false;
    			}else if(!_this.checkMobilePhone(telMp)){
    				errormsg +=" 手机号码格式有误;\n";
    				onepass = false;
    			}
    			if(onepass){
    				o.error = "";
    				errormsg = "";
    				$("#SPAN_ERROR_"+i).html("");
    			}else{
    				validPass = false;
    				$("#SPAN_ERROR_"+i).html("<img src='"+_slpbase+"/images/icon-a.png'>"+errormsg);
    			}
    			o.telGroupId = _this.get("telGroupId");
    		});
    		if(!validPass){
    			return ;
    		}
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "正在处理...",
				url: _base+"/account/phonebook/batchAddUserPhonebooks",
				data: {
					datas: JSON.stringify(arr)
				},
				success: function(data){
					var resultValue = data.data;
					_this._hiddenDialog("addDialogDiv");
					_this._queryPhoneBooks();
					_this._showMsgDialog("添加联系人","添加操作完成！<p>共<span>"+resultValue.totalCount+"</span>条数据；成功添加<span>"+resultValue.successCount+"</span>条；失败<span>"+resultValue.failCount+"</span>条；</p>", 2);
				}
			});
    		
    	},
    	/**
    	 * 判空
    	 */
    	checkBlank: function(value){
    		if(value == null || value == undefined){
    			return true;
    		}
    		var v = $.trim(value);
    		return v==""?true:false;
    	},
    	/**
    	 * 检查手机格式
    	 */
    	checkMobilePhone: function(value){
    		var re = /^1\d{10}$/;
			var valid =  (re.test(value))?true:false;	
			return valid;
    	},
    	/**
    	 * 删除前检查
    	 */
    	_checkDeleteData:function(){
    		var _this = this;
    		var checkboxs=$("input[name='CHEK_TEL_NO']:checked");
    		if(checkboxs.length==0){
    			this._showMsgDialog("提示","请选择要删除的联系人",1);
    			return;
    		}
    		//this._showDialog("deleteDialogDiv")
    		var msgDialog = Dialog({
				title: "删除操作确认",
				content: "确定要删除已选联系人吗？",
				okValue:"确定",
				ok:function () {
					_this._deletePhoneBooks()
				},
				cancelValue: '取消',
				cancel:function(){}
			});
        	msgDialog.show();
    	},
    	/**
    	 * 删除联系人
    	 */
    	_deletePhoneBooks: function(){
    		var _this = this;
    		var recordIds = "";
    		var checkboxs=$("input[name='CHEK_TEL_NO']:checked");
    		$.each(checkboxs,function(i,o){
    			var telNo = $(o).val();
    			recordIds+=telNo+","
    		});
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "正在处理...",
				url: _base+"/account/phonebook/batchDeleteUserPhonebooks",
				data: {
					recordIds: recordIds
				},
				success: function(data){
					//alert("删除成功"); 
					_this._hiddenDialog("deleteDialogDiv");
					_this._showMsgDialog("提示","删除成功!",2);
					_this._queryPhoneBooks();
				},
				failure: function(){
					_this._hiddenDialog("deleteDialogDiv");
					_this._showMsgDialog("错误","删除失败!",3);
				}
			});
    		
    	},
    	/**
    	 * 查询通讯组中的数据
    	 */
    	_queryPhoneBooks: function(){
    		var _this = this;
    		$("#CHECK_ALL").prop("checked", false);
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/account/phonebook/queryUserPhonebooks",
	 			method: "POST",
	 			dataType: "json",
	 			renderId:"TBODY_PHONEBOOKS",
	 			messageId:"showMessageDiv",
	            data : {
					//userId: this.get("userId"),
					telGroupId: this.get("telGroupId"),
					provinceCode: $("#provinceCode").val(),
					basicOrgId: $("#basicOrgId").val(),
					telName: $("#telName").val(),
					telMp: $("#telMp").val()
				},
	           	pageSize: PhoneBookDetailPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	            	var count = data.count;
	            	$("#phoneCount").text(count);
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#PhoneBooksImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_PHONEBOOKS").html(htmlOutput);
	            	}
	            }
    		}); 
    	},
    	/**
    	 * 编辑按钮事件
    	 */
    	_modifyTelData:function(telNo,telName,telMp){
    		$("#telName_"+telNo).html(
    				"<input id='telName_val_"+telNo+"' type='text' class='table-int-mini' value='"+telName+"' maxLength='24'>" +
    				"<div id='modify_name_error_"+telNo+"' class='ejecr-pos-border' style='display: none;'>" +
    				"<i class='icon-caret-up'></i></div>");
    		$("#telMp_"+telNo).html(
    				"<input id='telMp_val_"+telNo+"' type='text' class='table-int-mini' value='"+telMp+"' maxLength='11'><input type='button' class='mail-btn' value='保存' onclick=\"pager._saveModifyTelData('"+telNo+"')\">" +
    				"<div id='modify_mp_error_"+telNo+"' class='ejecr-pos-border' style='display: none;'>" +
					"<i class='icon-caret-up'></i></div>");
    	},
    	/**
    	 * 修改联系人
    	 */
    	_saveModifyTelData:function(telNo){
    		var _this = this;
    		var telName = $("#telName_val_"+telNo).val();
    		var telMp = $("#telMp_val_"+telNo).val();
    		var isOk = this._checkTelData(telNo,telName,telMp);
    		if(isOk){
    			ajaxController.ajax({
    				type: "post",
    				dataType: "json",
    				processing: true,
    				message: "正在处理...",
    				url: _base+"/account/phonebook/modifyPhonebook",
    				data: {
    					telGroupId: this.get("telGroupId"),
    					telNo:telNo,
    					telName:telName,
    					telMp:telMp
    				},
    				success: function(data){
    					//_this._showMsgDialog("提示","删除成功!",2);
    					_this._queryPhoneBooks();
    				}
    			});
    		}
    	},
    	/**
    	 * 检查联系人信息
    	 */
    	_checkTelData:function(telNo,telName,telMp){
    		var isSuccess = true;
    		if(telName != null || telName != undefined && telName != ""){
    			var nameSize = telName.length;
    			if(nameSize>24){
    				$("#modify_name_error_"+telNo).html("<i class='icon-caret-up'></i>姓名长度为1-24个字符");
    				$("#modify_name_error_"+telNo).css("display","block");
    				isSuccess = false;
    			}else{
    				$("#modify_name_error_"+telNo).html("");
    				$("#modify_name_error_"+telNo).css("display","none");
    			}
    		}else{
    			$("#modify_name_error_"+telNo).html("");
    			$("#modify_name_error_"+telNo).css("display","none");
    		}
    		if(telMp == null || telMp == undefined || telMp == ""){
    			$("#modify_mp_error_"+telNo).html("<i class='icon-caret-up'></i>手机号不能为空");
    			$("#modify_mp_error_"+telNo).css("display","block");
    			isSuccess = false;
    		}else{
    			var telIsOk = this.checkMobilePhone(telMp);
    			if(!telIsOk){
    				$("#modify_mp_error_"+telNo).html("<i class='icon-caret-up'></i>手机号格式不正确");
    				$("#modify_mp_error_"+telNo).css("display","block");
    				isSuccess = false;
    			}else{
    				$("#modify_mp_error_"+telNo).html("");
    				$("#modify_mp_error_"+telNo).css("display","none");
    			}
    		}
    		return isSuccess;
    	}
    	
    });
    
    module.exports = PhoneBookDetailPager
});

