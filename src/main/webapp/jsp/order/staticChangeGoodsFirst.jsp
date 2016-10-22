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
					                    <p class="word">支付流水号：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.externalId}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">支付方式：</p>
					                    <p class="wide-field" style="word-break:break-all;">${orderDetail.payStyleName}</p>
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
							                <td>${sp.prodSalePrice}/件</td>
							                <td><fmt:formatDate value="${orderDetail.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							                <td>${orderDetail.busiCodeName}</td>
							                <td>${orderDetail.stateName}</td>
							                <td>${sp.prodCouponFee}</td>
							                <td>${sp.jfFee}</td>
							                <td>${sp.giveJF}</td>
						              </tr>
						             </c:forEach>
                                    </tbody>
                                   </table>
                                </div>
                            <!--/table表格结束-->
							 <div class="text-r right" >
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
                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.orderId}</p>
                            		</li>
                            		<li class="col-md-6">
	                            		<p class="word">原始订单号：</p>
	                            		<p class="wide-field" style="word-break:break-all;">${orderDetail.origOrderId}</p>
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
                            			<p class="word">买家账号：</p>
                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.userName}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">手机号：</p>
                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.contactTel}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">支付方式：</p>
                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.payStyleName}</p>
                            		</li>
                            		<li class="col-md-6">
                            			<p class="word">支付账号：</p>
                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.acctId}</p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">收货信息:</p>
                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.address}&nbsp;${orderDetail.contactName}&nbsp;${orderDetail.contactTel}</p>
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
                            			<p class="wide-field" style="word-break:break-all;">${orderDetail.busiCodeName}</p>
                            		</li>
                            		<li class="col-md-6">
	                            		<p class="word">换货理由:</p>
	                            		<p class="wide-field" style="word-break:break-all;"></p>
                            		</li>
                            	</ul>
                            	<ul>
                            		<li class="col-md-6">
                            			<p class="word">图片:</p>
                            		</li>
                            	</ul>
                            </div>
                            <div class="row">
								<p class="center mt-30">
									<input id="backPage" type="button" class="biu-btn  btn-primary btn-blue btn-small  ml-5 " value="返  回">
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
				seajs.use('app/jsp/order/staticChangeGoodsFirst', function (staticChangePager) {
					pager = new staticChangePager({element: document.body});
					pager.render();
				});
			})();
 </script>   
</body>
</html>