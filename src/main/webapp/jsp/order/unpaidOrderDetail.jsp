<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
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
					                    <p>${order.orderTypeName}</p>
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
                                                <th>应付金额</th>
                                                <th>优惠扣减金额</th>
                                                <th>积分</th>
                                            </tr>
                                        </thead>                                                                                                
                                    <tbody>
                                    	 <tr class="bj-f3">
							                <td class="tl" colspan="7">
							                	<div>
							                		<p>
							                			<span>父订单号:</span>
							                			<span>${order.parentOrderId}</span>
							                		</p>
							                		<p>
							                			<span>子（商家平台）订单号:</span>
							                			<span id="orderId">${order.orderId}</span>
							                		</p>
							                	</div>
							                </td>
						              </tr>
						               <c:forEach items="${order.prodInfo}" var="sp">
								          <tr>
								                 <td class="sp"  width="45%">
								                      <table width="100%" border="0">
								                         <tr>
								                             <td><img src="../images/tp-01.png"></td>
								                             <td class="word"><a href="#">${sp.prodName}</a></td>	
								                         </tr>
								                      </table>
								                 </td>
								                <td>${sp.prodSalePrice}元/件</td>
								                <td>${order.orderTime}</td>
								                <td>${order.stateName}</td>
								                <td>${sp.prodAdjustFee}</td>
								                <td>${sp.prodDiscountFee}</td>
								                <td>xxxxxxxxxxx</td>
							              </tr>
							            </c:forEach> 
						           
                                    </tbody>
                                    </table>
                               
                                </div>
                            <!--/table表格结束-->
                            <header class="main-box-header clearfix">
                            	<h5 class="pull-left">买家信息</h5>
                        	</header>
                            <div class="form-label">
					           	<ul>
					                <li  class="col-md-6">
					                    <p class="word">买家：</p>
					                    <p>${order.userId}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">绑定手机号：</p>
					                    <p>shouji</p>
					                </li>  
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">配送方式：</p>
					                    <p>xxxxxxxxxxx</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">收货信息：</p>
					                    <p>${order.address}&nbsp;${order.contactName}&nbsp;${order.contactTel}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">买家留言：</p>
					                    <p>${order.remark}</p>
					                </li>
					            </ul>
					  		</div>
					  		<div>
	                            <ul>
	                            	<li class="col-md-5">
	                            		<p>
	                             			<input type="button" id="add-k" class="biu-btn btn-primary btn-blue btn-small " value="价格修改">
	                             			&nbsp;&nbsp;&nbsp;
	                             			<input type="button" id="closeOrder" class="biu-btn btn-primary btn-blue btn-small " value="关闭订单">
	                             		</p>
	                            	</li>
	                            </ul>
                            </div>
							 <!-- 修改金额理由 start-->
							 <form id="dataForm" method="post" >
                            	 <div class="eject-medium" id="add-samll">
                            	 <div class="eject-medium-title">
									<p>价格修改</p>
									<p class="img"><i class="fa fa-times"></i></p>
								</div>
									<div class="form-label mt-20">
										<ul>
							                <li>
						                    <p class="word">订单号:</p>
						                    <p>${order.orderId}</p>
							                </li>
						            	</ul>
						            	<ul>
							                <li>
						                    <p class="word">订单金额:</p>
						                    <p>${order.ordAdjustFee}</p>
							                </li>
						            	</ul>
						           		<ul>
							                <li>
							                    <p class="word"><span>*</span>改动价格:</p>
							                    <p><input type="text" class="int-text int-small" name="updateFee"></p>
						                    	<p id="errorMessage"></p>
							                </li>
						            	</ul>
						            	<ul>
						            		<li>
						            			<p class="word">改动备注:</p>
							                    <p><textarea id="updateRemark" rows="4" cols="25" class="int-text"></textarea></p>
						            		</li>
						            	</ul>
								    </div>	
										<!--按钮-->
										<div class="row mt-15"><!--删格化-->
							               <p class="center pr-30 mt-30">
							                   <input type="button" id="update" class="biu-btn  btn-primary  btn-auto  ml-5" value="确  认">
							                   <input id="close" type="button" class="biu-btn  btn-primary  btn-auto  ml-5 edit-close" value="取  消">
							                </p>
								        </div>
								</div>
								<div class="mask" id="eject-mask"></div>
								</form>		
                            <!--  修改金额理由end-->
                        </div>	
                     </div>
                </div>
              </div> 
          </div>
    </div>
     <script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/order/unpaidOrderDetail', function (unpaidOrderPager) {
					pager = new unpaidOrderPager({element: document.body});
					pager.render();
				});
			})();
 </script> 
</body>
</html>