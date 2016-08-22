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
                       
							<header class="main-box-header clearfix">
                            <h5 class="pull-left">新建运费模板</h5>
                            </header>
						
						<div class="form-label">
				            <ul>
				                <li class="col-lg-12">
				              		<p class="word">模板名称:</p>
				                    <p><input type="text" class="int-text int-medium"></p>
				                </li>
				                 <li class="col-lg-12">
				              		<p class="word">是否包邮:</p>
				                    <p><input type="radio"></p>
				                    <p>否</p>
				                    <p><input type="radio"></p>
				                    <p>是</p>
				                </li>
				                 <li class="col-lg-12">
				              		<p class="word">计价方式:</p>
				                    <p><input type="radio"></p>
				                    <p>按件数</p>
				                    <p><input type="radio"></p>
				                    <p>按重量</p>
				                    <p><input type="radio"></p>
				                    <p>按体积</p>
				                </li>
				                 <li class="col-lg-12">
				              		<p class="word">计价详情:</p>
				                    <p><input type="text" class="int-text int-mini"></p>
				                    <p>件及以内，</p>
				                    <p><input type="text" class="int-text int-mini"></p>
				                    <p>元，</p>
				                    <p>增加运费</p>
				                    <p><input type="text" class="int-text int-mini"></p>
				                    <p>元，</p>  
				                </li>
				                <li id="addcity"> <p class="add-city" >添加指定地区运费设置</p></li>
				                <li class="add-freight col-lg-12" id="addfrei">
				                	<!--table表格-->
                                <div class="table-responsive clearfix">
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>
                                            <tr>
                                            		<th>运送至</th>
                                                <th>首件（件）</th>
                                                <th>首费（元）</th>
                                                <th>续件（件）</th>
                                                <th>续费（元）</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                    <tbody>
                                        <tr>
                                        		<td>未添加省份地区 <a href="#" class="tr">编辑</a></td>
                                            <td><input type="text" class="int-text int-mini"></td>
                                            <td><input type="text" class="int-text int-mini"></td>
                                            <td><input type="text" class="int-text int-mini"></td>
                                            <td><input type="text" class="int-text int-mini"></td>
                                            <td><a href="#">删除</a></td>
                                        </tr> 
                                    </tbody>
                                    </table>
                               
                                </div>
                            <!--/table表格结束--> 	
				                </li>
				                  <li class="col-lg-12">
				              		<p class="word">&nbsp;</p>
				                    <p><input type="checkbox"></p>
				                    <p>设置包邮条件</p>
				                </li>
				                 <li class="col-lg-12">
				                 	<p class="word">&nbsp;</p>
				              		<p><select class="select select-medium"><option>选择包邮类型</option></select></p>
				                    <p><input type="text" class="int-text int-mini"></p>
				                    <p>元，</p>  
				                </li>
				            </ul>  
       				   	</div>
       				   
                        <div class="bc-ang mb-10"><input type="button" class="biu-btn btn-primary btn-blue btn-medium ml-10" value="保  存"></div>
						</div>	
                        
                </div>
              </div> 
            </div>
    </div>
</body>
</html>