<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>查询列表</title>
<%@include file="/inc/inc.jsp" %>
<script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/order/waitInvoiceDetails', function (demopagePager) {
			pager = new demopagePager({element: document.body});
			pager.render();
		});
	})();
</script>
</head>
<body>
      		  <div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        	<div class="main-box-body clearfix">	<!--padding20-->
	                     		<!--  <div class="form-label">
	                     				<p class="word" ><a id="backPage" href="javascript:void(0)">返回上一级</a>&nbsp;&nbsp;&nbsp;当前位置：订单详细</p>
	                     		</div>-->
							<div class="form-label">
					           	<ul>
					                <li  class="col-md-6">
					                    <p class="word">订单来源：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.chlId}</p>
					                </li>
					               	<li  class="col-md-6">
					                    <p class="word">订单类型：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.orderTypeName}</p>
					                </li> 
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">仓库ID：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.routeId}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">仓库信息：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.routeName}</p>
					                </li>  
					            </ul>
					             <ul>
					                <li  class="col-md-6">
					                    <p class="word">父订单号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.parentOrderId}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">子订单号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.orderId }</p>
					                </li>  
					            </ul>
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">支付方式：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.payStyleName}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">支付流水号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.balacneIfId }</p>
					                </li>  
					            </ul>
					  	</div>
	                <!--步骤结束-->
					  	 	<!--table表格-->
                                <div class="table-responsive clearfix">
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>
                                            <tr>
                                            	<th>商品</th>
                                                <th>单价/数量</th>
                                                <th>售后</th>
                                                <th>下单时间</th>
                                                <th>订单状态</th>
                                                <th>优惠券</th>
                                                <th>消费积分</th>
                                                <th>赠送积分</th>
                                            </tr>
                                        </thead>                                                                                                
                                    <tbody>
						               <c:forEach var="prod" items="${orderDetail.prodList}">
							          <tr>
							                 <td class="sp"  width="45%">
							                      <table width="100%" border="0">
							                         <tr>
							                             <td><img src="${prod.imageUrl}"></td>
							                             <td class="word"><a href="#">${prod.prodName}</a></td>	
							                         </tr>
							                      </table>
							                 </td>
							                <td>${prod.prodSalePrice}元/${prod.buySum}件</td>
							                <td>${orderDetail.busiCodeName }</td>
							                <td><fmt:formatDate value="${orderDetail.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							                <td>${orderDetail.stateName }</td>
							                <td>${prod.prodCouponFee }&nbsp;<br/>
											<c:choose>
										       <c:when test="${prod.cusServiceFlag eq 'Y'}">
										  		  <button class="biu-btn btn-blue btn-small  radius"  data-toggle="modal" onclick="pager._afterorderdetail('${orderDetail.orderId }','${prod.skuId}')">售后详情</button>
										       </c:when>
										       <c:otherwise>
												 <button class="biu-btn btn-blue btn-small  radius"  data-toggle="modal" data-target="#myModal${status.index}">售后</button>
										       </c:otherwise>
											</c:choose>
							                </td>
							                <td>${prod.jfFee }</td>
											<td>${prod.giveJF}</td>
						              </tr> 
						              
						              
						              		              
				 <!-- 模态框（Modal） -->
				 <div class="modal fade" id="myModal${status.index}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							 <div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								 <h4 class="modal-title" id="myModalLabel${status.index}">
									售后处理
								</h4>
							</div>  
							 <div class="modal-body text-center">
							 <br/><br/>
						  		 <button class="biu-btn btn-blue btn-small  radius" data-dismiss="modal"
						  		 data-toggle="modal" data-target="#backModal${status.index}">退货</button> 
								&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="biu-btn btn-blue btn-small  radius" data-dismiss="modal" 
								data-toggle="modal" data-target="#refundModal${status.index}">退款</button>
								<br/><br/>
							</div> 
						</div><!-- /.modal-content -->
					</div><!-- /.modal -->
				</div> 
				
				
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="backModal${status.index}" tabindex="-1" role="dialog" 
		aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
					</div>
					<form id="validateForm" method="post">
					<div class="modal-body">
						<h4 class="modal-title text-c" id="backModalLabel${status.index}">
							${prod.prodName}
						</h4><br/>
						<p class="text-c"><input id="backNum${prod.prodDetalId}" class="int-text int-large" placeholder="请输入退货数量" type="text"/></form></p>
					</div><p id="errorMessage"></p>
					<div class="modal-footer">
						<button type="button" class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" data-dismiss="modal"
						onclick="pager._backOrder('${prod.prodDetalId}')">
							确认
						</button>
						<button type="button" class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" data-dismiss="modal">取消
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		

		<!-- 模态框（Modal） -->
		<div class="modal fade" id="refundModal${status.index}" tabindex="-1" role="dialog" 
		aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="refundModalLabel${status.index}">
							提示
						</h4>
					</div>
					<div class="modal-body">
						<h5 class="word">确认进行退款处理吗?</h5> 
					</div>
					<div class="modal-footer">
						<button type="button" class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" data-dismiss="modal"
						onclick="pager._refundOrder('${prod.prodDetalId}')">
							确认
						</button>
						<button type="button" class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" data-dismiss="modal">取消
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		
						              </c:forEach>
                                    </tbody>
                                    </table>
                               
                                </div>
                            	<!--/table表格结束-->
                               <div class="text-r">
                            	<ul>
                            		<li>
                            			 <p class="word">总优惠金额：${orderDetail.ordDiscountFee}</p>
                            		</li>
                            		<li>
                            			 <p class="word">运费：${orderDetail.ordFreight}</p>
                            		</li>
                            		<li>
                            			 <p class="word">订单应付金额：${orderDetail.ordAdjustFee}</p>
                            		</li>
                            	</ul>
                            </div>
                             <div class="nav-tplist-title bd-bottom pb-10  pt-15">
				                  <ul>
				                    <li>买家信息</li>
				                  </ul>
				       		</div>
                            <div class="form-label">
					           	<ul>
					                <li  class="col-md-6">
					                    <p class="word">买家账号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.userName}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">收货人：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.contactName}</p>
					                </li>
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">手机号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.contactTel }</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">收货地址：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.provinceCode}${orderDetail.cityCode }${orderDetail.countyCode}
					                    ${orderDetail.address }&nbsp;${orderDetail.contactName}&nbsp;${orderDetail.contactTel}</p>
					                </li>
					            </ul>
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">买家留言：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.remark }</p>
					                </li>
					            </ul>
					  	</div>
					  	<div class="nav-tplist-title bd-bottom pb-10  pt-15">
		                  <ul>
		                    <li>发票信息</li>
		                  </ul>
				       	</div>
					  	  <div class="form-label">
					  	  		<ul>
					                <li  class="col-md-6">
					                    <p class="word">发票类型：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.invoiceTypeName }</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">发票类目：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.invoiceContent }</p>
					                </li>
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">发票抬头：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.invoiceTitle }</p>
					                </li>
					            </ul>
					  	</div>
					  		 <div class="mb-10 text-l">
					  		 <input type="hidden" id="orderId" value="${orderDetail.orderId }">
					  		 <input type="hidden" id="pOrderId" value="${orderDetail.parentOrderId }">
					  		 <input type="hidden" id="state" value="${orderDetail.state}">
					  		  <input type="button" class="biu-btn btn-primary btn-blue btn-medium ml-10" 
					  		 onclick="pager._sendGoods('${orderDetail.orderId }');" value="发货">
					  		 <c:choose>
							       <c:when test="${empty orderDetail.invoiceType || orderDetail.invoiceType == '1'}">
							       </c:when>
							       <c:when test="${orderDetail.invoiceStatus == '2'}">
							       </c:when>
							       <c:otherwise>
							  		  <input type="button" class="biu-btn btn-primary btn-blue btn-medium ml-10" 
							  		 onclick="pager._invoicePrint('changhong','${orderDetail.orderId }');" value="打印发票">
							       </c:otherwise>
							 </c:choose>
					  		 <input type="button" class="biu-btn btn-primary btn-blue btn-medium ml-10" 
					  		 onclick="pager._displayInvoiceOrder();" value="打印发货单">
					  		 </div>
							</div>	
                        	</div>	
                        </div>
                </div>
              </div> 
            </div>
    </div>
      
