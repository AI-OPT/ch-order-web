<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>审核详情</title>
<%@include file="/inc/inc.jsp" %>
<input type="hidden" value="${order.downstreamOrderId}" id="downOrdId">
<input type="hidden" value="${order.accountId}" id="accountId">
<input type="hidden" value="${order.operId}" id="operId">
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
					                    <p class="wide-field" style="word-break:break-all;">${order.orderType}</p>
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
					                    <p id="parentId" class="wide-field" style="word-break:break-all;">${order.parentOrderId}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">子订单号：</p>
					                    <p id="orderId" class="wide-field" style="word-break:break-all;">${order.orderId}</p>
					                </li>  
					            </ul>
					            <ul>
					            	<li  class="col-md-6">
					                    <p class="word">支付流水号：</p>
					                    <p id="balanceId" class="wide-field" style="word-break:break-all;">${order.balacneIfId}</p>
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
								                <td>${sp.prodSalePrice}/件</td>
								                <td>${order.orderTime}</td>
								                <td>${order.stateName}</td>
								                <td>${sp.prodCouponFee}</td>
							                	<td id="saleJF">${sp.jfFee}</td>
							                	<td id="giveJF">${sp.giveJF}</td>
							              </tr> 
						              </c:forEach>
                                    </tbody>
                                   </table>
                               
                                </div>
                            <!--/table表格结束-->
                            <div class="form-label">
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">售后订单号：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.orderId}</p>
                            		</li>
                            		<li class="col-md-6">
	                            		<p class="word">原始订单号：</p>
	                            		<p class="wide-field" style="word-break:break-all;">${order.origOrderId}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
	                            		<p class="word">售后操作人：</p>
	                            		<p class="wide-field" style="word-break:break-all;">${orderDetail.username}</p>
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
                            			<p class="word" >账号信息：</p>
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
                            			<p class="wide-field" style="word-break:break-all;">${order.address}</p>
                            		</li>
                            	</ul>
                            </div>
                            <div class="nav-tplist-title bd-bottom pb-10  pt-15">
                            	<ul>
                            		<li>退款详细</li>
                            	</ul>
                            </div>
                            <div class="form-label">
                            		<ul>
                            		<li class="col-md-6">
                            			<p class="word">子订单号：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.orderId}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">业务类型：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.busiCodeName}</p>
                            		</li>
                            		
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">退款金额：</p>
                            			<p id="currentMoney" class="wide-field" style="word-break:break-all;">${order.ordAdjustFee}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">退款理由：</p>
                            			<p class="wide-field" style="word-break:break-all;">${order.remark}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">图片：</p>
                            			<p></p>
                            		</li>
                            	</ul>
                            	</div>
                            <div class="nav-tplist-title bd-bottom pb-10  pt-15">
                            	<ul>
                            		<li>退款物流</li>
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
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">图片：</p>
                            			<p></p>
                            		</li>
                            	</ul>
                            </div>
                            <div class="row">
                           		<p class="center mt-20">
                           			<input type="button" class="biu-btn  btn-primary btn-blue btn-small  ml-5" id="edit" value="同意退款">
                           			<input type="button" id="add-k" class="biu-btn  btn-primary btn-blue btn-small  ml-5" value="拒绝退款">
                           			<input type="button" id="backPage" class="biu-btn  btn-primary btn-blue btn-small  ml-5" value="返回">
                            	</p>
                            </div>
                            <!-- 拒绝退款理由 start-->
                           <form id="refuseDataForm" method="post" >
                            <div class="eject-big">
                            	 <div class="eject-medium" id="add-samll">
                            	 	<div class="eject-medium-title">
										<p class="img"><i class="fa fa-times"></i></p>
									</div>
									<div class="form-label mt-20">
						           		<ul>
							                <li>
							                	<p class="word"><span>*</span>拒绝理由:</p>
							                    <p><textarea id="refuseMoneyInfo" name="refuseMoneyInfo" rows="4" cols="25" class="int-text"></textarea></p>
							                </li>
							                
						            	</ul>
								    </div>	
										<!--按钮-->
										<div class="row mt-15"><!--删格化-->
								               <p class="center pr-30 mt-30">
								                   <input type="button" id="refuseBackMoney" class="biu-btn  btn-primary  btn-auto  ml-5" value="确  认">
								                   <input id="add-close" type="button" class="biu-btn  btn-primary  btn-auto  ml-5 edit-close" value="取  消">
								               </p>
								        </div>
								</div>
								<div class="mask" id="eject-mask"></div>
							</div>	
							</form>
                            <!--  拒绝退款理由end-->
                            <!--编辑名称弹出框-->
                             <form id="dataForm" method="post" >
							<div class="eject-big">
									<div class="eject-medium" id="edit-medium">
										<div class="eject-medium-title">
											<p class="img"><i class="fa fa-times"></i></p>
										</div>
										<div class="form-label mt-20">
							           		<ul>
								                <li>
								                    <p class="word"><span>*</span>退款金额:</p>
								                    <p ><input type="text" class="int-text int-medium" placeholder="不能多余用户申请金额" name="updateMoneyData" id="updateMoneyData"></p>
								                    </li>
								                <li>
								                	<p class="word"><span>*</span>修改理由:</p>
								                    <p>
										 				<textarea id="updateMoneyInfo" name="updateMoneyInfo" rows="4" cols="25" class="int-text"></textarea>
										 			</p>
								                </li>
							            	</ul>
							      	    </div>
											<!--按钮-->
										<div class="row mt-15" ><!--删格化-->
											 <p class="center pr-30 mt-30">
												<input type="button" class="biu-btn  btn-primary  btn-auto  ml-5" id="updateMoney" value="确  认">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<input id="edit-close" type="button" class="biu-btn  btn-primary  btn-auto  ml-5" value="取  消">
											 </p>
										 </div>
									</div>	
									<div class="mask" id="eject-mask"></div>	
							</div>
							</form>
							<!--编辑名称弹出框 结束-->	
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
				seajs.use('app/jsp/order/backGoodSecond', function (backSecondPager) {
					pager = new backSecondPager({element: document.body});
					pager.render();
				});
			})();
 </script>   
</body>
</html>