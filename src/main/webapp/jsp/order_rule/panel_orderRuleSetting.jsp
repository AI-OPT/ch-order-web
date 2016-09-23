<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>订单规则设置</title>
<%@include file="/inc/inc.jsp"%>
<script type="text/javascript">
	var pager;
	(function() {
		seajs.use('app/jsp/order_rule/orderRuleSetting', function(
				OrderRuleSetting) {
			pager = new OrderRuleSetting({
				element : document.body
			});
			pager.render();
		});
	})();
</script>
</head>
<body>
	<div class="content-wrapper-iframe">
		<!--右侧灰色背景-->
		<!--框架标签结束-->
		<div class="row">
			<!--外围框架-->
			<div class="col-lg-12">
				<!--删格化-->
				<div class="row">
					<!--内侧框架-->
					<div class="col-lg-12">
						<!--删格化-->
						<div class="main-box clearfix">
							<!--白色背景-->
							<!--标题
						<header class="main-box-header clearfix">
							<h2 class="pull-left">未支付订单监控设置</h2>
						</header>
						-->
							<!--标题结束-->
							<!--查询条件-->
							<div class="form-label" style="padding-left:0px;">
								<form id="orderRuleSettingForm">
									<div class="panel panel-primary">
										<div class="panel-heading">
											<h3 class="panel-title">订单监控规则设置</h3>
										</div>
										<div class="panel-body">
											<div class="panel panel-info">
												<div class="panel-heading">
													<h3 class="panel-title">时间段监控</h3>
												</div>
												<div class="panel-body">
													<ul>
														<li class="col-md-12">

															<p class="word">监控时间为</p>
															<p>
																<input id="timeMonitorTime"
																	name="command.timeMonitorTime"
																	class="int-text int-medium " value="100" type="text" />
																<select id="timeMonitorTimeType" name="command.timeMonitorTimeType"
																	class="int-text int-medium ">
																	<option value="D">天</option>
																	<option value="H">小时</option>
																	<option value="MIN">分钟</option>
																</select> 内，订单量正常量为 <input id="timeMonitorOrderSum" name="command.timeMonitorOrderSum"
																	class="int-text int-medium " type="text" />
																单，超过正常量的进行监控预警
															</p>
														</li>
													</ul>
												</div>
											</div>

											<div class="panel panel-info">
												<div class="panel-heading">
													<h3 class="panel-title">购买人员监控</h3>
												</div>
												<div class="panel-body">
													<ul>

														<li class="col-md-12">
															<p class="word">同一用户，在</p>
															<p>
																<input id="buyEmployeeMonitorTime" name="command.buyEmployeeMonitorTime"
																	class="int-text int-medium " type="text" /> <select
																	id="buyEmployeeMonitorTimeType" name="command.buyEmployeeMonitorTimeType"
																	class="int-text int-medium ">
																	<option value="D">天</option>
																	<option value="H">小时</option>
																	<option value="MIN">分钟</option>
																</select> 内， 购买订单超过 <input id="buyEmployeeMonitorOrderSum"
																	name="command.buyEmployeeMonitorOrderSum"
																	class="int-text int-medium " type="text" /> 单，进行监控预警
															</p>
														</li>
													</ul>
												</div>
											</div>

											<div class="panel panel-info">
												<div class="panel-heading">
													<h3 class="panel-title">购买IP监控</h3>
												</div>
												<div class="panel-body">
													<ul>
														<li class="col-md-12">
															<p class="word">同一IP ， 在</p>
															<p>
																<input id="buyIpMonitorTime" name="command.buyIpMonitorTime"
																	class="int-text int-medium " type="text" /> <select
																	id="buyIpMonitorTimeType" name="command.buyIpMonitorTimeType"
																	class="int-text int-medium ">
																	<option value="D">天</option>
																	<option value="H">小时</option>
																	<option value="MIN">分钟</option>
																</select> 内， 购买订单超过 <input id="buyIpMonitorOrderSum" name="command.buyIpMonitorOrderSum"
																	class="int-text int-medium " type="text" /> 单，进行监控预警
															</p>
														</li>
													</ul>
												</div>
											</div>

											<div class="panel panel-info">
												<div class="panel-heading">
													<h3 class="panel-title">订单合并时间设置</h3>
												</div>
												<div class="panel-body">
													<ul>
														<li class="col-md-12">
															<p class="word">同一用户购买的</p>
															<p>
																同一商品，在<input id="mergeOrderSettingTime" name="command.mergeOrderSettingTime"
																	class="int-text int-medium " type="text" />
																分钟内，默认合并为同一订单 <input id="mergeOrderSettingTimeType" type="hidden"
																	name="command.mergeOrderSettingTimeType" value="MIN"/> <input id="mergeOrderSettingOrderSum"
																	type="text" name="command.mergeOrderSettingOrderSum" />
															</p>

														</li>
													</ul>
												</div>
											</div>
											<div style="margin: 0 auto; width: 200px; margin-top: -35px;">
												<ul>
													<li class="width-xlag">
														<p class="word">&nbsp;</p>
														<p>
															<input type="button" id="orderRuleSettingButton"
																class="biu-btn  btn-primary btn-blue btn-medium ml-10"
																value="保  存" data-toggle="modal"
																data-target="#saveModal">
														</p>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</form>
							</div>
							<!--查询结束-->
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--框架标签结束-->
		<div class="row">
			<!--外围框架-->
			<div class="col-lg-12">
				<!--删格化-->
				<div class="row">

					<!-- 模态框（Modal） 开始 -->
					<div class="modal fade" id="saveModal" tabindex="-1" role="dialog"
						aria-labelledby="saveModalLabel" aria-hidden="true">
						<div class="modal-dialog" style="width: 400px;">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">保存</h4>
								</div>
								<div class="modal-body">你是否确认保存信息？</div>
								<div class="modal-footer">
									<button type="button"
										class="biu-btn  btn-primary btn-blue btn-medium ml-10"
										data-dismiss="modal">关闭</button>
									<button onclick="javascript:alert('保存成功');alertTest();"
										type="button"
										class="biu-btn  btn-primary btn-blue btn-medium ml-10">
										确认</button>
								</div>
							</div>
							<!-- /.modal-content -->
						</div>
						<!-- /.modal -->
					</div>
					<!-- 模态框（Modal） 结束-->

					<!--内侧框架
				<div style="padding-left: 50px;">
					<h2>创建模态框（Modal）</h2>
					
					<button class="biu-btn btn-primary btn-blue btn-medium ml-30" data-toggle="modal" 
					   data-target="#myModal">
					   开始演示模态框
					</button>
				</div>-->
					<!-- 模态框（Modal） 开始 -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
								</div>
								<div class="modal-body">在这里添加一些文本 在这里添加一些文本 在这里添加一些文本
									在这里添加一些文本 在这里添加一些文本 在这里添加一些文本</div>
								<div class="modal-footer">
									<button type="button"
										class="biu-btn  btn-primary btn-blue btn-medium ml-10"
										data-dismiss="modal">关闭</button>
									<button type="button"
										class="biu-btn  btn-primary btn-blue btn-medium ml-10">
										提交更改</button>
								</div>
							</div>
							<!-- /.modal-content -->
						</div>
						<!-- /.modal -->
					</div>
					<!-- 模态框（Modal） 结束-->

				</div>

			</div>
		</div>
	</div>
</body>
</html>
