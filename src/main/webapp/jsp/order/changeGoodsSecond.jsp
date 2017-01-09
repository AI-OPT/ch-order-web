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
<input type="hidden" value="${order.expressId}" id="expressId">
<input type="hidden" value="${order.expressOddNumber}" id="expressOddNumber">
<input type="hidden" name="sourceFlag" id="sourceFlag" value="${requestScope.sourceFlag}"/>
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
					                    <p class="wide-field" style="word-break:break-all;">${order.externalId}</p>
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
                            			 <p class="word">总优惠金额：<span class="red">${order.ordDiscountFee}</span></p>
                            		</li>
                            		<li>
                            			 <p class="word">运费：<span class="red">${order.ordFreight}</span></p>
                            		</li>
                            		<li>
                            			 <p class="word">订单应付金额：<span class="red">${order.ordAdjustFee}</span></p>
                            		</li>
                            	</ul>
                            </div>
                            <div class="form-label">
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">售后订单号：</p>
                            			<p  id="orderId" class="wide-field" style="word-break:break-all;">${order.orderId}</p>
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
                             <c:choose>
                            	<c:when test="${order.state==22}">
		                            <!-- 客户信息 -->
		                            <div class="nav-tplist-title bd-bottom pb-10  pt-15">
		                            	<ul>
		                            		<li>客户信息</li>
		                            	</ul>
		                            </div>
		                            <div class="form-label">
		                            	<ul>
		                            		<li class="col-md-6">
		                            			<p class="word" >账号信息：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.userName}</p>
		                            		</li>
		                            		<li class="col-md-6">
		                            			<p class="word">手机号：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.aftercontactTel}</p>
		                            		</li>
		                            	</ul>
		                            	<ul>
		                            		<li class="col-md-6">
		                            			<p class="word">支付方式：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.payStyleName}</p>
		                            		</li>
		                            		<li class="col-md-6">
		                            			<p class="word">收货地址：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.aftercontactInfo}</p>
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
		                            			<p class="word">类型：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.busiCodeName}</p>
		                            		</li>
		                            		<li class="col-md-6">
			                            		<p class="word">换货理由：</p>
			                            		<p class="wide-field" style="word-break:break-all;">${order.remark}</p>
		                            		</li>
		                            	</ul>
		                            	<ul>
		                            		<li class="col-md-6">
		                            			<p class="word">图片：</p>
		                            			<c:forEach items="${order.prodList}" var="sp">
								          			<p class="wide-field" style="word-break:break-all;"><img src="${sp.afterSaleImageUrl}"></p>
						                		</c:forEach>
		                            		</li>
		                            	</ul>
		                            </div>
                            	</c:when>
                            	<c:otherwise>
                            		  <!-- 客户信息 -->
		                            <div class="nav-tplist-title bd-bottom pb-10  pt-15">
		                            	<ul>
		                            		<li>客户信息</li>
		                            	</ul>
		                            </div>
		                            <div class="form-label">
		                            	<ul>
		                            		<li class="col-md-6">
		                            			<p class="word" >账号信息：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.userName}</p>
		                            		</li>
		                            		<li class="col-md-6">
		                            			<p class="word">手机号：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.aftercontactTel}</p>
		                            		</li>
		                            	</ul>
		                            	<ul>
		                            		<li class="col-md-6">
		                            			<p class="word">支付方式：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.payStyleName}</p>
		                            		</li>
		                            		<li class="col-md-6">
		                            			<p class="word">收货地址：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.aftercontactInfo}</p>
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
		                            			<p class="word">类型：</p>
		                            			<p class="wide-field" style="word-break:break-all;">${order.busiCodeName}</p>
		                            		</li>
		                            		<li class="col-md-6">
			                            		<p class="word">换货理由：</p>
			                            		<p class="wide-field" style="word-break:break-all;">${order.remark}</p>
		                            		</li>
		                            	</ul>
		                            	<ul>
		                            		<li class="col-md-6">
		                            			<p class="word">图片：</p>
		                            			<c:forEach items="${order.prodList}" var="sp">
								          			<p class="wide-field" style="word-break:break-all;"><img src="${sp.afterSaleImageUrl}"></p>
						                		</c:forEach>
		                            		</li>
		                            	</ul>
		                            </div>
		                                <div class="nav-tplist-title bd-bottom pb-10  pt-15">
		                            	<ul>
		                            		<li>买家退货物流信息</li>
		                            	</ul>
		                            </div>
		                            
                            <div class="form-label">
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">快递公司：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.expressName}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">快递单号：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.expressOddNumber}</p>
                            		</li>
                            	</ul>
                            </div>
                           <%--  <br/>
						     <div class="table-responsive clearfix">
						                <table class="table table-hover table-border table-bordered" >
						                    <thead>
						                        <tr>
						                            <th>时间</th>
						                            <th>物流跟踪</th>
						                        </tr>
						                    </thead>
						                <tbody>
						                 <c:forEach var="logisticsDetail" items="${order.logisticsDetail}">
						                    <tr>
						                        <td>${logisticsDetail.time }</td>
						                         <td>${logisticsDetail.context }</td>
						                    </tr>
						                 </c:forEach>
						                </tbody>
						                </table>
						         </div> --%>
						         <!--/table表格结束-->        
                            
                            	</c:otherwise>
                            </c:choose>
                            <c:choose>
                            	<c:when test="${order.state==23 && requestScope.sourceFlag!=00}">
                            		<div class="row">
										<p class="center mt-30">
											<input id="operation" type="button" class="biu-btn  btn-primary btn-blue btn-small  ml-5 " value="收到换货">
											<input id="backPage" type="button" class="biu-btn  btn-primary btn-blue btn-small  ml-5 " value="返回">
									   	</p>
									</div>
                            	</c:when>
                            	<c:otherwise>
                            		 <div class="row">
										<p class="center mt-30">
											<input id="backPage" type="button" class="biu-btn  btn-primary btn-blue btn-small  ml-5 " value="返回">
									   	</p>
									</div>
                            	</c:otherwise>
                            </c:choose>
                            
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
									<li class="word">确定要收到换货吗？</li>
									<li>
										<input id="confirmChange" type="button"  class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" value="确认">
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
    </div>
    <script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/order/changeGoodsSecond', function (changeSecondPager) {
					pager = new changeSecondPager({element: document.body});
					pager.render();
				});
			})();
 </script>   
</body>
</html>