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
          									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'11')" id="waitMoney">待付款订单</a></li>
          									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'13,14,15')">待发货订单</a></li>
          									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'16')" style="border-right-style: none;" id="alreadySend">已发货订单</a></li>
          									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'90')" style="border-right-style: none;" >已完成订单</a></li>
           									<li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'91')" style="border-right-style: none;" >已关闭</a></li>
							           </ul>                                        
							     	</div> 	
					   		</div> 
	                    	<!--查询条件-->
	                    	<form id="dataForm" method="post" >
		                    	<div class="form-label"  id="dateDiv">
						           <ul>
						                <li class="col-md-6">
						                    <p class="word">开始时间</p>
						                    <p><input class="int-text int-medium " readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'orderTimeEnd\')}'})" id="orderTimeBegin" name="control_date" id="orderTimeBegin" />
						                   <span class="time"> <i class="fa  fa-calendar" ></i></span>
						                    </p>
						                </li>
						                <li class="col-md-6">
						                    <p class="word">结束时间</p>
						                    <p><input class="int-text int-medium " readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'orderTimeBegin\')}'})" id="orderTimeEnd" name="control_date"/>
						                     <span class="time"><i class="fa  fa-calendar" ></i></span>
						                    </p>
						                </li>  
						            </ul> 
									<ul>
									 	<li class="col-md-6">
						                   	<p class="word" >关键字</p>
						                    <p><input  class="int-text int-medium" id="orderId" name="orderId" placeholder="请输入父订单号查询" type="text"/>
						                    </p>
						                	<p>高级搜索<a href="javascript:void(0);"><i class="fa fa-caret-down" id="showQuery"></i></a></p>
						                </li>
						            </ul>
						            <div  id="selectDiv" style="display:none" >
							            <ul>
								            <li class="col-md-6">
									            <p class="word">用户名</p>
									            <p><input class="int-text int-medium" id="username" type="text" placeholder="请输入用户名" ></p>
								            </li>
							            	<li class="col-md-6">
								            	<p class="word">订单来源</p>
							            		<p>
								            		<select class="select select-medium" id="orderSource">
								            			<option value="">请选择</option>
								            		</select>
							            		</p>
							            	</li>
							            </ul>
							            <ul>
							            	<li class="col-md-6">
								            	<p class="word">仓库</p>
								            	<p>
									            	<select class="select select-medium" id="routeSource">
									            			<option value="">请选择</option>
									            	</select>
								            	</p>
							            	</li>
							            	<li class="col-md-6">
								            	<p class="word">收货人手机号</p>
								            	<p>
								            		<input class="int-text int-medium" id="contactTelQ" type="text" placeholder="请输入收货人手机号" >
								            	</p>
							            	</li>
							            </ul>
							             <ul>
							            	
							            	<li class="col-md-6">
								            	<p class="word">是否需要物流</p>
							            		<p>
								            		<select class="select select-small" id="deliveryFlag">
								            			<option value="">请选择</option>
								            		</select>
							            		</p>
							            	</li>
							            </ul>
						            </div>
						            <ul>
										<li class="width-xlag">
											<p class="word">&nbsp;</p>
											<p><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-10"
													  id="search" value="查  询"></p>
										</li>
									</ul>
						         </div>
					         </form>
					   	<!--查询结束-->      
	         			</div>
	                </div>
              </div>
         </div>
     </div>	
     <!--框架标签结束-->
  <script type="text/javascript">
	  <%-- 展示日历 --%>
		$('#dateDiv').delegate('.fa-calendar','click',function(){
			var calInput = $(this).parent().prev();
			var timeId = calInput.attr('id');
			WdatePicker({el:timeId,readOnly:true});
		});
		
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