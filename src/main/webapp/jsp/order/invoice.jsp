<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>查询打印列表</title>
<%@include file="/inc/inc.jsp" %>
<script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/order/invoiceList', function (ListPager) {
			pager = new ListPager({element: document.body});
			pager.render();
		});
	})();
 </script> 
</head>
<body>
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
        <!--框架标签结束-->
      <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化-->
	                    <div class="main-box clearfix"><!--白色背景-->
	                    	<!--查询条件-->
	                    	<div class="form-label" id="selectDiv">
	                    	<form id="queryForm">
	                    	<input type="hidden" id="tenantId" name="command.tenantId" value="changhong">
					           <ul>
				                 	<li class="col-md-6">
							            <p class="word">订单号</p>
							            <p><input name="command.orderId" class="int-text int-medium"  type="text" placeholder="请输入订单号" ></p>
							        </li>
					               <li class="col-md-6">
							            <p class="word">发票抬头</p>
							            <p><input name="command.invoiceTitle" class="int-text int-medium"  type="text" placeholder="请输入发票抬头" ></p>
							        </li> 
					            </ul> 
					            <ul>
				            		<li class="col-md-6">
						            	<p class="word">发票状态</p>
					            		<p>
						            		<select name="command.invoiceStatus" class="select select-medium">
						            			<option value="">请选择</option>
						            			<option value="1">未打印</option>
						            			<option value="3">已打印</option>
						            			<option value="2">已报送</option>
						            		</select>
					            		</p>
						            </li>
						            <li class="col-md-6">
					            		<p><input id="queryButtonId" type="button" class="biu-btn btn-primary btn-blue btn-mini" value="查询" id="search"></p>
					            	</li> 
					            </ul>
					            </form>
					         </div>
					   	<!--查询结束-->      
	         			</div>
	                </div>
              </div>
         </div>
     </div>	
   	  	<div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        <!--标题-->
                            <header class="main-box-header clearfix">
                            <h2 class="pull-left">发票列表</h2>
                            </header>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
                                   	<!--table表格-->
                          		<div class="table-responsive clearfix mt-10">
                                    <table class="table table-hover table-border table-bordered ">
                                        <thead>
                                            <tr>
                                                <th>订单号</th>
                                                <th>发票类目</th>
                                                <th>发票抬头</th>
                                                <th>发票金额</th>
                                                <th>税率</th>
                                                <th>税额</th>
                                                <th>发票类型</th>
                                                <th>发票打印状态</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                         <tbody id="table_info_id_pay_id">
                                           
                                         </tbody>
                                    </table>
                                    <div id="showMessageDiv"></div>
                                </div>
                           		<!--/table表格结束-->
                                </div>
                                <!--分页-->
                                <div class="paging">
									<ul id="pagination">
									</ul>
								</div>
								<!--分页-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
</body>
<script id="pageSearchTmpl" type="text/x-jsrender">
					  	
						<tr>
	                                            <td>{{:orderId}}</td>
	                                            <td>{{:invoiceContent}}</td>
	                                            <td>{{:invoiceTitle}}</td>
	                                            <td>￥{{:invoiceMoney}}</td>
	                                            <td>{{:taxRate}}%</td>
	                                            <td>{{:taxMoney}}</td>
	                                            <td>
													{{if invoiceType == '0'}}电子发票{{/if}}
													{{if invoiceType == '1'}}纸质发票{{/if}}
													

												</td>
	                                            <td>{{if invoiceStatus == '1'}}未打印{{/if}}
													{{if invoiceStatus == '2'}}已报送{{/if}}
													{{if invoiceStatus == '3'}}已打印{{/if}}
													{{if invoiceStatus == '4'}}打印失败{{/if}}
												</td>
	                                            <td>
												{{if invoiceStatus == '1'}}
													<a href="javascript:void(0);" onclick="pager._invoicePrint('changhong','{{:orderId}}');">发票报送</a>
												{{/if}}
												{{if invoiceStatus == '2'}}
													等待下载
												{{/if}}
												{{if invoiceStatus == '3'}}
													<a href="javascript:void(0);" onclick="pager._downloadInvoice('{{:invoiceId}}','{{:invoiceNum}}');">发票下载</a>
												{{/if}}
												{{if invoiceStatus == '4'}}
													<a href="javascript:void(0);" onclick="pager._invoicePrint('changhong','{{:orderId}}');">发票报送</a>
												{{/if}}
												</td>
	                                       </tr>
						
					  </script>
</html>