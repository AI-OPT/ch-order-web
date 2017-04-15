<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>刷新数据情况</title>
<%@include file="/inc/inc.jsp"%>
</head>
<body>
	<div class="flushdata" style="margin: 10px 20px 30px 40px">
		<div>
			<ul style="margin: 30px 20px 30px 40px">
				<li style="margin: 10px 20px 30px 40px">符合条件的查询数据共有:${resp.queryCount } 个</li>
				<li style="margin: 10px 20px 30px 40px">成功刷新的数据有:${resp.queryCount-resp.failCount-resp.shareParentCount} 个</li>
				<li style="margin: 10px 20px 30px 40px">刷新失败的数据有:${resp.failCount } 个 .订单id具体有: ${failOrders}</li>
				<li style="margin: 10px 20px 30px 40px">存在相同父订单的数据有:${resp.shareParentCount } 个</li>
			</ul>
		</div>
	</div>
</body>
</html>
