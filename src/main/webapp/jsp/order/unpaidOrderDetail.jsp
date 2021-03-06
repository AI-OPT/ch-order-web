<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>待付款详情</title>
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
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.chlidname}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">订单类型：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.ordertypename}</p>
					                </li>  
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">父订单号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.orderid}</p>
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
								                <td>${sp.prodSalePrice}元/${sp.buySum }件</td>
								                <td><fmt:formatDate value="${orderDetail.ordertime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								                <td>${orderDetail.statename}</td>
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
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.username}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">手机号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.contacttel}</p>
					                </li>  
					            </ul>  
					            <ul>
					            
					            	<li  class="col-md-6">
					                    <p class="word">收货人：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.contactname}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">收货地址：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.provincecode}${orderDetail.citycode }${orderDetail.countycode}
					                    ${orderDetail.address }&nbsp;${orderDetail.contactname}&nbsp;${orderDetail.contacttel}</p>
					                </li>
					            </ul>
					            <ul>
					            	<li class="col-md-6">
					                    <p class="word">买家留言：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.remark}</p>
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
					           		<li class="col-md-6">
					                    <p class="word">发票类型：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.invoicetypename}</p>
					                </li>
					                <li class="col-md-6">
					                    <p class="word">发票类目：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.invoicecontent}</p>
					                </li>
					            </ul>
					            <ul>
					            	<li class="col-md-6">
					                    <p class="word">发票抬头：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.invoicetitle}</p>
					                </li>
					            </ul>
					  		</div>
					  		<div  class="row">
                           		<p class="center mt-20">
                           			<input type="button" id="add-k" class="biu-btn  btn-primary btn-blue btn-small  ml-5" value="价格修改">
                           			<input type="button" id="operation" class="biu-btn  btn-primary btn-blue btn-small  ml-5" value="关闭订单">
                            	</p>
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
										<input type="button"  id="closeOrder"  class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" value="确认">
										<input id="prompt-close" type="button"  class="biu-btn  btn-primary btn-blue btn-small ml-15 mt-20 radius" id="closebtn" value="取消"></li>		
									</ul>
								</div>
								</div>	
							<div class="mask" id="eject-mask"></div>
							</div>
						<!--/提示弹出框操作结束-->
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
						                    <p id="orderid">${orderDetail.orderid}</p>
							                </li>
						            	</ul>
						            	<ul>
							                <li>
						                    <p class="word">订单金额(元):</p>
						                    <p id="currentMony">${orderDetail.ordAdjustFee}</p>
							                </li>
						            	</ul>
						           		<ul>
							                <li>
							                    <p class="word"><span>*</span>改动价格:</p>
							                    <p><input type="text" class="int-text int-small" name="updateFee" id="updateFee"></p>
						                    	<p id="errorMessage"></p>
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