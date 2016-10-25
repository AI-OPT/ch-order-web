<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>订单列表</title>
<input type="hidden" name="stateFlag" id="stateFlag" value="${requestScope.stateFlag}"/>
</head>
<body>
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
    <%@include file="/jsp/order/commonDetail.jsp" %><!-- 公共查询条件 -->
        <!--框架标签结束-->
      <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化-->
	                    <div class="main-box clearfix"><!--白色背景-->
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
                            <h5 class="pull-left">订单处理</h5>
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
                                                <th>用户名</th>
                                                <th>绑定手机号</th>
                                                <th>积分</th>
                                                <th>优惠</th>
                                                <th>实付款</th>
                                                <th>收货人手机号</th>
                                                <th>是否需要物流</th>
                                   				<th>子订单号</th>
                                   				<th>商品信息</th>
                                   				<th>数量</th>
                                      			<th>订单状态</th>
                                     			<th>详情</th>
	                                            	
                                            </tr>
                                        </thead>
                                         <tbody id="orderListData"></tbody>
                                    </table>
                                    <div id="showMessage"></div>
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
<script id="orderListTemple" type="text/template">

					{{if orderList!=null}}
						{{for orderList ~orderData = #data}}  
							<!-- 商品 -->
								{{for productList ~parentProdSize=prodSize ~cOrderId=orderId 
									~busiCode=busiCode ~state=state ~stateName=stateName
									~parentInd = #index ~parentOrder =~orderData }}	
        						<tr>
								{{if ~parentInd == 0 && #index ==0}}
									<td rowspan="{{:~parentOrder.totalProdSize}}">{{:~parentOrder.chlIdName}}</td>
		   							<td rowspan="{{:~parentOrder.totalProdSize}}">{{:~parentOrder.pOrderId}}</td>
									<td rowspan="{{:~parentOrder.totalProdSize}}">{{:~parentOrder.userName}}</td>
									<td rowspan="{{:~parentOrder.totalProdSize}}">{{:~parentOrder.userTel}}</td>
									<td rowspan="{{:~parentOrder.totalProdSize}}">{{:~parentOrder.totalJF}}</td>
									<td rowspan="{{:~parentOrder.totalProdSize}}">{{:~parentOrder.orderTotalDiscountFee}}</td>
									<td rowspan="{{:~parentOrder.totalProdSize}}">{{:~parentOrder.totalAdjustFee}}</td>
		   							<td rowspan="{{:~parentOrder.totalProdSize}}">{{:~parentOrder.contactTel}}</td>
									<td rowspan="{{:~parentOrder.totalProdSize}}">{{:~parentOrder.deliveryFlagName}}</td>
								{{/if}}
								{{if #index ==0 }}
									<td rowspan="{{:~parentProdSize}}">{{:~subStr(2,~cOrderId)}}</td>
								{{/if}}
									<td title="{{:prodName}}">{{:~subStr(10,prodName)}}</td>	
									<td >{{:buySum}}</td>
								{{if #index ==0 }}
									<td  rowspan="{{:~parentProdSize}}">{{:~stateName}}</td>
									<td  rowspan="{{:~parentProdSize}}"><a  href="javascript:void(0);" onclick="pager._detailPage('{{:~cOrderId}}','{{:~state}}','{{:~parentOrder.pOrderId}}')">查看详情</a></td>
								{{/if}}
        					</tr>
        			{{/for}}
				{{/for}}
			{{/if}}	
 </script> 
<script type="text/javascript">
var pager;
(function () {
	seajs.use('app/jsp/order/orderList', function (OrderListPager) {
		pager = new OrderListPager({element: document.body});
		pager.render();
	});
})();
</script>
</body>
</html>