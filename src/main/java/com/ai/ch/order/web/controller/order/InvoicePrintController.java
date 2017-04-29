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
		ListInvoicePrintInfo listInvoicePrintInfo = new ListInvoicePrintInfo();
		//
		listInvoicePrintInfo.setList(bodyList);
		listInvoicePrintInfo.setId(InvoiceUtils.getID(InvoiceUtils.TYPE_BATCH_ADD));
		JSONObject invoicePrintJson =JSONObject.parseObject(JSONObject.toJSONString(listInvoicePrintInfo)); 
		
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
