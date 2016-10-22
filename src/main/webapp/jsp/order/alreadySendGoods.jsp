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
		seajs.use('app/jsp/order/alreadySendGoods', function (demopagePager) {
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
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.externalId}</p>
					                </li>  
					            </ul>
					  	</div>
	                <!--步骤结束-->
					  	 	<!--table表格-->
                                <div class="table-responsive clearfix">
                                 <input type="hidden" id="orderId" value="${orderDetail.orderId }">
                                 <input type="hidden" id="pOrderId" value="${orderDetail.parentOrderId }">
					  		 	 <input type="hidden" id="state" value="${orderDetail.state}">
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
                                                <th>操作</th>
                                            </tr>
                                        </thead>                                                                                                
                                    <tbody>
						               <c:forEach var="prod" items="${orderDetail.prodList}" varStatus="status">
							          <tr>
							                 <td class="sp"  width="45%">
							                      <table width="100%" border="0">
							                         <tr>
							                             <td><img src="${prod.imageUrl}"></td>
							                             <td class="word" id="prodName${status.index}"><a href="#">${prod.prodName}</a></td>	
							                         </tr>
							                      </table>
							                 </td>
							                <td>${prod.prodSalePrice}元/<p id="buySum${status.index}">${prod.buySum}件</p></td>
							                <td>${orderDetail.busiCodeName }</td>
							                <td><fmt:formatDate value="${orderDetail.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							                <td>${orderDetail.stateName }</td>
							                <td>${prod.prodCouponFee }</td>
							                <td>${prod.jfFee }</td>
							                <td>${prod.giveJF}</td>
							                <td>
							                	<c:choose>
											       <c:when test="${prod.cusServiceFlag eq 'Y'}">
											  		  <button class="biu-btn btn-blue btn-small  radius"  data-toggle="modal" onclick="pager._afterorderdetail('${orderDetail.orderId }','${prod.skuId}')">售后详情</button>
											       </c:when>
											       <c:otherwise>
													 <button class="biu-btn btn-blue btn-small  radius"  data-toggle="modal" data-target="#myModal${status.index}">售后</button>
											       </c:otherwise>
												</c:choose>
							                </td>
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
								data-toggle="modal" data-dismiss="modal" data-target="#exchangeModal${status.index}">换货
								</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
						<h4 class="modal-title" id="backModalLabel1${status.index}">
							退货提示
						</h4>
					</div>
					<div class="modal-body">
						<h4 class="modal-title text-c" id="backModalLabel${status.index}">
							${prod.prodName}
						</h4><br/>
						<p class="text-c"><input id="backNum${prod.prodDetalId}" class="int-text int-large" placeholder="请输入退货的商品数量" type="text" name="returnSum"/></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" data-dismiss="modal"
						onclick="pager._validateNum('${prod.prodDetalId}','${prod.buySum}')">
							确认
						</button>
						<button type="button" class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" data-dismiss="modal">取消
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="exchangeModal${status.index}" tabindex="-1" role="dialog" 
		aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="exchangeModalLabel1${status.index}">
							换货提示
						</h4>
					</div>
					<div class="modal-body">
						<h4 class="modal-title text-c" id="exchangeModalLabel${status.index}">
							${prod.prodName}
						</h4><br/>
						<p class="text-c"><input id="exchangeNum${prod.prodDetalId}" class="int-text int-large" placeholder="请输入换货的商品数量" type="text" name="exchangeSum"/></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" data-dismiss="modal"
						onclick="pager._exchangeOrderValidateNum('${prod.prodDetalId}','${prod.buySum}')">
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
						<h4 class="modal-title" id="refundModalLabel1${status.index}">
							退款提示
						</h4>
					</div>
					<div class="modal-body">
						<h4 class="modal-title text-c" id="refundModalLabel${status.index}">
							${prod.prodName}
						</h4><br/>
						<p class="text-c"><input id="refundNum${prod.prodDetalId}" class="int-text int-large" placeholder="请输入退款的商品数量" type="text" name="refundSum"/></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" data-dismiss="modal"
						onclick="pager._refundOrder('${prod.prodDetalId}','${prod.buySum}')">
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
					  	  <div class="form-label text">
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
					  	<div class="nav-tplist-title bd-bottom pb-10  pt-15">
		                  <ul>
		                    <li>物流信息</li>
		                  </ul>
				       	</div>
                        		 <div class="form-label text">
					  	  		<ul>
					                <li  class="col-md-6">
					                    <p class="word">快递公司：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.expressName }</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">快递单号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.expressOddNumber }</p>
					                </li>
					            </ul>  
					  	</div>
     <br/>
     <div class="table-responsive clearfix">
                <table class="table table-hover table-border table-bordered" >
                    <thead>
                        <tr>
                            <th>时间</th>
                            <th>物流跟踪</th>
                        </tr>
                    </thead>
                <tbody>
                 <c:forEach var="logisticsDetail" items="${orderDetail.logisticsDetail}">
                    <tr>
                        <td>${logisticsDetail.time }</td>
                         <td>${logisticsDetail.context }</td>
                    </tr>
                 </c:forEach>
                </tbody>
                </table>
         </div>
           <!--/table表格结束-->      
           <br/><br/>                     
                </div>
              </div> 
            </div>
    </div>
</body>
</html>