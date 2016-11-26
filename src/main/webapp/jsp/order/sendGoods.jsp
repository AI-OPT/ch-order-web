<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>发货</title>
<%@include file="/inc/inc.jsp" %>
</head>
<body>
	  <input id="deliveryModal_orderId" type="hidden" value="${orderId }" />
      <input id="deliveryModal_parentOrderId" type="hidden" value="${pOrderId }" />
      <input id="deliveryModal_state" type="hidden" value="${state }" />
      <input id="deliveryModal_busiCode" type="hidden" value="${busiCode }" />
      <input id="deliveryModal_flag" type="hidden" value="${flag }" />
         
      <div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        	<div class="main-box-body clearfix">	<!--padding20-->
					  	 	<!--table表格-->
					  	 	<form id="dataForm">
                                <div class="table-responsive clearfix" >
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>
                                            <tr>
                                            	<th>公司名</th>
                                                <th>快递单号</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>                                                                                                
                                    <tbody>
                                      <tr>
							                <td value="shunfeng">顺丰</td>
							                <td><input type="text" class="int-text int-medium" id="flowName1" name="flowName1"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
							          <tr>
							                <td value="shentong">申通</td>
							                <td><input type="text" class="int-text int-medium" id="flowName2" name="flowName2"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="yuantong">圆通</td>
							                <td><input type="text" class="int-text int-medium" id="flowName3" name="flowName3"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
							          <tr>
							                <td value="zhongtong">中通</td>
							                <td><input type="text" class="int-text int-medium" id="flowName4" name="flowName4"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="yunda">韵达</td>
							                <td><input type="text" class="int-text int-medium" id="flowName5" name="flowName5"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
							          <tr>
							                <td value="zhaijisong">宅急送</td>
							                <td><input type="text" class="int-text int-medium" id="flowName6" name="flowName6"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="quanfengkuaidi">全峰</td>
							                <td><input type="text" class="int-text int-medium" id="flowName7" name="flowName7"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="tiantian">天天</td>
							                <td><input type="text" class="int-text int-medium" id="flowName8" name="flowName8"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="ems">EMS</td>
							                <td><input type="text" class="int-text int-medium" id="flowName9" name="flowName9"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="youzhengguonei">中国邮政</td>
							                <td><input type="text" class="int-text int-medium" id="flowName10" name="flowName10"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="huitongkuaidi">百世汇通</td>
							                <td><input type="text" class="int-text int-medium" id="flowName11" name="flowName11"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="debangwuliu">德邦</td>
							                <td><input type="text" class="int-text int-medium" id="flowName12" name="flowName12"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="guotongkuaidi">国通</td>
							                <td><input type="text" class="int-text int-medium" id="flowName13" name="flowName13"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="dhl">DHL</td>
							                <td><input type="text" class="int-text int-medium" id="flowName14" name="flowName14"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						               <tr>
							                <td value="youshuwuliu">优速</td>
							                <td><input type="text" class="int-text int-medium" id="flowName15" name="flowName15"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						              <tr>
							                <td value="ups">UPS</td>
							                <td><input type="text" class="int-text int-medium" id="flowName16" name="flowName16"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
						                <tr>
							                <td value="other9999"><input type="text" class="int-text int-medium"  placeholder="请填写快递公司名" id="otherName" onblur="pager._valideName()"><input type='text' id='codeErr' class="error" style='display:none;color:red;font-weight:r400'></td>
							                <td><input type="text" class="int-text int-medium" id="flowName17" name="flowNameother"></td>
							                <td><a href="javaScript:void(0)"  style="text-decoration: underline" onclick="pager._confirmSendGoods('${orderId}',this)">确认发货</a></td>
						              </tr>
                                    </tbody>
                                    </table>
                                </div>
                                </form>
                              	<div class="eject-big"  id="choice">
								<div class="prompt-samll" id="p-operation">
								<div class="eject-medium-title">
										<p>提示操作</p>
										<p class="img"><i class="fa fa-times"></i></p>
									</div>
								<!--确认删除-->
								<div class="prompt-samll-confirm">
									<ul>
									
									<li class="word">请填写一个物流信息进行操作！</li>
									<li>
										<input id="p-op-close" type="button"  class="biu-btn btn-primary btn-small ml-15 mt-20 radius" value="确认">
									</ul>
								</div>
								</div>	
									<div class="mask" id="eject-mask"></div>
									</div>
                            <!--/table表格结束-->
                        	</div>	
                       </div>
                  </div>
              </div> 
         </div>
    </div>
 <script type="text/javascript">
var pager;
(function () {
	seajs.use('app/jsp/order/sendGoods', function (sendPager) {
		pager = new sendPager({element: document.body});
		pager.render();
	});
})();
</script>
</body>
</html>