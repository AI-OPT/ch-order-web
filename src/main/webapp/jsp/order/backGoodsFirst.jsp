<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>查询列表</title>
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
						                    <p class="word">订单来源：</p>
						                    <p>${order.chlId}</p>
						                </li>
						                <li  class="col-md-6">
						                    <p class="word">订单类型：</p>
						                    <p>${order.orderType}</p>
						                </li>  
						            </ul>  
						            <ul>
						                <li  class="col-md-6">
						                    <p class="word">仓库ID：</p>
						                    <p>${order.routeId}</p>
						                </li>
						                <li  class="col-md-6">
						                    <p class="word">仓库信息：</p>
						                    <p>${order.routeName}</p>
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
                                                <th>售后</th>
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
							                		<p>
							                			<span>支付流水号:</span>
							                			<span>${order.balacneIfId}</span>
							                		</p>	
							                	</div>
							                </td>
						              </tr>
						              <c:forEach items="${order.prodList}" var="sp">
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
								                <td>${order.busiCodeName}</td>
								                <td>${order.stateName}</td>
								                <td>${sp.prodAdjustFee}</td>
							                    <td>${sp.prodCouponFee}</td>
							                    <td>${sp.jfFee}</td>
							              </tr> 
						              </c:forEach>
                                    </tbody>
                                   </table>
                               
                                </div>
                            <!--/table表格结束-->
                            <div class="form-label">
                            	<ul>
                            		<li class="col-md-3">
                            			<p class="word">售后订单号：</p>
                            			<p>${order.origOrderId}</p>
                            		</li>
                            		<li class="col-md-3">
	                            		<p class="word">原始订单号：</p>
	                            		<p >${order.origOrderId}</p>
                            		</li>
                            		<li class="col-md-3">
	                            		<p class="word">售后操作人：</p>
	                            		<p >XXXX5</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-2">
	                            		<p class="word">
	                            			<h2>客户信息</h2>
	                            		</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-5">
                            			<p class="word">账号信息：</p>
                            			<p>${order.userId}</p>
                            		</li>
                            		<li class="col-md-5">
                            			<p class="word">手机号：</p>
                            			<p>${order.contactTel}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-5">
                            			<p class="word">支付方式：</p>
                            			<p>${order.payStyleName}</p>
                            		</li>
                            		<li class="col-md-5">
                            			<p class="word">支付账号：</p>
                            			<p>${order.acctId}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-5">
                            			<p class="word">收货信息:</p>
                            			<p>${order.address}&nbsp;${order.contactName}&nbsp;${order.contactTel}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-2"><p class="word"><h2>售后详细</h2></p></li>
                            	</ul>
                            	<c:forEach items="${order.payDataList}" var="cg">
	                            	<ul>
	                            		<li class="col-md-3">
	                            			<p class="word">子订单号：</p>
	                            			<p>${order.orderId}</p>
	                            		</li>
	                            		<li class="col-md-3">
	                            			<p class="word">退款类型：</p>
	                            			<p>${cg.payStyleName}</p>
	                            		</li>
	                            		<li class="col-md-3">
	                            			<p class="word">退款金额:</p>
	                            			<p>${cg.paidFee}</p>
	                            		</li>
	                            	</ul>
                            	</c:forEach>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">退款理由:</p>
                            			<p>尺码不合适，需要更换XL</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">图片:</p>
                            		</li>
                            	</ul>
                            </div>
                            <div>
                            <ul>
                            	<li class="col-md-5">
                            		<p>
                             			<input type="button" id="agrren" class="biu-btn btn-primary btn-blue btn-small " value="同意退货">
                             			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                             			<input type="button" id="add-k" class="biu-btn btn-primary btn-blue btn-small " value="拒绝退货">
                             		</p>
                            	</li>
                            </ul>
                            </div>
                             <!-- 拒绝退货理由 start-->
                            	 <div class="eject-samll" id="add-samll">
	                            	 <div class="eject-medium-title">
										<p></p>
										<p class="img"><i class="fa fa-times"></i></p>
									</div>
									<div class="form-label mt-20">
						           		<ul>
							                <li>
							                	<p class="word"><span>*</span>拒绝理由:</p>
							                    <p class="word"><textarea id="refuseInfo" rows="7" cols="32" class="int-text"></textarea></p>
							                </li>
							                
						            	</ul>
								    </div>	
										<!--按钮-->
										<div class="row mt-15"><!--删格化-->
								               <p class="center pr-30 mt-30">
								                   <input type="button" id="refuse" class="biu-btn  btn-primary  btn-auto  ml-5" value="确  认">
								                   <input id="add-close" type="button" class="biu-btn  btn-primary  btn-auto  ml-5 edit-close" value="取  消">
								                </p>
								        </div>
								</div>
								<div class="mask" id="eject-mask"></div>		
                            <!--  拒绝退货理由end-->
                   </div>
                </div>
              </div> 
          </div>
         </div>
    </div>
     <script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/order/backGoods', function (backPager) {
					pager = new backPager({element: document.body});
					pager.render();
				});
			})();
 </script>   
</body>
</html>