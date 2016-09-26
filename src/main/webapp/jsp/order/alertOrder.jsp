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
	                    	<div class="form-label" id="selectDiv">
					           <ul>
					                <li class="col-md-4">
					                    <p class="word">开始时间</p>
					                    <p><input name="control_date" readonly class="int-text int-medium " type="text"  id="orderTimeBegin" name="orderTimeBegin"/>
					                   <span class="time"> <i class="fa  fa-calendar" ></i></span>
					                    </p>
					                </li>
					                <li class="col-md-4">
					                    <p class="word">结束时间</p>
					                    <p><input name="control_date" class="int-text int-medium " type="text"  id="orderTimeEnd" name="orderTimeEnd"/>
					                     <span class="time"><i class="fa  fa-calendar" ></i></span>
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
                            <h4 class="pull-left">查询结果</h4>
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
                                                <th>用户账号</th>
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
<tr>
    	<td>{{:chlId}}</td>
        <td>{{:orderId}}</td>
		<td class="hind1">
			<div class="center-hind" >{{:userName}}</div>
        	<div class="showbj"><i class="fa fa-posi fa-caret-up"></i>{{:userName}}</div>
		</td>
        <td></td>
        <td>{{:ifWarning}}</td>
		<td>{{:warningType}}</td>
        <td>{{:userTel}}</td>
        <td>{{:deliveryFlag}}</td>
		<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', orderTime)}}</td>
        <td>
        	 <table class="table close-border" width="100%">
        		<tbody>
					{{if prodInfo!=null}}
						{{for prodInfo}}
        					<tr>
								<td class="hind1 new-td">
									<div class="center-hind" >{{:prodName}}</div>
                                    <div class="showbj"><i class="fa fa-posi fa-caret-up"></i>{{:prodName}}</div>
								</td>
        					</tr>
						{{/for}}
					{{/if}}
        		</tbody>	
        	</table>
        </td>
       <td>
        	 <table class="table close-border" width="100%">
        		<tbody>
					{{if prodInfo!=null}}
						{{for prodInfo}}
        					<tr>
        						<td class="new-td">{{:buySum}}</td>	
        					</tr>
						{{/for}}
					{{/if}}
        		</tbody>	
        	</table>
        </td>
        <td>{{:state}}</td>
         <td><a  href="javascript:void(0);" onclick="pager._detailPage('{{:orderId}}')">订单详情</a></td>
         <td><a  href="javascript:void(0);" onclick="pager._closeOrder('{{:orderId}}')">关闭订单</a></td>
    </tr>
 </script> 
  <script type="text/javascript">
  <%-- 展示日历 --%>
	$('#selectDiv').delegate('.fa-calendar','click',function(){
		var calInput = $(this).parent().prev();
		var timeId = calInput.attr('id');
		console.log("click calendar "+timeId);
		WdatePicker({el:timeId,readOnly:true});
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