<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>>
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
	                     
							<div class="form-label">
					           	<ul>
					                <li  class="col-md-6">
					                    <p class="word">订单来源：</p>
					                    <p>${orderInfos.chlId}</p>
					                </li>
					               	<li  class="col-md-6">
					                    <p class="word">订单类型：</p>
					                    <p>${orderInfos.orderType}</p>
					                </li> 
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">仓库ID：</p>
					                    <p>${orderInfos.routeId}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">仓库信息：</p>
					                    <p>北京某某</p>
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
                                                <th>下单时间</th>
                                                <th>售后</th>
                                                <th>订单状态</th>
                                                <th>实付金额</th>
                                                <th>优惠扣减金额</th>
                                            </tr>
                                        </thead>                                                                                                
                                    <tbody>
                                    	 <tr class="bj-f3">
							                <td class="tl" colspan="7">
							                	<div>
							                		<p>
							                			<span>父订单号:</span>
							                			<span>${orderInfos.parentOrderId}</span>
							                		</p>
							                		<p>
							                			<span>——&nbsp;</span>
							                			<span>${orderInfos.payStyleName}</span>
							                		</p>
							                	</div>
							                	<div>
							                		<p>
							                			<span>子（商家平台）订单号:</span>
							                			<span>${orderInfos.orderId }</span>
							                		</p>
							                		<p>
							                			<span>支付流水号:</span>
							                			<span>${orderInfos.balacneIfId }</span>
							                		</p>	
							                	</div>
							                </td>
						              </tr>
						               <c:forEach var="prod" items="${orderInfos.productList}">
							          <tr>
							                 <td class="sp"  width="45%">
							                      <table width="100%" border="0">
							                         <tr>
							                             <td><img src=""></td>
							                             <td class="word"><a href="#">${prod.prodName}</a></td>	
							                         </tr>
							                      </table>
							                 </td>
							                <td>${prod.salePrice}元/件</td>
							                <td>${orderInfos.orderTime}</td>
							                <td></td>
							                <td>${prod.state }</td>
							                <td>${prod.adjustFee }</td>
							                <td>${prod.discountFee }</td>
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
					                    <p class="word">买家账号：</p>
					                    <p>${orderInfos.userId}</p>
					                </li>
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">收货人：</p>
					                    <p>${orderInfos.contactName}</p>
					                </li>
					            </ul>
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">手机号：</p>
					                    <p>${orderInfos.contactTel }</p>
					                </li>
					            </ul>
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">收货地址：</p>
					                    <p>${orderInfos.provinceCode}${orderInfos.cityCode }${orderInfos.countyCode}
					                    ${orderInfos.address },${orderInfos.contactName},${orderInfos.contactTel}</p>
					                </li>
					            </ul>
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">买家留言：</p>
					                    <p>${orderInfos.remark }</p>
					                </li>
					            </ul>
					  	</div>
					  	<br/><br/><br/>
					  	<header class="main-box-header clearfix">
                            <h5 class="pull-left">发票信息</h5>
                        		</header>
					  	  <div class="form-label text">
					           	<ul>
					                <li  class="col-md-6">
					                    <p class="word">发票类目：</p>
					                    <p>${orderInfos.invoiceType }</p>
					                </li>
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">发票抬头：</p>
					                    <p>${orderInfos.invoiceTitle }</p>
					                </li>
					            </ul>
					  	</div>
					  		 <div class="bc-ang mb-10">
					  		 <input type="hidden" id="orderId" value="${orderInfos.orderId }">
					  		 <input type="button" class="biu-btn btn-primary btn-blue btn-medium ml-10" 
					  		 onclick="pager._displayInvoiceOrder();" value="打印发货单"></div>
							</div>	
                        	</div>	
                        </div>
                </div>
              </div> 
            </div>
    </div>
      
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
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
         <div class="modal-body" id="invoiceModal"> </div>
      </div>
</div><!-- /.modal -->
</div>

</body>
<script id="invoiceTempalte" type="text/x-jsrender">
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