<!-- 模态框（Modal） -->
<div class="modal fade" id="myDeliverModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h2 class="modal-title text-c" id="myModalLabel">
             	  发货清单
            </h2>
         </div>
         <div class="modal-body" id="deliverPrintModal"> </div>
      </div>
</div><!-- /.modal -->
</div>

</body>
<script id="deliverPrintTempalte" type="text/x-jsrender">
						<div class="form-label">
					           	<ul>
					                <li  class="col-md-6">
					                    <p class="word">客户订单号：</p>
					                    <p id="invoiceId">{{:orderId}}</p>
					                </li>
					               	<li  class="col-md-6">
					                    <p class="word">发货日期：</p>
					                    <p>{{:~timestampToDate('yyyy-MM-dd', invoiceDate)}}</p>
					                </li> 
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">物流商：</p>
					                    <p>{{:expressOddNumber}}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">发货仓库：</p>
					                    <p>{{:routeId}}</p>
					                </li>  
					            </ul>
 								<ul>
					                <li  class="col-md-6">
					                    <p class="word">收货人姓名：</p>
					                    <p>{{:contactName}}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">收货人电话：</p>
					                    <p>{{:contactTel}}</p>
					                </li>  
					            </ul>
								 <ul>
					                <li  class="col-md-6">
					                    <p class="word">收货人地址：</p>
					                    <p>{{:provinceCode}}{{:cityCode}}{{:countyCode}}{{:address}}</p>
					                </li>
					            </ul>
					  	</div>
               		 <div class="table-responsive clearfix">
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>
                                            <tr>
                                            	<th>序号</th>
                                                <th>商品编号</th>
                                                <th>商品名称</th>
                                                <th>规格</th>
                                                <th>价格</th>
                                                <th>数量</th>
                                            </tr>
                                        </thead>                                                                                                
                                    <tbody id="invoiceDisPlay">
									 {{for invoicePrintVos}}  
							          <tr>
     										<td>{{:#index+1}}</td>
							                <td id="{{:#index}}_skuId">{{:skuId}}</td>
							                <td id="{{:#index}}_prodName">{{:prodName}}</td>
							                <td id="{{:#index}}_extendInfo">{{:extendInfo}}</td>
  											<td id="{{:#index}}_salePrice">{{:salePrice}}</td>
							                <td id="{{:#index}}_buySum">{{:buySum}}</td>
 											<td id="{{:#index}}_horOrderId" style="display:none">{{:horOrderId}}</td>
						              </tr> 
									 {{/for}}
                                    </tbody>
                             </table>
                    </div>
					<div class="bj-f3 text-r">
						<p>
							<span>合计:</span>
							<span>{{:sum}}</span><br/>
						</p>
					</div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">取消
            </button>
            <button type="button" onclick="pager._printInvoiceOrder();" class="btn btn-primary" data-dismiss="modal">
               	确认打印
            </button>
         </div>
</script>
</html>