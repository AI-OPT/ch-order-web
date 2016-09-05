<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>预警订单详情</title>
<%@include file="/inc/inc.jsp" %>
</head>
<body>
  <div class="content-wrapper-iframe"><!--右侧灰色背景-->
  		  <div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        	<div class="main-box-body clearfix">	<!--padding20-->
	                     
								<div class="form-label">
						           	<ul>
						                <li  class="col-md-6">
						                    <p class="word">预警类型：</p>
						                    <p class="color-red">${order.warningType}</p>
						                </li>
						                <li  class="col-md-6">
						                    <p class="word">预警信息：</p>
						                    <p class="color-red">IP:${order.ipAddress}</p>
						                </li>  
						            </ul>  
						            <ul>
						                <li  class="col-md-6">
						                    <p class="word">订单来源：</p>
						                    <p>${order.chlId}</p>
						                </li>
						                <li  class="col-md-6">
						                    <p class="word">订单类型：</p>
						                    <p>${order.orderType}</p>
						                </li>  
						            </ul>
					  			</div>
					  	 	<!--table表格-->
                                <div class="table-responsive clearfix">
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>
                                            <tr>
                                            	<th>商品</th>
                                                <th>单价/数量</th>
                                                <th>下单时间</th>
                                                <th>订单状态</th>
                                                <th>实付金额</th>
                                                <th>优惠扣减金额</th>
                                                 <th>积分</th>
                                            </tr>
                                        </thead>                                                                                                
                                    <tbody>
                                    	 <tr class="bj-f3">
							                <td class="tl" colspan="8">
							                	<div>
							                		<p>
							                			<span>父订单号:</span>
							                			<span>${order.parentOrderId}</span>
							                		</p>
							                	</div>
							                	<div>
							                		<p>
							                			<span>子订单号:</span>
							                			<span id="orderId">${order.orderId}</span>
							                		</p>
							                	</div>
							                </td>
						              </tr>
						              <c:forEach items="${order.productList}" var="sp">
								          <tr>
								                 <td class="sp"  width="45%">
								                      <table width="100%" border="0">
								                         <tr>
								                             <td><img src="${sp.imageUrl}"></td>
								                             <td class="word"><a href="#">${sp.prodName}</a></td>	
								                         </tr>
								                      </table>
								                 </td>
								                <td>${sp.prodSalePrice}/件</td>
								                <td>${order.orderTime}</td>
								                <td class="color-red">${order.state}</td>
								                <td>${sp.prodAdjustFee}</td>
								                <td>${sp.prodDiscountFee}</td>
								                 <td>${sp.jf}</td>
							              </tr> 
						              </c:forEach>
                                    </tbody>
                                   </table>
                               
                                </div>
                            <!--/table表格结束-->
                            <div class="form-label">
                            	<ul>
                            		<li class="col-md-2">
	                            		<p class="word">
	                            			<h2>买家信息</h2>
	                            		</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-5">
                            			<p class="word">买家：</p>
                            			<p>${order.userId}</p>
                            		</li>
                            		<li class="col-md-5">
                            			<p class="word">手机号：</p>
                            			<p>${order.userTel}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-5">
                            			<p class="word">配送方式：</p>
                            			<p>${order.logisticsType}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">收货信息:</p>
                            			<p>${order.address}&nbsp;${order.contactName}&nbsp;${order.contactTel}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">买家留言:</p>
                            			<p>${order.remark}</p>
                            		</li>
                            	</ul>
                            </div>
                            <div>
	                            <ul>
	                            	<li class="col-md-5">
	                            		<p>
	                             			<input type="button" id="operation" class="biu-btn btn-primary btn-blue btn-small " value="关闭订单">
	                             		</p>
	                            	</li>
	                            </ul>
                            </div>
                            <!--提示弹出框 操作-->	
							<div class="eject-big">
								<div class="prompt-samll" id="prompt">
								<div class="eject-medium-title">
										<p>提示</p>
										<p class="img"><i class="fa fa-times"></i></p>
								</div>
								<!--确认删除-->
								<div class="prompt-samll-confirm">
									<ul>
									<li class="word">确定要关闭订单吗？</li>
									<li>
										<input type="button"  id="close"  class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" value="确认">
										<input id="prompt-close" type="button"  class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" id="closebtn" value="取消"></li>		
									</ul>
								</div>
								</div>	
							<div class="mask" id="eject-mask"></div>
							</div>
						<!--/提示弹出框操作结束-->
                   		</div>
                	</div>
              	</div> 
          	</div>
         </div>
    </div> 
    <script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/order/alertOrderDetail', function (alertOrderDetailPager) {
					pager = new alertOrderDetailPager({element: document.body});
					pager.render();
				});
			})();
 </script> 
</body>
</html>