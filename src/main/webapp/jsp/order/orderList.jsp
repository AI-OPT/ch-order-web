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
<tr>
    	<td>{{:chlIdName}}</td>
        <td>{{:pOrderId}}</td>
        <td>{{:userName}}</td>
        <td>{{:userTel}}</td>
 		<td>{{:totalJF}}</td>
		<td>{{:orderTotalCouponFee}}</td>
 		<td>{{:totalAdjustFee}}</td>
		<td>{{:contactTel}}</td>
        <td>{{:deliveryFlagName}}</td>

        <td>
               <table class="table close-border" width="100%">
                   <tbody>
						{{if orderList!=null}}
							{{for orderList}}  
                                <tr>
                                   <td class="new-td" title="{{:orderId}}" style="">{{:~subStr(5,orderId)}}</td>	
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
									<td  class="new-td" title="{{:prodName}}">{{:~subStr(10,prodName)}}</td>	
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
								{{if productList!=null}}
									{{for productList}}	 
                                <tr>
									<td>{{:buySum}}</td>
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
									<td class="new-td">{{:stateName}}</td>
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
                                   <td><a  href="javascript:void(0);" onclick="pager._detailPage('{{:orderId}}','{{:state}}','{{:parentOrderId}}')">订单详情</a></td>
                                 </tr>
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
	seajs.use('app/jsp/order/orderList', function (OrderListPager) {
		pager = new OrderListPager({element: document.body});
		pager.render();
	});
})();
</script>
</body>
</html>