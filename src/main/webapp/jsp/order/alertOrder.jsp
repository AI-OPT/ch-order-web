<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>查询预警订单列表</title>
<%@include file="/inc/inc.jsp" %>
</head>
<body>
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
        <!--框架标签结束-->
      <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化-->
	                    <div class="main-box clearfix"><!--白色背景-->
	                    	<!--查询条件-->
	                    	<div class="form-label">
					           <ul>
					                <li class="col-md-4" id="dateDiv1">
											<p class="word">开始时间</p>
											<p>
												<input class="int-text int-medium " readonly
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'orderTimeEnd\')}'})"
													id="orderTimeBegin" name="control_date" /> <span class="time">
													<i class="fa  fa-calendar"></i>
												</span>
											</p>
										</li>
										<li class="col-md-4" id="dateDiv2">
											<p class="word">结束时间</p>
											<p>
												<input class="int-text int-medium " readonly
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'orderTimeBegin\')}'})"
													id="orderTimeEnd" name="control_date" /> <span class="time"><i class="fa  fa-calendar"></i></span>
											</p>
										</li>
					                <li class="col-md-4">
					            		<p><input type="button" class="biu-btn btn-primary btn-blue btn-mini" value="查询" id="search"></p>
					            	</li> 
					            </ul> 
					         </div>
					   	<!--查询结束-->      
	         			</div>
	                </div>
              </div>
         </div>
     </div>	
   	  	<div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        <!--标题-->
                            <header class="main-box-header clearfix">
                            <h5 class="pull-left">预警订单列表</h5>
                            </header>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
                                   	<!--table表格-->
                          		<div class="table-responsive clearfix mt-10">
                                    <table class="table table-hover table-border table-bordered ">
                                        <thead>
                                            <tr>
                                            	<th>订单来源</th>
                                                <th>订单号</th>
                                                <th>用户名</th>
                                                <th>绑定手机号</th>
                                                <th>是否预警订单</th>
                                                <th>预警类型</th>
                                                <th>收货人手机号</th>
                                                <th>是否需要物流</th>
                                                <th>下单时间</th>
                                                <th>商品信息</th>
                                                <th>数量</th>
                                                <th>订单状态</th>
                                                <th>详情</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                         <tbody id="alertData"></tbody>
                                    </table>
                                    <div id="showMessageDiv"></div>
                                </div>
                           		<!--/table表格结束-->
                                </div>
                                <!--分页-->
								 <div>
					 				 <nav style="text-align: center">
										<ul id="pagination">
										</ul>
									</nav>
								  </div>
								<!--分页结束-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
<script id="alertTemple" type="text/template">
			{{if ordextendes!=null}}
						{{for ordextendes ~orderData = #data}}  
							<!-- 商品 -->
								{{for prodinfos ~parentProdSize=prodsize ~cOrderId=orderid 
									~busiCode=busicode ~state=state ~stateName=statename
									~parentInd = #index ~parentOrder =~orderData }}	
        						<tr>
								{{if ~parentInd == 0 && #index ==0}}
									<td rowspan="{{:~parentOrder.totalprodsize}}">{{:~parentOrder.chlidname}}</td>
		   							<td rowspan="{{:~parentOrder.totalprodsize}}">{{:~parentOrder.porderid}}</td>
									<td rowspan="{{:~parentOrder.totalprodsize}}">{{:~parentOrder.username}}</td>
									<td rowspan="{{:~parentOrder.totalprodsize}}">{{:~parentOrder.usertel}}</td>
									<td rowspan="{{:~parentOrder.totalprodsize}}">{{:~parentOrder.ifwarning}}</td>
									<td rowspan="{{:~parentOrder.totalprodsize}}">{{:~parentOrder.warningtype}}</td>
		   							<td rowspan="{{:~parentOrder.totalprodsize}}">{{:~parentOrder.contacttel}}</td>
									<td rowspan="{{:~parentOrder.totalprodsize}}">{{:~parentOrder.deliveryflagname}}</td>
									<td rowspan="{{:~parentOrder.totalprodsize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~parentOrder.ordertime)}}</td>
								{{/if}}

								<td title="{{:prodname}}">{{:~subStr(10,prodname)}}</td>	
								<td >{{:buysum}}</td>
								{{if #index ==0}}
									<td rowspan="{{:~parentProdSize}}">{{:~stateName}}</td>
									<td rowspan="{{:~parentProdSize}}"><a href="javascript:void(0);" onclick="pager._detailPage('{{:~parentOrder.porderid}}')">订单详情</a></td>
      							    <td rowspan="{{:~parentProdSize}}"><a href="javascript:void(0);" onclick="pager._closeOrder('{{:~parentOrder.porderid}}')">关闭订单</a></td>
								{{/if}}
        					</tr>
        			{{/for}}
				{{/for}}
			{{/if}}	
 </script> 
  <script type="text/javascript">
	<%-- 展示日历 --%>
	$('#dateDiv1').delegate('.fa-calendar', 'click', function() {
		var calInput = $(this).parent().prev();
		var timeId = calInput.attr('id');
		WdatePicker({
			el : timeId,
			readOnly : true,
			dateFmt : 'yyyy-MM-dd',
			isShowClear : true,
			maxDate : '#F{$dp.$D(\'orderTimeEnd\')}'
		});
	});
	$('#dateDiv2').delegate('.fa-calendar', 'click', function() {
		var calInput = $(this).parent().prev();
		var timeId = calInput.attr('id');
		WdatePicker({
			el : timeId,
			readOnly : true,
			dateFmt : 'yyyy-MM-dd',
			isShowClear : true,
			minDate : '#F{$dp.$D(\'orderTimeBegin\')}'
		});
	});
			var pager;
			(function () {
				seajs.use('app/jsp/order/alertOrder', function (alertOrderPager) {
					pager = new alertOrderPager({element: document.body});
					pager.render();
				});
			})();
 </script>  
</body>

</html>