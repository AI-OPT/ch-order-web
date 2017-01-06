package com.ai.ch.order.web.controller.order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.ch.order.web.controller.constant.Constants;
import com.ai.ch.order.web.model.order.InvoicePrintInfo;
import com.ai.ch.order.web.model.order.ListInvoicePrintInfo;
import com.ai.ch.order.web.utils.InvoiceUtils;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
import com.ai.slp.order.api.invoiceprint.param.InvoiceModifyRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSubmitRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSumbitResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoiceSumbitVo;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/invoice")
public class InvoicePrintController {
	
	private static final Logger LOG = LoggerFactory.getLogger(InvoicePrintController.class);
	
	/**
	 * 发票打印通知
	 * @param request
	 * @param body
	 * @return
	 * @author zhouxh
	 */
	@RequestMapping(value="/invoicePrint",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String invoicePrint(HttpServletRequest request) {
		String orderId = request.getParameter("orderId");
		String tenantId = request.getParameter("tenantId");
		//
		InvoiceSubmitRequest invoiceSubmitRequest = new InvoiceSubmitRequest();
		invoiceSubmitRequest.setOrderId(new Long(orderId));
		invoiceSubmitRequest.setTenantId(tenantId);
		//
		InvoiceSumbitResponse response = DubboConsumerFactory.getService(IInvoicePrintSV.class).invoiceSubmit(invoiceSubmitRequest);
		
		if(!response.getResponseHeader().isSuccess()){
			//
			return "{\"IsSuccessful\":false,\"MessageKey\":\""+response.getResponseHeader().getResultMessage()+"\"}";
		}
		
		List<InvoiceSumbitVo> invoiceSumbitVoList = response.getInvoiceSumbitVo();
		InvoicePrintInfo invoicePrintInfo = null;
		//
		List<InvoicePrintInfo> bodyList = new ArrayList<InvoicePrintInfo>();
		LOG.info("invoiceSumbitVoList:"+invoiceSumbitVoList.size());
		
		//
		for(InvoiceSumbitVo invoiceSumbitVo : invoiceSumbitVoList){
			invoicePrintInfo = new InvoicePrintInfo();
			//
			BeanUtils.copyVO(invoicePrintInfo, invoiceSumbitVo);
			invoicePrintInfo.setOrderCreateTime(""+DateUtil.getSysDate());
			invoicePrintInfo.setBuyerMobiile(invoiceSumbitVo.getBuyerMobile());
			//
			bodyList.add(invoicePrintInfo);
		}
		//
//		String date_now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		body.setCorporationCode("2000"); // 公司代码
//		body.setInvoiceClass("0");// 发票类型 0 电子发票 1纸质发票
//		body.setInvoiceKind("003"); // 发票种类 001增值税发票 002增值税普通电子发票 003普通发票
//									// 004增值税普通发票 005增值税纸质发票 增值税电子发票
//		body.setBuyerTaxpayerNumber("510798326942604"); // 购货方纳税人识别号
//		body.setBuyerCode("");// 购货方代码
//		body.setBuyerName("四川智易家网络科技有限公司"); // 购货方名称
//		body.setBuyerAddress("四川绵阳高新区绵兴东路35号 "); // 购货方地址
//		body.setBuyerTelephone("15802856879");// 购货方固定电话
//		body.setBuyerMobiile("0816-2438114");// 购货方手机
//		body.setBuyerEmail("519945018@qq.com");// 购货方邮箱
//		body.setBuyerCompanyClass("03"); // 购货方类型 01:企业 02：机关事业单位 03：个人 04：其它
//		
//		body.setBuyerBankCode("10010");//购货方开户行代码
//		body.setBuyerBankName("中国工商银行股份有限公司绵阳高新技术产业开发支行");//购货方开户行名称
//		body.setBuyerBankAccount("2308414119100081746"); // 购货方银行账号
//		
//
//		body.setSalesOrderNo("0000012"); // 销售订单号
//		body.setOrderItem("123456789"); // 项目号
//		body.setOrderCreateTime(""+DateUtil.getSysDate()); // （销售）订单创建日期
//
//		body.setMaterialName("43U1"); // 物料代码
//		body.setSpecification("55q2n");
//		body.setMaterialCode("1233333"); // 物料名称
//		body.setPrice("2213.67666666667");
//		body.setQuantity("3"); // 数量
//		body.setUnit("台");// 物料单位
//		body.setDiscountAmount("0.00"); // 折扣金额
//		body.setRate("0.17"); // 税率
//		body.setTax("1128.97"); // 税金
//		body.setAmount("2213.67666666667"); // (单价)金额
//		body.setTaxAmount("7770"); // 含税金额
//		body.setRemark("打印发票请求");//备注
		

		
		//获取授权ID
		//body.setId(InvoiceUtils.getID(InvoiceUtils.TYPE_BATCH_ADD));//设置授权ID
		
		
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
	@RequestMapping(value="/downloadInvoice",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String downloadInvoice(HttpServletRequest request,String invoiceCode,String invoiceNumber) {
		//StringBuffer getfileURL =new StringBuffer(Constants.INVOICE_PRINT_URL+InvoiceUtils.DOWNLOAD_INVOICE_FILE_URL);
		StringBuffer getfileURL =new StringBuffer(Constants.INVOICE_DOWNLOAD_URL);
		//获取授权ID
		String id=InvoiceUtils.getID(InvoiceUtils.TYPE_GetFile);
		getfileURL.append("?id="+id);
	    //invoiceCode ="051201600121";
	    getfileURL.append("&invoiceCode="+invoiceCode);
	    //invoiceNumber="11450001";
	    getfileURL.append("&invoiceNumber="+invoiceNumber);
		//返回下载电子发票的URL地址
	    return getfileURL.toString();
		//return "http://bill.dchfcloud.com/BILL/PubicInterFace/GetFileByAuthInfo/?id=26B21B24-1A99-4E2D-8ABE-3486D5EC1ECC&invoiceNumber=11450001&invoiceCode=051201600121";
	}
	
	//修改发票状态
	@RequestMapping(value="/modifyInvoiceState",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public BaseResponse modifyInvoiceState(String tenantId,String invoiceStatus,String orderId){
		InvoiceModifyRequest invoiceModifyRequest = new InvoiceModifyRequest();
		//
		invoiceModifyRequest.setTenantId(tenantId);
		invoiceModifyRequest.setInvoiceStatus(invoiceStatus);
		invoiceModifyRequest.setOrderId(Long.valueOf(orderId));
		//
		BaseResponse response = DubboConsumerFactory.getService(IInvoicePrintSV.class).modifyState(invoiceModifyRequest);
		//
		return response;
	}
}
