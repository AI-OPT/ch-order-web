<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>售后详情页面</title>
<%@include file="/inc/inc.jsp" %>
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
	                     		<!--  <div class="form-label">
	                     			<p class="word" ><a  id="backPage" href="javascript:void(0)">返回上一级</a>&nbsp;&nbsp;&nbsp;当前位置：售后订单</p>
	                     		</div>-->
							<div class="form-label">
					           	<ul>
					                <li  class="col-md-6">
					                    <p class="word">订单来源：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.chlidname}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">订单类型：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.ordertype}</p>
					                </li>  
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">仓库ID：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.routeid}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">仓库信息：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.routename}</p>
					                </li> 
					            </ul>
					             <ul>
					                <li  class="col-md-6">
					                    <p class="word">父订单号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.parentorderid}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">子订单号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.orderid }</p>
					                </li>  
					            </ul>
					             <ul>
					                <li  class="col-md-6">
					                    <p class="word">支付流水号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.externalid}</p>
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
                                                <th>退款金额</th>
                                            </tr>
                                        </thead>                                                                                                
                                    <tbody>
						              	<c:forEach items="${orderDetail.prodList}" var="sp">
								          	<tr>
								                 <td class="sp"  width="45%">
								                      <table width="100%" border="0">
								                         <tr>
								                             <td><img src="${sp.imageUrl}"></td>
								                             <td class="word"><a href="#">${sp.prodName}</a></td>	
								                         </tr>
								                      </table>
								                 </td>
								                <td>${sp.prodSalePrice}/${sp.buySum }件</td>
								                <td><fmt:formatDate value="${orderDetail.ordertime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								                <td>${orderDetail.statename}</td>
								                <c:choose>
                            	 					<c:when test="${orderDetail.state!=93}">
                            	 						<td>${orderDetail.updateFee}</td>
                            	 					</c:when> 
                            	 					<c:otherwise>
                            	 						<td>${sp.prodAdjustFee}</td>
                            	 					</c:otherwise>
                            	 				</c:choose>
							              </tr> 
						              </c:forEach>
                                    </tbody>
                                   </table>
                                </div>
                            <!--/table表格结束-->
                              <div class="text-r right">
                            	<ul class="mt-20">
                            		<li>
                            			 <p class="word">总优惠金额：<span class="red">${orderDetail.ordDiscountFee}</span></p>
                            		</li>
                            		<li>
                            			 <p class="word">运费：<span class="red">${orderDetail.ordFreight}</span></p>
                            		</li>
                            		<li>
                            			 <p class="word">订单应付金额：<span class="red">${orderDetail.ordAdjustFee}</span></p>
                            		</li>
                            	</ul>
                            </div>
                            <div class="form-label">
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">售后订单号：</p>
                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.orderid}</p>
                            		</li>
                            		<li class="col-md-6">
	                            		<p class="word">原始订单号：</p>
	                            		<p class="wide-field" style="word-break:break-all;">${orderDetail.origorderid}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
	                            		<p class="word">售后操作人：</p>
	                            		<p class="wide-field" style="word-break:break-all;">${orderDetail.afterSalesOperator}</p>
                            		</li>
                            	</ul>
                            	</div>
	                             <div class="nav-tplist-title bd-bottom pb-10  pt-15">
	                            	<ul>
	                            		<li>客户信息</li>
	                            	</ul>
	                            </div>
	                            <div class="form-label">
	                            	<ul>
	                            		<li class="col-md-6">
	                            			<p class="word">账号信息：</p>
	                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.username}</p>
	                            		</li>
	                            		<li class="col-md-6">
	                            			<p class="word">手机号：</p>
	                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.contacttel}</p>
	                            		</li>
	                            	</ul>
	                            	<ul>
	                            		<li class="col-md-6">
	                            			<p class="word">支付方式：</p>
	                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.paystylename}</p>
	                            		</li>
	                            		<li class="col-md-6">
	                            			<p class="word">收货地址：</p>
	                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.provincecode}${orderDetail.citycode }${orderDetail.countycode}
					                    ${orderDetail.address }&nbsp;${orderDetail.contactname}</p>
	                            		</li>
	                            	</ul>
	                            </div>
                            <div class="nav-tplist-title bd-bottom pb-10  pt-15"></div>
                            <c:choose>
                            	 <c:when test="${orderDetail.state!=94}"> 
		                            <div class="nav-tplist-title bd-bottom pb-10  pt-15">
		                            	<ul>
		                            		<li>买家退货物流信息</li>
		                            	</ul>
		                            </div>
		                            	  <div class="form-label">
			                            	<ul>
			                            		<li class="col-md-6">
			                            			<p class="word">快递公司：</p>
			                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.expressName}</p>
			                            		</li>
			                            		<li class="col-md-6">
			                            			<p class="word">快递单号：</p>
			                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.expressoddnumber}</p>
			                            		</li>
			                            	</ul>
		                            	  </div>
		                           </c:when>
		                         </c:choose>
                            	<div class="row">
	                           		<p class="center mt-20">
	                           			<input type="button" id="backPage" class="biu-btn  btn-primary btn-blue btn-small  ml-5" value="返回">
	                            	</p>
	                         	</div>
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
				seajs.use('app/jsp/order/afterComplete', function (afterCompletePager) {
					pager = new afterCompletePager({element: document.body});
					pager.render();
				});
			})();
 </script>   
</body>
</html>