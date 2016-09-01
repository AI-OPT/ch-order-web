<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>查询列表</title>
<%@include file="/inc/inc.jsp" %>
</head>
<body>
     <!--框架标签结束-->
      <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化-->
	                    <div class="main-box clearfix"><!--白色背景-->
	                    	<div class="main-box-body clearfix">
									<!--选项卡1-->
									<div class="order-list-table">
							           <input id="searchOrderState" value="" style="display:none"/>
							           <ul>
								            <li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this)" class="current">全部</a></li>
          									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'11')">待付款订单</a></li>
          									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'13,14,15')">待发货订单</a></li>
          									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'16')" style="border-right-style: none;" >已发货订单</a></li>
          									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'90')" style="border-right-style: none;" >已完成订单</a></li>
           									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'91')" style="border-right-style: none;" >已关闭</a></li>
							           </ul>                                        
							     	</div> 	
					   		</div> 
	                    	<!--查询条件-->
	                    	<div class="form-label">
					           <ul>
					                <li class="col-md-6">
					                    <p class="word">开始时间</p>
					                    <p><input name="control_date" class="int-text int-medium " type="text"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'orderTimeEnd\')}'})" id="orderTimeBegin" name="orderTimeBegin"/>
					                   <span class="time"> <i class="fa  fa-calendar" ></i></span>
					                    </p>
					                </li>
					                <li class="col-md-6">
					                    <p class="word">结束时间</p>
					                    <p><input name="control_date" class="int-text int-medium " type="text"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'orderTimeBegin\')}'})" id="orderTimeEnd" name="orderTimeEnd"/>
					                     <span class="time"><i class="fa  fa-calendar" ></i></span>
					                    </p>
					                </li>  
					            </ul> 
								<ul>
								 	<li class="col-md-6">
					                   	<p class="word" >关键字</p>
					                    <p><input  class="int-text int-medium"id="orderId" name="orderId" placeholder="请输入父订单号查询" type="text"/>
					                    </p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                    <p><input type="button" class="biu-btn btn-primary btn-blue btn-mini" value="搜索" id="search"></p>
					                </li>
					                <li >
					                	<p> 高级搜索<img id="showQuery" src="${uedroot}/images/daosanjiao.png"/></p>
					                </li>
					            </ul>
					            <div id="queryInfo" style="display:none">
						            <ul>
						            <li class="col-md-4">用户名&nbsp;<input class="int-text int-medium" id="username" type="text" placeholder="请输入用户名" ></li>
						            	<li class="col-md-4">
						            		订单来源
						            		<select class="select select-medium" id="orderSource">
						            			<option value="">请选择</option>
						            		</select>
						            	</li>
						            	<li class="col-md-4">
							            	仓库
							            	<select class="select select-medium" id="routeSource">
							            			<option value="">请选择</option>
							            			<option>仓库1</option>
							            			<option>仓库2</option>
							            			<option>仓库3</option>
							            	</select>
						            	</li>
						            </ul>
						             <ul>
						            	<li class="col-md-4">收货人手机号&nbsp;<input class="int-text int-medium" id="contactTelQ" type="text" placeholder="请输入收货人手机号" ></li>
						            	<li class="col-md-4">
						            		是否需要物流
						            		<select class="select select-small" id="deliveryFlag">
						            			<option value="">请选择</option>
						            		</select>
						            	</li>
						            	<li> <p><input type="button" class="biu-btn btn-primary btn-blue btn-mini" id="moreSearch" value="搜索"></p></li>
						            </ul>
					            </div>
					         </div>
					   	<!--查询结束-->      
	         			</div>
	                </div>
              </div>
         </div>
     </div>	
     <!--框架标签结束-->
  <script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/order/commonDetail', function (commonPager) {
					pager = new commonPager({element: document.body});
					pager.render();
				});
			})();
 </script>   
</body>
</html>