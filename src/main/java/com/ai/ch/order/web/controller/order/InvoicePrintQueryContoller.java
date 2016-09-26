package com.ai.ch.order.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.order.web.utils.RequestParameterUtils;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.order.api.invoiceprint.interfaces.IInvoicePrintSV;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintRequest;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintResponse;
import com.ai.slp.order.api.invoiceprint.param.InvoicePrintVo;
import com.alibaba.fastjson.JSON;

@RestController
public class InvoicePrintQueryContoller {
	private static final Logger log = LoggerFactory.getLogger(InvoicePrintQueryContoller.class);
	
	@RequestMapping(value="/invoiceController/queryList",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseData<PageInfo<InvoicePrintVo>> queryList(HttpServletRequest request){
		//
		String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
		//
		InvoicePrintRequest requestVo = RequestParameterUtils.request2Bean(request, InvoicePrintRequest.class);
		//
		//
		requestVo.setPageNo(Integer.parseInt(strPageNo));
		requestVo.setPageSize(Integer.parseInt(strPageSize));
		
		InvoicePrintResponse response = DubboConsumerFactory.getService(IInvoicePrintSV.class).queryList(requestVo);
		ResponseData<PageInfo<InvoicePrintVo>> responseData = new ResponseData<PageInfo<InvoicePrintVo>>(ResponseData.AJAX_STATUS_SUCCESS,"success",response.getPageInfo());
		//
		log.info("responseData:"+JSON.toJSONString(responseData));
		return responseData;
	}
}
