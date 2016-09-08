<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>查询售后列表</title>
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
	                    	<form id="dataForm" method="post" >
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
									            			<option>仓库1</option>
									            			<option>仓库2</option>
									            			<option>仓库3</option>
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
                                                <th>是否需要物流</th>
                                                 <th>子订单号</th>
                                                <th>商品信息</th>
                                                <th>订单状态</th>
                                                <th>详情</th>
                                            </tr>
                                        </thead>
	                                     <tbody id="paidData"></tbody>
                                    </table>
                                    <div id="showMessageDiv"></div>
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
						<td>{{:deliveryFlagName}}</td>
						<td>
        	 				<table class="table close-border" width="100%">
        						<tbody>
									{{if orderList!=null}}
										{{for orderList}}
        									<tr>
												<td class="new-td">{{:orderId}}</td>
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
													<td class="hind1 new-td">
														<div class="center-hind" >{{:prodName}}</div>
        												<div class="showbj"><i class="fa fa-posi fa-caret-up"></i>{{:prodName}}</div>
													</td>
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
											{{if busiCode=='2'}}
												<tr>
													<td class="new-td" ><a  href="javascript:void(0);" onclick="pager._detail('{{:orderId}}','{{:busiCode}}','{{:state}}')">查看详情(换货)</a></td>
        										</tr>			
											{{else  busiCode=='3'}}
												<tr>
													<td class="new-td"><a  href="javascript:void(0);" onclick="pager._detail('{{:orderId}}','{{:busiCode}}','{{:state}}')">查看详情(退货)</a></td>
        										</tr>
											{{else}}
												<tr>
													<td class="new-td"><a  href="javascript:void(0);" onclick="pager._detail('{{:orderId}}','{{:busiCode}}','{{:state}}')">查看详情</a></td>
        										</tr>
											{{/if}}
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