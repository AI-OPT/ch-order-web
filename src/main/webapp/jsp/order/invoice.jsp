<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>查询打印列表</title>
<%@include file="/inc/inc.jsp" %>
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
					           <ul>
				                 	<li class="col-md-6">
							            <p class="word">订单号</p>
							            <p><input class="int-text int-medium"  type="text" placeholder="请输入订单号" ></p>
							        </li>
					               <li class="col-md-6">
							            <p class="word">发票抬头</p>
							            <p><input class="int-text int-medium"  type="text" placeholder="请输入发票抬头" ></p>
							        </li> 
					            </ul> 
					            <ul>
				            		<li class="col-md-6">
						            	<p class="word">发票状态</p>
					            		<p>
						            		<select class="select select-medium">
						            			<option value="">请选择</option>
						            			<option value="">未打印</option>
						            			<option value="">已打印</option>
						            			<option value="">已报送</option>
						            		</select>
					            		</p>
						            </li>
						            <li class="col-md-6">
					            		<p><input type="button" class="biu-btn btn-primary btn-blue btn-mini" value="查询" id="search"></p>
					            	</li> 
					            </ul>
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
                            <h2 class="pull-left">查询结果</h2>
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
                                         <tbody>
                                           <tr>
	                                            <td>3123231</td>
	                                            <td>办公用品</td>
	                                            <td>北京八方达公司</td>
	                                            <td>431</td>
	                                            <td>12%</td>
	                                            <td>125</td>
	                                            <td>增值税发票</td>
	                                            <td>未打印</td>
	                                            <td>打印</td>
	                                       </tr>
                                          <tr>
	                                            <td>3123231</td>
	                                            <td>办公用品</td>
	                                            <td>北京八方达公司</td>
	                                            <td>431</td>
	                                            <td>12%</td>
	                                            <td>125</td>
	                                            <td>增值税发票</td>
	                                            <td>已打印</td>
	                                            <td>查看</td>
	                                       </tr>
	                                       <tr>
	                                            <td>3123231</td>
	                                            <td>办公用品</td>
	                                            <td>北京八方达公司</td>
	                                            <td>431</td>
	                                            <td>12%</td>
	                                            <td>125</td>
	                                            <td>增值税发票</td>
	                                            <td>已报送</td>
	                                            <td>重新报送</td>
	                                       </tr>
                                         </tbody>
                                    </table>
                                </div>
                           		<!--/table表格结束-->
                                </div>
                                <!--分页-->
                                <div class="paging">
                            		<ul class="pagination">
									<li class="disabled"><a href="#"><i class="fa fa-chevron-left"></i></a></li>
									<li class="active"><a href="#">1</a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">4</a></li>
									<li><a href="#">5</a></li>
									<li><a href="#"><i class="fa fa-chevron-right"></i></a></li>
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

</html>