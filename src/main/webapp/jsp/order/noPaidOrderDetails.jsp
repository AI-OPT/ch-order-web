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
<script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/demo/demopage', function (demopagePager) {
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
					                    <p>代理商订单</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">订单归属地：</p>
					                    <p>北京</p>
					                </li>  
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">订单类型：</p>
					                    <p>流量充值订单（直充类）</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">是否需要物流：</p>
					                    <p>否</p>
					                </li>  
					            </ul>
					             <ul>
					                <li  class="col-md-6">
					                    <p class="word">订单类型：</p>
					                    <p>流量充值订单（直充类）</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">下单时间：</p>
					                    <p>2016-02-12 16：23</p>
					                </li>  
					            </ul>
					  	</div>
					  	 <!--步骤-->
		                     <div class="steps steps-four">
		                    		<ul>
		                    			<li class="active">用户申请退款<i class="fa  fa-arrow-right"></i></li>
		                    			<li class="active">等待审批<i class="fa  fa-arrow-right"></i></li>
		                    			<li class="active">退回商品<i class="fa  fa-arrow-right"></i></li>
		                    			<li>卖家处理<i class="fa  fa-arrow-right"></i></li>
		                    			<li>退款给用户<i class="fa  fa-arrow-right"></i></li>
		                    			<li>用户申请退款<i class="fa  fa-arrow-right"></i></li>
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
							                			<span>20103910019301</span>
							                		</p>
							                		<p>
							                			<span>支付流水号:</span>
							                			<span>20103910019301</span>
							                		</p>
							                	</div>
							                	<div>
							                		<p>
							                			<span>子（商家平台）订单号:</span>
							                			<span>20103910019301</span>
							                		</p>
							                		<p>
							                			<span>支付流水号:</span>
							                			<span>20103910019301</span>
							                		</p>	
							                		<p>
							                			<span>支付时间:</span>
							                			<span>2016-02-12 16：23</span>
							                		</p>
							                	</div>
							                </td>
						              </tr>
							          <tr>
							                 <td class="sp"  width="45%">
							                      <table width="100%" border="0">
							                         <tr>
							                             <td><img src="../images/tp-01.png"></td>
							                             <td class="word"><a href="#">中国移动全国通用1G流量包半年包</a></td>	
							                         </tr>
							                      </table>
							                 </td>
							                <td>5.2元/件</td>
							                <td>2016-06-11</td>
							                <td></td>
							                <td class="color-red">交易完成</td>
							                <td>30.00</td>
							                <td>2.00</td>
						              </tr> 
						              <tr>
							                 <td class="sp"  width="45%">
							                      <table width="100%" border="0">
							                         <tr>
							                             <td><img src="../images/tp-01.png"></td>
							                             <td class="word"><a href="#">中国移动全国通用1G流量包半年包</a></td>	
							                         </tr>
							                      </table>
							                 </td>
							                <td>5.2元/件</td>
							                <td>2016-06-11</td>
							                <td></td>
							                <td class="color-red">交易完成</td>
							                <td>30.00</td>
							                <td>2.00</td>
						              </tr> 
						              <tr class="bj-f3">
							                <td class="tl" colspan="7">
							                	<div>
							                		<p>
							                			<span>供货商:</span>
							                			<span>中国移动北京分公司</span>
							                		</p>
							                		<p>
							                			<span>分配路由:</span>
							                			<span>北京移动流量路由</span>
							                		</p>	
							                	</div>
							                	<div>
							                		<p>
							                			<span>库存组ID:</span>
							                			<span>28391039383173</span>
							                		</p>
							                		<p>
							                			<span>标准品ID:</span>
							                			<span>2738910300100</span>
							                		</p>	
							                		<p>
							                			<span>标准品名称:</span>
							                			<span>手机流量30MB</span>
							                		</p>
							                	</div>
							                </td>
						              </tr>
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
					                    <p>章叁</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">绑定手机号：</p>
					                    <p>1381920123</p>
					                </li>  
					            </ul>  
					            <ul>
					                <li  class="col-md-6">
					                    <p class="word">充值手机号：</p>
					                    <p>1529172791</p>
					                </li>
					               
					            </ul>
					  	</div>
                        	</div>	
                        </div>
                </div>
              </div> 
            </div>
    </div>
</body>
</html>