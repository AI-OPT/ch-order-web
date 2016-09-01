<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>查询售后列表</title>
</head>
<body>
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
   <%@include file="/jsp/order/commonDetail.jsp" %><!-- 公共查询条件 -->
   	  	<div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        <!--标题-->
                            <header class="main-box-header clearfix">
                            <h2 class="pull-left">查询结果</h2>
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
                                                <th>父订单号</th>
                                                <th>用户账号</th>
                                                <th>绑定手机号</th>
                                                <th>收货人手机号</th>
                                                <th>积分抵扣</th>
                                                <th>优惠</th>
                                                <th>实付款</th>
                                                <th>是否需要物流</th>
                                                 <th>子订单号</th>
                                                <th>商品信息</th>
                                                <th>订单状态</th>
                                                <th>详情</th>
                                            </tr>
                                        </thead>
	                                     <tbody id="paidData"></tbody>
                                    </table>
                                </div>
                           <!--/table表格结束-->
                                </div>
                                <!--分页-->
								 <div>
					 				 <nav style="text-align: right">
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
   	<script id="paidTemple" type="text/template">
				<tr>
		   				<td>{{:chlId}}</td>
		   				<td>{{:pOrderId}}</td>
						<td>{{:userId}}</td>
						<td>XXXXX</td>
		   				<td>{{:contactTel}}</td>
						<td>{{:jf}}</td>
						<td>{{:discountFee}}</td>
						<td>{{:~liToYuan(adjustFee)}}</td>
						<td>{{:deliveryFlagName}}</td>
						<td>
        	 				<table class="table close-border" width="100%">
        						<tbody>
									{{if orderList!=null}}
										{{for orderList}}
        									<tr>
												<td class="new-td" style="">{{:orderId}}</td>
        									</tr>
										{{/for}}
									{{/if}}
        						</tbody>	
        					</table>
        				</td>
						<td>
        	 				<table class="table close-border" width="100%">
        						<tbody>
									{{if orderList!=null}}
										{{for orderList}}
											{{if productList!=null}}
											{{for productList}}
        										<tr>
													<td class="new-td" style="">{{:prodName}}</td>
        										</tr>
											{{/for}}
											{{/if}}
										{{/for}}
									{{/if}}
        						</tbody>	
        					</table>
        				</td>
						<td>
        	 				<table class="table close-border" width="100%">
        						<tbody>
									{{if orderList!=null}}
										{{for orderList}}
        									<tr>
												<td class="new-td" style="">{{:stateName}}</td>
        									</tr>
										{{/for}}
									{{/if}}
        						</tbody>	
        					</table>
        				</td>
						<td>
        	 				<table class="table close-border" width="100%">
        						<tbody>
									{{if orderList!=null}}
										{{for orderList}}
        									<tr>
												<td class="new-td" style="">查看详情</td>
        									</tr>
										{{/for}}
									{{/if}}
        						</tbody>	
        					</table>
        				</td>
				</tr>
  </script> 
   <script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/order/paidOrderList', function (paidOrderPager) {
					pager = new paidOrderPager({element: document.body});
					pager.render();
				});
			})();
 </script>       
</body>
</html>