package com.ai.ch.order.web.controller.order;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.InvoicePrintInfo;
import com.ai.ch.order.web.model.order.ListInvoicePrintInfo;
import com.ai.ch.order.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.order.web.utils.AmountUtil;
import com.ai.ch.order.web.utils.InvoiceUtils;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.order.api.delivergoods.interfaces.IDeliverGoodsPrintSV;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfoVo;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintInfosRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintRequest;
import com.ai.slp.order.api.delivergoods.param.DeliverGoodsPrintResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/deliveryPrint")
public class DeliveryGoodsPrintController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeliveryGoodsPrintController.class);
	
	@RequestMapping("/query")
	@ResponseBody
	public ResponseData<DeliverGoodsPrintResponse> query(HttpServletRequest request,String orderId) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<DeliverGoodsPrintResponse> responseData =null;
		try {
			DeliverGoodsPrintRequest req=new DeliverGoodsPrintRequest();
			req.setOrderId(Long.valueOf(orderId));
			req.setTenantId(user.getTenantId());
			IDeliverGoodsPrintSV deliverGoodsPrintSV = DubboConsumerFactory.getService(IDeliverGoodsPrintSV.class);
			DeliverGoodsPrintResponse response = deliverGoodsPrintSV.query(req);
			if(response!=null && response.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<DeliverGoodsPrintResponse>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",response);
			}else {
				responseData = new ResponseData<DeliverGoodsPrintResponse>(ResponseData.AJAX_STATUS_FAILURE, response.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {			
			responseData = new ResponseData<DeliverGoodsPrintResponse>(ResponseData.AJAX_STATUS_FAILURE, "查询出错,出现未知异常");
			LOG.error("查询信息出错",e);
		}
		System.out.println(JSON.toJSON(responseData));
		return responseData;
	}
	
	
	@RequestMapping("/print")
	@ResponseBody
	public ResponseData<BaseResponse> print(HttpServletRequest request,String orderId,
			String orderInfos) {
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		ResponseData<BaseResponse> responseData =null;
		try {
			DeliverGoodsPrintInfosRequest req=new DeliverGoodsPrintInfosRequest();
			List<DeliverGoodsPrintInfoVo> deliveryProdPrintVos = JSON.parseArray(orderInfos, DeliverGoodsPrintInfoVo.class); 
			for (DeliverGoodsPrintInfoVo deliverGoodsPrintInfoVo : deliveryProdPrintVos) {
				deliverGoodsPrintInfoVo.setSalePrice(AmountUtil.YuanToLi(deliverGoodsPrintInfoVo.getSalePrice()));
			}
			req.setOrderId(Long.valueOf(orderId));
			req.setInvoicePrintVos(deliveryProdPrintVos);
			req.setTenantId(user.getTenantId());
			IDeliverGoodsPrintSV deliveryOrderPrintSV = DubboConsumerFactory.getService(IDeliverGoodsPrintSV.class);
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
	 */
	@RequestMapping(value="/invoicePrint",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String invoicePrint(HttpServletRequest request,InvoicePrintInfo body) {
		String date_now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		body.setCorporationCode("2000"); // 公司代码
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
		body.setOrderCreateTime(""+DateUtil.getSysDate()); // （销售）订单创建日期

		body.setMaterialName("43U1"); // 物料代码
		body.setSpecification("55q2n");
		body.setMaterialCode("1233333"); // 物料名称
		body.setPrice("2213.67666666667");
		body.setQuantity("3"); // 数量
		body.setUnit("台");// 物料单位
		body.setDiscountAmount("0.00"); // 折扣金额
		body.setRate("0.17"); // 税率
		body.setTax("1128.97"); // 税金
		body.setAmount("2213.67666666667"); // (单价)金额
		body.setTaxAmount("7770"); // 含税金额
		body.setRemark("打印发票请求");//备注
		

		
		//获取授权ID
		body.setId(InvoiceUtils.getID(InvoiceUtils.TYPE_BATCH_ADD));//设置授权ID
		List<InvoicePrintInfo> bodyList = new ArrayList<InvoicePrintInfo>();
		bodyList.add(body);
		
		//
		ListInvoicePrintInfo listInvoicePrintInfo = new ListInvoicePrintInfo();
		//
		listInvoicePrintInfo.setList(bodyList);
		listInvoicePrintInfo.setId(InvoiceUtils.getID(InvoiceUtils.TYPE_BATCH_ADD));
		JSONObject invoicePrintJson =JSONObject.parseObject(JSONObject.toJSONString(listInvoicePrintInfo)); 
		
		//JSONArray invoicePrintJson = JSONArray.parseArray(JSONArray.toJSONString(bodyList));
		LOG.info("bodyList:"+invoicePrintJson.toJSONString());
		String retVal = InvoiceUtils.postBatchAdd(invoicePrintJson.toJSONString());
		LOG.info("retVal:"+retVal);
		Timestamp dateTimeStamp = DateUtil.getSysDate();
		LOG.info("timestamp:"+dateTimeStamp);
		return retVal;
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
	public String downloadInvoice(HttpServletRequest request,String invoiceCode,String invoiceNumber) {
		StringBuffer getfileURL =new StringBuffer(Constants.INVOICE_PRINT_URL+InvoiceUtils.GET_FILE);
		//获取授权ID
		String id=InvoiceUtils.getID(InvoiceUtils.TYPE_GetFile);
		getfileURL.append("?id="+id);
	    invoiceCode ="051201600121";
	    getfileURL.append("&invoiceCode="+invoiceCode);
	    invoiceNumber="11450001";
	    getfileURL.append("&invoiceNumber="+invoiceNumber);
		//返回下载电子发票的URL地址
//	    return getfileURL;
		return "http://bill.dchfcloud.com/BILL/PubicInterFace/GetFileByAuthInfo/?id=26B21B24-1A99-4E2D-8ABE-3486D5EC1ECC&invoiceNumber=11450001&invoiceCode=051201600121";
	}

	
	
}
