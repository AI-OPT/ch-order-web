<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>订单详情查询</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpbase}/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase}/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase}/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase}/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script src="${_slpbase}/scripts/frame.js" type="text/javascript"></script>

</head>

<body>

  <div class="fsast-charge">
     	<div class="big-wrapper"><!--内侧居中框架-->
       		<div class="payment-title"><p>请确认您的订单信息</p></div>
           <div class="recharge-bj-tow"><!--白色背景-->
				<div class="payment-success">
			       <p><img src="${_slpbase }/images/order-fail.png"></p>
			       <p class="word">订单详情查询失败！</p>
			       <p class="blod"><span id="sec">5</span>秒后自动跳转至“首页”</p>
			       <p class="alink"><A  id="BACK_BTN" href="${_base}">即刻跳转</A></p>
			       
			   </div>
         </div>
     </div>
 </div>
   
<script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/order/orderQueryFail', function (OrderFailPager) {
					pager = new OrderFailPager({element: document.body});
					pager.render();
				});
			})();
			
		</script>
</body>
</html>



