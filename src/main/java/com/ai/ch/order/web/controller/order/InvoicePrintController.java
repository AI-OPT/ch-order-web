package com.ai.ch.order.web.controller.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.InvoicePrintInfo;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.WcfUtils;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.order.api.deliveryorderprint.interfaces.IDeliveryOrderPrintSV;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintInfosRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderQueryResponse;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryProdPrintVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/invoice")
public class InvoicePrintController {
	
	private static final Logger LOG = LoggerFactory.getLogger(InvoicePrintController.class);
	
	@RequestMapping("/query")
	@ResponseBody
	public ResponseData<DeliveryOrderQueryResponse> query(HttpServletRequest request,String orderId) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<DeliveryOrderQueryResponse> responseData =null;
		try {
			DeliveryOrderPrintRequest req=new DeliveryOrderPrintRequest();
			req.setOrderId(Long.valueOf(orderId));
			req.setTenantId(user.getTenantId());
			IDeliveryOrderPrintSV deliveryOrderPrintSV = DubboConsumerFactory.getService(IDeliveryOrderPrintSV.class);
			DeliveryOrderQueryResponse response = deliveryOrderPrintSV.query(req);
			if(response!=null && response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<DeliveryOrderQueryResponse>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",response);
			}else {
				responseData = new ResponseData<DeliveryOrderQueryResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {			
			responseData = new ResponseData<DeliveryOrderQueryResponse>(ResponseData.AJAX_STATUS_FAILURE, "查询出错,出现未知异常");
			LOG.error("查询信息出错",e);
		}
		return responseData;
	}
	
	
	@RequestMapping("/print")
	@ResponseBody
	public ResponseData<BaseResponse> print(HttpServletRequest request,String orderId,
			String orderInfos) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<BaseResponse> responseData =null;
		try {
			DeliveryOrderPrintInfosRequest req=new DeliveryOrderPrintInfosRequest();
			List<DeliveryProdPrintVo> deliveryProdPrintVos = JSON.parseArray(orderInfos, DeliveryProdPrintVo.class); 
			req.setOrderId(Long.valueOf(orderId));
			req.setDeliveryProdPrintVos(deliveryProdPrintVos);
			req.setTenantId(user.getTenantId());
			IDeliveryOrderPrintSV deliveryOrderPrintSV = DubboConsumerFactory.getService(IDeliveryOrderPrintSV.class);
			BaseResponse response = deliveryOrderPrintSV.print(req);
			if(response!=null && response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<BaseResponse>(ResponseData.AJAX_STATUS_SUCCESS, "发货单打印成功",response);
			}else {
				responseData = new ResponseData<BaseResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {			
			responseData = new ResponseData<BaseResponse>(ResponseData.AJAX_STATUS_FAILURE, "打印出错,出现未知异常");
			LOG.error("打印信息出错",e);
		}
		return responseData;
	}
	
	
	@RequestMapping("/deliverGoods")
	public ModelAndView deliverGoods(HttpServletRequest request,String orderId) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("orderId", orderId);
		return new ModelAndView("jsp/order/sendGoods",model);
	}
	
	
	
	/**
	 * 发票打印通知
	 * @param request
	 * @param body
	 * @return
	 * @author zhouxh
	 * @ApiDocMethod
	 * @ApiCode
	 * @RestRelativeURL
	 */
	@RequestMapping("/invoicePrint")
	@ResponseBody
	public String invoicePrint(HttpServletRequest request,InvoicePrintInfo body) {
		String date_now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		body.setCorporationCode("000003"); // 公司代码
		body.setInvoiceClass("0");// 发票类型 0 电子发票 1纸质发票
		body.setInvoiceKind("003"); // 发票种类 001增值税发票 002增值税普通电子发票 003普通发票
									// 004增值税普通发票 005增值税纸质发票 增值税电子发票
		body.setBuyerTaxpayerNumber("510798326942604"); // 购货方纳税人识别号
		body.setBuyerCode("");// 购货方代码
		body.setBuyerName("四川智易家网络科技有限公司"); // 购货方名称
		body.setBuyerAddress("四川绵阳高新区绵兴东路35号 "); // 购货方地址
		body.setBuyerTelephone("15802856879");// 购货方固定电话
		body.setBuyerMobiile("0816-2438114");// 购货方手机
		body.setBuyerEmail("519945018@qq.com");// 购货方邮箱
		body.setBuyerCompanyClass("03"); // 购货方类型 01:企业 02：机关事业单位 03：个人 04：其它
		
		body.setBuyerBankCode("10010");//购货方开户行代码
		body.setBuyerBankName("中国工商银行股份有限公司绵阳高新技术产业开发支行");//购货方开户行名称
		body.setBuyerBankAccount("2308414119100081746"); // 购货方银行账号
		

		body.setSalesOrderNo("0000012"); // 销售订单号
		body.setOrderItem("123456789"); // 项目号
		body.setOrderCreateTime(date_now); // （销售）订单创建日期

		body.setMaterialName("43U1"); // 物料代码
		body.setSpecification("55q2n");
		body.setMaterialCode("液晶电视"); // 物料名称
		body.setPrice("2213.67666666667");
		body.setQuantity("3"); // 数量
		body.setUnit("台");// 物料单位
		body.setDiscountAmount("0.00"); // 折扣金额
		body.setRate("0.17"); // 税率
		body.setTax("1128.97"); // 税金
		body.setAmount("2213.67666666667"); // (单价)金额
		body.setTaxAmount("7770"); // 含税金额
		body.setRemark("打印发票请求");//备注
		

		// 服务地址
		HttpPost httpPost = new HttpPost(Constants.INVOICE_PRINT_URL);
		CloseableHttpClient client = HttpClients.createDefault();
		//获取授权ID
		body.setId(WcfUtils.getID(httpPost, client));//设置授权ID
		JSONObject invoicePrintJson =JSONObject.parseObject(JSONObject.toJSONString(body)); 
		String retVal = WcfUtils.postWcf(httpPost, client, invoicePrintJson.toJSONString());
		return "发票打印通知成功";
	}
	/**
	 * 下载电子发票
	 * @param request
	 * @param body
	 * @return
	 * @author zhouxh
	 */
	@RequestMapping("/downloadInvoice")
	@ResponseBody
	public String downloadInvoice(HttpServletRequest request,InvoicePrintInfo body) {
		HttpPost httpPost = new HttpPost(Constants.INVOICE_DOWNLOAD_URL);
		CloseableHttpClient client = HttpClients.createDefault();
		//获取授权ID
		JSONObject invoiceInfo = new JSONObject();
		invoiceInfo.put("id", WcfUtils.getID(httpPost, client));//设置授权ID
		invoiceInfo.put("invoiceCode", "051201600121");//发票代码
		invoiceInfo.put("invoiceNumber", "11450001");//发票号码
		JSONObject invoicePrintJson =JSONObject.parseObject(JSONObject.toJSONString(body)); 
		//返回结果为 pdf文件流
		String retVal = WcfUtils.postWcf(httpPost, client, invoicePrintJson.toJSONString());
		return "下载电子发票下载成功";
	}

	
	
}
