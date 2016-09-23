<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>查询列表</title>
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
                                                <th>用户账号</th>
                                                <th>绑定手机号</th>
                                                <th>优惠信息</th>
                                                <th>子订单号</th>
                                                <th>收货人手机号</th>
                                                <th>是否需要物流</th>
                                                <th>商品信息</th>
                                                <th>数量</th>
                                                <th>订单状态</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                    <tbody>
                                        <tr>
                                        	<td> up平台</td>
                                            <td>3123231</td>
                                            <td>北京八方达公司</td>
                                            <td>13989890011</td>
                                            <td>20优惠卷</td>
                                            <td>
                                            	<table class="table close-border" width="100%"  border="0">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">13123</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >1123123</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >11231232</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                           <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">13989891100  </td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >13989891100</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none">13989891100</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">是</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >是</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >是</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">全国移动</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >全国移动</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >全国移动</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">30MB通用流量包</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >30MB通用流量包</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >30MB通用流量包</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">待付款</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >待付款</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >待付款</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                             <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style=""><a href="#">订单详情</a></td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" ><a href="#">订单详情</a></td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" ><a href="#">订单详情</a></td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                        </tr>
                                    </tbody>
                                   <tbody>
                                        <tr>
                                        		<td> up平台</td>
                                            <td>3123231</td>
                                            <td>北京八方达公司</td>
                                            <td>13989890011</td>
                                            <td>20优惠卷</td>
                                            <td>
                                            	<table class="table close-border" width="100%"  border="0">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">13123</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >1123123</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >11231232</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                           <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">13989891100  </td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >13989891100</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none">13989891100</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">是</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >是</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >是</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">全国移动</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >全国移动</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >全国移动</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">30MB通用流量包</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >30MB通用流量包</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >30MB通用流量包</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                            <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style="">待付款</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" >待付款</td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" >待付款</td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                             <td>
                                            	 <table class="table close-border" width="100%">
                                            	<tbody>
                                            		<tr>
                                            		<td class="new-td" style=""><a href="#">订单详情</a></td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td" ><a href="#">订单详情</a></td>	
                                            		</tr>
                                            		<tr>
                                            		<td class="new-td bot-none" ><a href="#">订单详情</a></td>	
                                            		</tr>
                                            	</tbody>	
                                            	</table>
                                            </td>
                                        </tr>
                                    </tbody>
                                   
                                    </table>
                               
                                </div>
                           
                           <!--/table表格结束-->
                                </div>
                                	<!--/table表格结束-->
                                <!--分页-->
                                
                                
                                <div class="paging">
                            		<ul class="pagination">
									<li class="disabled"><a href="#"><i class="fa fa-chevron-left"></i></a></li>
									<li class="active"><a href="#">1</a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">4</a></li>
									<li><a href="#">5</a></li>
									<li><a href="#"><i class="fa fa-chevron-right"></i></a></li>
								</ul>
								</div>
								<!--分页结束-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div>      
</body>
</html>