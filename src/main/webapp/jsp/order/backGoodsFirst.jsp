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
						                    <p class="wide-field" style="word-break:break-all;">${order.chlId}</p>
						                </li>
						                <li  class="col-md-6">
						                    <p class="word">订单类型：</p>
						                    <p class="wide-field" style="word-break:break-all;">${order.orderTypeName}</p>
						                </li>  
						            </ul>  
						            <ul>
						                <li  class="col-md-6">
						                    <p class="word">仓库ID：</p>
						                    <p class="wide-field" style="word-break:break-all;">${order.routeId}</p>
						                </li>
						                <li  class="col-md-6">
						                    <p class="word">仓库信息：</p>
						                    <p class="wide-field" style="word-break:break-all;">${order.routeName}</p>
						                </li>  
						            </ul>
						            <ul>
					                <li  class="col-md-6">
					                    <p class="word">父订单号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${order.parentOrderId}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">子订单号：</p>
					                    <p  class="wide-field" style="word-break:break-all;">${order.origOrderId}</p>
					                </li>  
					            </ul>
					            <ul>
					            	<li  class="col-md-6">
					                    <p class="word">支付流水号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${order.balacneIfId}</p>
					                </li>  
					            </ul>
					  			</div>
					  	 	<!--table表格-->
                                <div class="table-responsive">
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>
                                            <tr>
                                            	<th>商品</th>
                                                <th>单价/数量</th>
                                                <th>下单时间</th>
                                                <th>售后</th>
                                                <th>订单状态</th>
                                                <th>优惠券</th>
                                                <th>消费积分</th>
                                                <th>赠送积分</th>
                                            </tr>
                                        </thead>                                                                                                
                                    <tbody>
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
								                <td>${sp.prodSalePrice}/${sp.buySum}件</td>
								                <td><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								                <%-- <td>${order.orderTime}</td> --%>
								                <td>${order.busiCodeName}</td>
								                <td>${order.stateName}</td>
							                    <td>${sp.prodCouponFee}</td>
							                    <td>${sp.jfFee}</td>
							                     <td>${sp.giveJF}</td>
							              </tr> 
						              </c:forEach>
                                    </tbody>
                                   </table>
                               
                                </div>
                            <!--/table表格结束-->
                            <div class="text-r right">
                            	<ul class="mt-20">
                            		<li>
                            			 <p class="word">总优惠金额：${order.ordDiscountFee}</p>
                            		</li>
                            		<li>
                            			 <p class="word">运费：${order.ordFreight}</p>
                            		</li>
                            		<li>
                            			 <p class="word">订单应付金额：${order.ordAdjustFee}</p>
                            		</li>
                            	</ul>
                            </div>
                            <div class="form-label">
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">售后订单号：</p>
                            			<p id="orderId" class="wide-field" style="word-break:break-all;">${order.orderId}</p>
                            		</li>
                            		<li class="col-md-6">
	                            		<p class="word">原始订单号：</p>
	                            		<p class="wide-field" style="word-break:break-all;">${order.origOrderId}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
	                            		<p class="word">售后操作人：</p>
	                            		<p class="wide-field" style="word-break:break-all;">${order.username}</p>
                            		</li>
                            	</ul>
                            </div>
                            <div class="nav-tplist-title bd-bottom pb-10  pt-15"></div>
                            <div class="nav-tplist-title bd-bottom pb-10  pt-15">
                            	<ul>
                            		<li>客户信息</li>
                            	</ul>
                            </div>
                            <div class="form-label">
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">账号信息：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.userName}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">手机号：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.contactTel}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">支付方式：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.payStyleName}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">支付账号：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.acctId}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">收货信息:</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.address}&nbsp;${order.contactName}&nbsp;${order.contactTel}</p>
                            		</li>
                            	</ul>
                            </div>
                            <div class="nav-tplist-title bd-bottom pb-10  pt-15">
                            	<ul>
                            		<li>售后详细</li>
                            	</ul>
                            </div>
                            <div class="form-label">
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">业务类型：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.busiCodeName}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">退款金额:</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.ordAdjustFee}</p>
                            		</li>
                            		
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">退款理由:</p>
                            			<p class="wide-field" style="word-break:break-all;"></p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">图片:</p>
                            		</li>
                            	</ul>
                            </div>
                            <c:choose>
                            	 <c:when test="${order.state != 212}"> 
                            	 	 <div class="row">
		                           		<p class="center mt-20">
		                           			<input type="button" id="operation" class="biu-btn  btn-primary btn-blue btn-small  ml-5" value="同意退货">
		                           			<input type="button" id="add-k" class="biu-btn  btn-primary btn-blue btn-small  ml-5" value="拒绝退货">
		                           			<input type="button" id="backPage" class="biu-btn  btn-primary btn-blue btn-small  ml-5" value="返回">
		                            	</p>
	                            	</div> 
                            	 </c:when>
                            	 <c:otherwise>
                            	 	<div class="row">
		                           		<p class="center mt-20">
		                           			<input type="button" id="backPage" class="biu-btn  btn-primary btn-blue btn-small  ml-5" value="返回">
		                            	</p>
	                            	</div>
                            	 </c:otherwise>
                            </c:choose>
                            <!--同意退货提示弹出框 操作 start-->	
								<div class="eject-big">
									<div class="prompt-samll" id="prompt">
									<div class="eject-medium-title">
											<p>提示</p>
											<p class="img"><i class="fa fa-times"></i></p>
									</div>
									<!--确认同意换货-->
									<div class="prompt-samll-confirm">
										<ul>
										<li class="word">确定同意退货吗？</li>
										<li>
											<input type="button"  id="agrren" class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" value="确认">
											<input id="prompt-close" type="button"  class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" id="closebtn" value="取消"></li>		
										</ul>
									</div>
									</div>	
								<div class="mask" id="eject-mask"></div>
								</div>
							<!--/提示弹出框操作结束 end-->
                             <form id="refuseDataForm" method="post" >
                             <!-- 拒绝退货理由 start-->
                            	 <div class="eject-medium" id="add-samll">
	                            	 <div class="eject-medium-title">
										<p></p>
										<p class="img"><i class="fa fa-times"></i></p>
									</div>
									<div class="form-label mt-20">
						           		<ul>
							                <li>
							                	<p class="word"><span>*</span>拒绝理由:</p>
							                    <p><textarea id="refuseInfo" name="refuseInfo" rows="7" cols="32" class="int-text"></textarea></p>
							                	<p id="errorMessage"></p>
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
								</form>		
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