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
      <div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        	<div class="main-box-body clearfix">	<!--padding20-->
					  	 	<!--table表格-->
                                <div class="table-responsive clearfix">
                                    <div>
                                    	<p><input type="button" class="biu-btn btn-primary btn-blue btn-large" value="自己联系快递" id="search"></p>
                                    </div>
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
							                <td>顺丰</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
							          <tr>
							                <td>申通</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>圆通</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
							          <tr>
							                <td>中通</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>韵达</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
							          <tr>
							                <td>宅急送</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>全峰</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>天天</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>EMS</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>中国邮政</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>百世汇通</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>德邦</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>圆通</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>DHL</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						               <tr>
							                <td>优速</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						              <tr>
							                <td>UPS</td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
						                <tr>
							                <td><input type="text" class="int-text int-medium" value="请填写快递公司名"></td>
							                <td><input type="text" class="int-text int-medium"></td>
							                <td>确认发货</td>
						              </tr>
                                    </tbody>
                                    </table>
                                </div>
                            <!--/table表格结束-->
                        	</div>	
                       </div>
                  </div>
              </div> 
         </div>
    </div>
</body>
</html>